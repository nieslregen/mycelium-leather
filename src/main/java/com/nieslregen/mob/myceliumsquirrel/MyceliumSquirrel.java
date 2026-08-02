package com.nieslregen.mob.myceliumsquirrel;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.block.ModBlocks;
import com.nieslregen.block.custom.CustomOccupant;
import com.nieslregen.block.custom.mushroomstem.MushroomStemHollowEntity;
import com.nieslregen.items.ModItems;
import com.nieslregen.mob.HollowUser;
import com.nieslregen.mob.ModPoiTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import java.util.Comparator;
import java.util.Optional;

public class MyceliumSquirrel extends HollowUser {

    boolean isClimbing = false;


    private int timeUntilResting;
    private int digTimer;
    private boolean needsToRest = false;

    // Avoid daylight goal?
    // can glide down from nest then when hitting the ground it rolls

    // ToDo: check out BeeLocateHiveGoal

    public MyceliumSquirrel(EntityType<? extends Animal> type, Level level) {
        super(type, level);
        resetTimeUntilResting();
        resetDigTimer();

        if (level instanceof ServerLevel) {

            Registry<PoiType> registry = level.registryAccess()
                    .lookupOrThrow(Registries.POINT_OF_INTEREST_TYPE);

            registry.get(ModPoiTypes.SQUIRREL_HOME)
                    .ifPresentOrElse(
                            poi -> System.out.println("POI gefunden: " + poi),
                            () -> System.out.println("POI NICHT gefunden!")
                    );


            registry.listElements()
                    .forEach(holder ->
                            System.out.println(holder.key())
                    );
        }
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new WallClimberNavigation(this, level);
//        return super.createNavigation(level);
    }

    @Override
    public boolean onClimbable() {
        return this.isClimbing();
    }

    public boolean isClimbing() {
        return isClimbing;
    }


    public void setClimbing(final boolean value) {
        isClimbing = value;
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return false;
    }


    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        return null;
    }
    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 5)
                .add(Attributes.TEMPT_RANGE, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.FOLLOW_RANGE, 25)
                .add(Attributes.SCALE, 1);
    }

    private void resetTimeUntilResting() {
        timeUntilResting = 0;
        needsToRest = false;
    }

    private void resetDigTimer() {
        digTimer = this.random.nextInt(6000) + 6000;
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.level() instanceof ServerLevel serverLevel && !needsToRest) {
            if (this.isAlive() && --timeUntilResting <= 0) {
                needsToRest = true;
                MyceliumLeatherMod.LOGGER.info("Squirrel wants to rest");
            }
        }
        if (!this.level().isClientSide()) {
            this.setClimbing(this.horizontalCollision);
        }
        digTimer--;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new EnterHollowGoal(this));
        this.goalSelector.addGoal(2, new PanicGoal(this, (double)1.25F));
        this.goalSelector.addGoal(3, new SquirrelLocateHollowGoal(this));
        this.goalSelector.addGoal(4, new GoHomeGoal(this));
        this.goalSelector.addGoal(5, new DigForTrufflesGoal(this));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this,1));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
    }



    private class SquirrelLocateHollowGoal extends Goal {
        private final MyceliumSquirrel squirrel;

        public SquirrelLocateHollowGoal(MyceliumSquirrel squirrel) {
            this.squirrel = squirrel;
        }

        @Override
        public boolean canUse() {
            return this.squirrel.getHomePos().isEmpty()
                    && this.squirrel.needsToRest;
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public void start() {
            MyceliumLeatherMod.LOGGER.info("Search for hollow");
            squirrel.resetTimeUntilResting();
            squirrel.homePos = findHollowWithSpace();
        }

        private Optional<BlockPos> findHollowWithSpace() {
            BlockPos squirrelPos = squirrel.blockPosition();
            PoiManager poiManager = ((ServerLevel) squirrel.level()).getPoiManager();

            return poiManager.getInRange((p) ->
                            p.is(ModPoiTypes.SQUIRREL_HOME),
                            squirrelPos,
                            20,
                            PoiManager.Occupancy.ANY)
                    .map(PoiRecord::getPos)
                    .peek(x -> MyceliumLeatherMod.LOGGER.info("Hollow found at [{}]", x))
                    .filter(this::doesHollowHaveSpace)
                    .sorted(Comparator.comparingDouble((pos) -> pos.distSqr(squirrelPos)))
                    .findFirst();
        }

        private boolean doesHollowHaveSpace(final BlockPos pos) {
            // ToDo: check if occupant limit is reached
            return squirrel.level().getBlockState(pos).is(ModBlocks.MUSHROOM_STEM_HOLLOW);
        }


    }

    private class GoHomeGoal extends Goal {

        private final MyceliumSquirrel squirrel;

        public GoHomeGoal(MyceliumSquirrel squirrel) {
            this.squirrel = squirrel;
        }

        @Override
        public void start() {
            MyceliumLeatherMod.LOGGER.info("start go home");
            squirrel.homePos.ifPresent(pos -> squirrel
                    .getNavigation()
                    .moveTo(
                            pos.getX() + 0.5,
                            pos.getY(),
                            pos.getZ() + 0.5,
                            1.25
                    ));
        }

        @Override
        public void stop() {
            super.stop();
            squirrel.resetTimeUntilResting();
            squirrel.getNavigation().stop();
            MyceliumLeatherMod.LOGGER.info("end go home");
        }


        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse()
                    && squirrel.needsToRest
                    && squirrel.homePos.isPresent()
                    && !squirrel.homePos.get()
                    .closerToCenterThan(squirrel.position(), 0.75);
        }

        @Override
        public boolean canUse() {
            return squirrel.homePos.isPresent()
                    && squirrel.needsToRest;
        }
    }

    private class DigForTrufflesGoal extends Goal {
        private final MyceliumSquirrel squirrel;

        private DigForTrufflesGoal(MyceliumSquirrel squirrel) {
            this.squirrel = squirrel;
        }

        @Override
        public boolean canUse() {
            return squirrel.digTimer <= 0
                    && !squirrel.needsToRest;
        }

        @Override
        public void stop() {
            super.stop();
            squirrel.resetDigTimer();
            if (squirrel.level().getBlockState(squirrel.blockPosition().below()).is(Blocks.MYCELIUM)) {
                int randomizedDrop = squirrel.random.nextInt(1, 3);
                Block.popResourceFromFace(level(), squirrel.blockPosition().below(), Direction.UP, new ItemStack(ModItems.TRUFFLE, randomizedDrop));
            }
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }
    }


    private class EnterHollowGoal extends Goal {
        private final MyceliumSquirrel squirrel;

        private EnterHollowGoal(MyceliumSquirrel squirrel) {
            this.squirrel = squirrel;
        }

        @Override
        public void start() {
            super.start();
            squirrel.homePos.ifPresent(pos -> {
                MushroomStemHollowEntity entity = (MushroomStemHollowEntity) squirrel.level().getBlockEntity(pos);
                if (entity != null) {
                    entity.addOccupant(squirrel);
                }
            });
        }

        @Override
        public boolean canUse() {
            if (squirrel.homePos.isPresent() && squirrel.needsToRest) {
                if (squirrel.level().getBlockEntity(squirrel.homePos.get()) instanceof MushroomStemHollowEntity entity) {
                    return !entity.isFull() && entity.getBlockPos().closerToCenterThan(squirrel.position(), 2f);
                }
            }
            return false;
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }
    }
}

package com.nieslregen.mob.myceliumsquirrel;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.block.ModBlocks;
import com.nieslregen.mob.ModPoiTypes;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import java.util.Comparator;
import java.util.Optional;

public class MyceliumSquirrel extends Animal {

    boolean isClimbing = false;

    private Optional<BlockPos> homePos = Optional.empty();
    private int timeUntilResting;
    private boolean needsToRest = false;

    // Avoid daylight goal?
    // can glide down from nest then when hitting the ground it rolls

    // ToDo: check out BeeLocateHiveGoal

    public MyceliumSquirrel(EntityType<? extends Animal> type, Level level) {
        super(type, level);
        resetTimeUntilResting();

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
        timeUntilResting = this.random.nextInt(6000) + 6000;
        needsToRest = false;
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
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, (double)1.25F));
        this.goalSelector.addGoal(2, new SquirrelLocateHollowGoal(this));
        this.goalSelector.addGoal(2, new GoHomeGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this,1));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    public Optional<BlockPos> getHomePos() {
        if (homePos.isPresent()) {
            BlockState state = this.level().getBlockState(homePos.get());
            if (state.is(ModBlocks.MUSHROOM_STEM_HOLLOW)) {
                return homePos;
            } else {
                homePos = Optional.empty();
            }
        }
        return Optional.empty();

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
}

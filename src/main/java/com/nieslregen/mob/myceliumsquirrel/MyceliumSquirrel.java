package com.nieslregen.mob.myceliumsquirrel;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.items.ModItems;
import com.nieslregen.mob.HollowUser;
import com.nieslregen.mob.ModPoiTypes;
import com.nieslregen.mob.goals.squirrel.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jspecify.annotations.Nullable;

import static com.nieslregen.datagen.ModEntityLootTableProvider.DEATH_MYCELIUM_SQUIRREL;

public class MyceliumSquirrel extends HollowUser {

    boolean isClimbing = false;


    public int timeUntilResting;
    public int digTimer;
    public boolean needsToRest = false;
    public boolean carriesBaby = false;

    // Avoid daylight goal?
    // can glide down from nest then when hitting the ground it rolls

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

    // ToDo: check if these sounds fit better: SoundEvents.AXOLOTL_DEATH; SoundEvents.AXOLOTL_IDLE_AIR; SoundEvents.AXOLOTL_HURT;

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.RABBIT_DEATH;
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.ALLAY_THROW;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.RABBIT_HURT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockState) {
        playSound(SoundEvents.ARMADILLO_STEP);
    }

    @Override
    protected @Nullable ResourceKey<LootTable> getDeathLootTable() {
        return DEATH_MYCELIUM_SQUIRREL;
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

    public void resetTimeUntilResting() {
        timeUntilResting =  this.random.nextInt(6000) + 6000;;
        needsToRest = false;
    }

    public void resetDigTimer() {
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
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.25F));
        this.goalSelector.addGoal(3, new LocateHollowGoal(this));
        this.goalSelector.addGoal(4, new GoHomeGoal(this));
        this.goalSelector.addGoal(5, new SquirrelBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new TemptGoal(this, 1D, (i) -> i.is(ModItems.MYCELIUM_CHICKEN_EGG), true));
        this.goalSelector.addGoal(7, new DigForTrufflesGoal(this));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this,1));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(ModItems.MYCELIUM_CHICKEN_EGG);
    }
}

package com.nieslregen.mob.myceliumsquirrel;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.items.ModItems;
import com.nieslregen.mob.HollowUser;
import com.nieslregen.mob.ModEntityTypes;
import com.nieslregen.mob.goals.squirrel.*;
import com.nieslregen.navigation.CustomClimbingNavigation;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jspecify.annotations.Nullable;


import static com.nieslregen.datagen.ModEntityLootTableProvider.DEATH_MYCELIUM_SQUIRREL;

public class MyceliumSquirrel extends HollowUser {


    private static final EntityDimensions BABY_DIMENSIONS;

    public static final EntityDataAccessor<Boolean> DIGGING = SynchedEntityData.defineId(MyceliumSquirrel.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> CLIMBING = SynchedEntityData.defineId(MyceliumSquirrel.class, EntityDataSerializers.BOOLEAN);
    public final AnimationState diggingAnimationState = new AnimationState();
    public final AnimationState climbingAnimationState = new AnimationState();

    public int timeUntilResting;
    public int digTimer;
    public boolean wantsToGoHome = false;
    public boolean carriesBaby = false;
    public int bufferFindHollow;

    public MyceliumSquirrel(EntityType<? extends Animal> type, Level level) {
        super(type, level);
        resetTimeUntilResting();
        resetDigTimer();
        resetBufferFindHollow();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(CLIMBING, false);
        entityData.define(DIGGING, false);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        super.onSyncedDataUpdated(accessor);

        if (accessor == DIGGING) {
            this.diggingAnimationState.animateWhen(isDigging(), this.tickCount);
        }
        if (accessor == CLIMBING) {
            this.climbingAnimationState.animateWhen(isClimbing(), this.tickCount);
        }
    }

    @Override
    protected EntityDimensions getDefaultDimensions(Pose pose) {
        return isBaby() ? BABY_DIMENSIONS : super.getDefaultDimensions(pose);
    }

    public boolean isDigging() {
        return entityData.get(DIGGING);
    }

    public void setDigging(boolean digging) {
        entityData.set(DIGGING, digging);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new CustomClimbingNavigation(this, level);
    }

    @Override
    public boolean onClimbable() {
        return this.isClimbing();
    }

    public boolean isClimbing() {
        return entityData.get(CLIMBING);
    }

    public void setClimbing(final boolean value) {
        entityData.set(CLIMBING, value);
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.AXOLOTL_DEATH;
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.AXOLOTL_IDLE_AIR;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.AXOLOTL_HURT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockState) {
        playSound(SoundEvents.GRASS_STEP);
    }

    @Override
    protected @Nullable ResourceKey<LootTable> getDeathLootTable() {
        return DEATH_MYCELIUM_SQUIRREL;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        return ModEntityTypes.SQUIRREL.create(level, EntitySpawnReason.BREEDING);
    }
    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 5)
                .add(Attributes.TEMPT_RANGE, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.FOLLOW_RANGE, 25)
                .add(Attributes.SCALE, 1);
    }

    public int resetBufferFindHollow() {
        this.bufferFindHollow = this.random.nextInt(750) + 750;
        return this.bufferFindHollow;
    }

    public int resetTimeUntilResting() {
        timeUntilResting = this.random.nextInt(2400) + 2400;
        wantsToGoHome = false;
        return timeUntilResting;
    }

    public int resetDigTimer() {
        digTimer = this.random.nextInt(3000) + 3000;
        return digTimer;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            this.setClimbing(this.horizontalCollision);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.level() instanceof ServerLevel serverLevel && !wantsToGoHome) {
            if (this.isAlive() && --timeUntilResting <= 0) {
                wantsToGoHome = true;
                MyceliumLeatherMod.LOGGER.info("Squirrel wants to rest");
            }
        }
        digTimer--;

        if (level().isDarkOutside()) {
            wantsToGoHome = true;
        }

        if (getHomePos().isEmpty()){
            bufferFindHollow--;
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new EnterHollowGoal(this));
        this.goalSelector.addGoal(2, new GoHomeGoal(this));
        this.goalSelector.addGoal(3, new LocateHollowGoal(this));
        this.goalSelector.addGoal(4, new HollowUserPanicGoal(this, 1.25F));
        this.goalSelector.addGoal(5, new StayCloseToHollowGoal(this));
        this.goalSelector.addGoal(6, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new TemptGoal(this, 1D, (i) -> i.is(ModItems.MYCELIUM_CHICKEN_EGG), true));
        this.goalSelector.addGoal(8, new DigForTrufflesGoal(this));
        this.goalSelector.addGoal(9, new WaterAvoidingRandomStrollGoal(this,1));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(11, new RandomLookAroundGoal(this));
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(ModItems.MYCELIUM_CHICKEN_EGG);
    }

    static {
        BABY_DIMENSIONS = EntityDimensions.scalable(1F, 1F).withEyeHeight(0.2F).withAttachments(EntityAttachments.builder().attach(EntityAttachment.PASSENGER, 0.0F, 0.375F, 0.0F));

    }

    public static final String TIME_UNTIL_RESTING_IDENTIFIER = "timeUntilResting";
    public static final String DIGGING_IDENTIFIER = "digging";
    public static final String DIG_TIMER_IDENTIFIER = "digTimer";
    public static final String NEEDS_TO_REST_IDENTIFIER = "needsToRest";
    public static final String CARRIES_BABY_IDENTIFIER = "carriesBaby";

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt(TIME_UNTIL_RESTING_IDENTIFIER, timeUntilResting);
        output.putInt(DIG_TIMER_IDENTIFIER, digTimer);

        output.putBoolean(NEEDS_TO_REST_IDENTIFIER, wantsToGoHome);
        output.putBoolean(CARRIES_BABY_IDENTIFIER, carriesBaby);

        output.putBoolean(DIGGING_IDENTIFIER, isDigging());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);

        input.getIntOr(TIME_UNTIL_RESTING_IDENTIFIER, resetTimeUntilResting());
        input.getIntOr(DIG_TIMER_IDENTIFIER, resetDigTimer());

        wantsToGoHome = input.getBooleanOr(NEEDS_TO_REST_IDENTIFIER, false);
        carriesBaby = input.getBooleanOr(CARRIES_BABY_IDENTIFIER, false);

        setDigging(input.getBooleanOr(DIGGING_IDENTIFIER, false));

    }
}

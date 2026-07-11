package com.nieslregen.mob.myceliumchicken;

import com.google.common.annotations.VisibleForTesting;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jspecify.annotations.Nullable;

import static com.nieslregen.datagen.ModEntityLootTableProvider.MYCELIUM_CHICKEN_DROP;
import static net.minecraft.world.entity.animal.camel.Camel.LAST_POSE_CHANGE_TICK;

public class MyceliumChicken extends Animal implements NeutralMob {

    public final AnimationState sitAnimationState = new AnimationState();
    public final AnimationState sitPoseAnimationState = new AnimationState();
    public final AnimationState sitUpAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();

    private int idleAnimationTimeout = 0;
    private int featherTime;

    // take a look into sniffer for laying eggs

    public MyceliumChicken(EntityType<? extends Animal> type, Level level) {
        super(type, level);
        this.featherTime = this.random.nextInt(6000) + 6000;
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return false;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        return null;
//        return ModEntityTypes.MYCELIUM_CHICKEN.create();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 5)
                .add(Attributes.TEMPT_RANGE, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.SCALE, 1.25);

    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.4));
        this.goalSelector.addGoal(2, new BreedGoal(this, (double)1.0F));
        this.goalSelector.addGoal(3, new TemptGoal(this, (double)1.0F, (i) -> i.is(ItemTags.CHICKEN_FOOD), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, (double)1.0F));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    //ToDo
    private void dropFeathers() { }

    @Override
    public long getPersistentAngerEndTime() {
        return 0;
    }

    @Override
    public void setPersistentAngerEndTime(long endTime) {

    }

    @Override
    public @Nullable EntityReference<LivingEntity> getPersistentAngerTarget() {
        return null;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable EntityReference<LivingEntity> persistentAngerTarget) {

    }

    @Override
    public void startPersistentAngerTimer() {

    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 40;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
        this.sitAnimationState.stop();
        this.sitPoseAnimationState.stop();
//        this.sitUpAnimationState.animateWhen(this.isInPoseTransition() && this.getPoseTime() >= 0L, this.tickCount);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        Level var3 = this.level();
        if (var3 instanceof ServerLevel level) {
            if (this.isAlive() && !this.isBaby() && --this.featherTime <= 0) {

                if (this.dropFromGiftLootTable(level, MYCELIUM_CHICKEN_DROP, this::spawnAtLocation)) {
                    //                    this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                    this.gameEvent(GameEvent.ENTITY_PLACE);
                }

                this.featherTime = this.random.nextInt(6000) + 6000;
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }

//        if (this.isSitting() && this.isInWater()) {
//            this.standUpInstantly();
//        }
    }

    public void standUpInstantly() {
        this.setPose(Pose.STANDING);
        this.gameEvent(GameEvent.ENTITY_ACTION);
        this.resetLastPoseChangeTickToFullStand(this.level().getGameTime());
    }

    private void resetLastPoseChangeTickToFullStand(final long currentTime) {
        this.resetLastPoseChangeTick(Math.max(0L, currentTime - 52L - 1L));
    }

    @VisibleForTesting
    public void resetLastPoseChangeTick(final long syncedPoseTickTime) {
        this.entityData.set(LAST_POSE_CHANGE_TICK, syncedPoseTickTime);
    }

//    public boolean isSitting() {
//        return this.entityData.get(LAST_POSE_CHANGE_TICK) < 0L;
//    }

//    public boolean isInPoseTransition() {
//        long poseTime = this.getPoseTime();
//        return poseTime < (long)(this.isSitting() ? 40 : 52);
//    }

//    public long getPoseTime() {
//        return this.level().getGameTime() - Math.abs((Long)this.entityData.get(LAST_POSE_CHANGE_TICK));
//    }
}

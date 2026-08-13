package com.nieslregen.mob.myceliumchicken;

import com.google.common.annotations.VisibleForTesting;
import com.nieslregen.mob.ModEntityTypes;
import com.nieslregen.mob.ModAnimal;
import com.nieslregen.mob.goals.myceliumchicken.*;
import com.nieslregen.mob.goals.myceliumchicken.BreedGoal;
import com.nieslregen.tags.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.animal.chicken.ChickenSoundVariant;
import net.minecraft.world.entity.animal.chicken.ChickenSoundVariants;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

import static com.nieslregen.datagen.ModEntityLootTableProvider.MYCELIUM_CHICKEN_DROP;

public class MyceliumChicken extends ModAnimal implements NeutralMob {

    private static final EntityDimensions BABY_DIMENSIONS;
    public final AnimationState sitDownAnimationState = new AnimationState();
    public final AnimationState sitPoseAnimationState = new AnimationState();
    public final AnimationState sitUpAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();

    public static final EntityDataAccessor<Long> LAST_POSE_CHANGE_TICK;
    private static final long DEFAULT_LAST_POSE_CHANGE_TICK = 0L;

    private static final int SITDOWN_DURATION_TICKS = 20;
    private static final int STANDUP_DURATION_TICKS = 20;

    public Optional<BlockPos> nestPos = Optional.empty();

    public float flap;
    public float flapSpeed;
    public float oFlapSpeed;
    public float oFlap;
    public float flapping = 1.0F;
    private float nextFlap = 1.0F;

    private boolean carriesEgg = false;
    public boolean carriesStolenEgg = false;
    private int idleAnimationTimeout = 0;
    private int featherTime;

    public int trollCooldown;

    public MyceliumChicken(EntityType<? extends Animal> type, Level level) {
        super(type, level);
        this.moveControl = new MyceliumChickenMoveControl(this);
        resetFeatherTime();
        resetTrollCooldown();
    }

    @Override
    protected EntityDimensions getDefaultDimensions(Pose pose) {
        return this.isBaby() ? BABY_DIMENSIONS : super.getDefaultDimensions(pose);
    }

    public int resetFeatherTime() {
        featherTime = this.random.nextInt(6000) + 6000;
        return featherTime;
    }

    public int resetTrollCooldown() {
        trollCooldown = this.random.nextInt(6000) + 6000;
        return trollCooldown;
    }

    public static boolean checkChickenSpawnRules(final EntityType<MyceliumChicken> type, final LevelAccessor level, final EntitySpawnReason spawnReason, final BlockPos pos, final RandomSource random) {
        return level.getBlockState(pos.below()).is(BlockTags.MOOSHROOMS_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
    }

    static {
        BABY_DIMENSIONS = EntityDimensions.scalable(0.3F, 0.2F).withEyeHeight(0.28125F).withAttachments(EntityAttachments.builder().attach(EntityAttachment.PASSENGER, 0.0F, 0.375F, 0.0F));
        LAST_POSE_CHANGE_TICK = SynchedEntityData.defineId(Camel.class, EntityDataSerializers.LONG);
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RecoverEggGoal(this));
        this.goalSelector.addGoal(2, new ChaseEggThiefGoal(this));
        this.goalSelector.addGoal(3, new DefendNestGoal(this));
        this.goalSelector.addGoal(4, new ReturnToNestGoal(this));
        this.goalSelector.addGoal(5, new IncubateGoal(this));
        this.goalSelector.addGoal(6, new BreedGoal(this, (double)1.0F));
        this.goalSelector.addGoal(7, new LayEggGoal(this, 1f));
        this.goalSelector.addGoal(8, new TemptGoal(this, (double)1.0F, (i) -> i.is(ModTags.Items.MYCELIUM_CHICKEN_FOOD), false));
        this.goalSelector.addGoal(9, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(10, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(11, new TrollGoal(this));
        this.goalSelector.addGoal(12, new WaterAvoidingRandomStrollGoal(this, (double)1.0F));
        this.goalSelector.addGoal(13, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(14, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new DefendNestGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal(this, false));
    }

    public boolean carriesEgg() {
       return carriesEgg;
    }

    public void setCarriesEgg(boolean carriesEgg) {
        this.carriesEgg = carriesEgg;
    }

    @Override
    public boolean isFood(final ItemStack itemStack) {
        return itemStack.is(ModTags.Items.MYCELIUM_CHICKEN_FOOD);
    }

    @Override
    protected @Nullable ResourceKey<LootTable> getDeathLootTable() {
        return null;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        return (AgeableMob) ModEntityTypes.MYCELIUM_CHICKEN.create(level, EntitySpawnReason.BREEDING);
    }

    public boolean canFallInLove() {
        return super.canFallInLove() && !this.carriesEgg();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 5)
                .add(Attributes.TEMPT_RANGE, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_DAMAGE, 3)
                .add(Attributes.FOLLOW_RANGE, 25)
                .add(Attributes.ATTACK_SPEED, .5)
                .add(Attributes.SCALE, 1.25);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed += (this.onGround() ? -1.0F : 4.0F) * 0.3F;
        this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);
        if (!this.onGround() && this.flapping < 1.0F) {
            this.flapping = 1.0F;
        }

        this.flapping *= 0.9F;
        Vec3 movement = this.getDeltaMovement();
        if (!this.onGround() && movement.y < (double)0.0F) {
            this.setDeltaMovement(movement.multiply((double)1.0F, 0.6, (double)1.0F));
        }

        this.flap += this.flapping * 2.0F;
        Level var3 = this.level();
        if (var3 instanceof ServerLevel level) {
            trollCooldown--;
            if (this.isAlive() && !this.isBaby() && --this.featherTime <= 0) {

                if (this.dropFromGiftLootTable(level, MYCELIUM_CHICKEN_DROP, this::spawnAtLocation)) {
                    this.gameEvent(GameEvent.ENTITY_PLACE);
                }
                resetFeatherTime();
            }
        }
    }



    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }

        if (this.isVisuallySitting() && this.isInWater()) {
            this.standUp();
        }
    }

    //// Animation

    private void setupAnimationStates() {
        // idle
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 40;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }


        if (this.isVisuallySitting()) {
            this.sitUpAnimationState.stop();
            if (this.isVisuallySittingDown()) {
                this.sitDownAnimationState.startIfStopped(this.tickCount);
                this.sitPoseAnimationState.stop();
            } else {
                this.sitDownAnimationState.stop();
                this.sitPoseAnimationState.startIfStopped(this.tickCount);
            }
        } else {
            this.sitDownAnimationState.stop();
            this.sitPoseAnimationState.stop();
            this.sitUpAnimationState.animateWhen(this.isInPoseTransition() && this.getPoseTime() >= 0L, this.tickCount);
        }

    }

    public void standUp() {
        if (isSitting()) {
            this.setPose(Pose.STANDING);
            this.gameEvent(GameEvent.ENTITY_ACTION);
            this.resetLastPoseChangeTickToFullStand(this.level().getGameTime());
        }
    }

    public void sitDown() {
        if (!isSitting()) {
            this.setPose(Pose.SITTING);
            this.gameEvent(GameEvent.ENTITY_ACTION);
            this.resetLastPoseChangeTickToFullStand(this.level().getGameTime());
        }
    }

    private boolean isSitting() {
        return this.entityData.get(LAST_POSE_CHANGE_TICK) < 0;
    }

    private boolean isVisuallySitting() {
        return this.getPoseTime() < 0L != this.isSitting();
    }

    public long getPoseTime() {
        return this.level().getGameTime() - Math.abs((Long)this.entityData.get(LAST_POSE_CHANGE_TICK));
    }

    public boolean isInPoseTransition() {
        long poseTime = this.getPoseTime();
        return poseTime < (this.isSitting() ? SITDOWN_DURATION_TICKS : STANDUP_DURATION_TICKS);
    }

    private void resetLastPoseChangeTickToFullStand(final long currentTime) {
        this.resetLastPoseChangeTick(Math.max(0L, currentTime - STANDUP_DURATION_TICKS - 1L));
    }

    private boolean isVisuallySittingDown() {
        return this.isSitting() && this.getPoseTime() < SITDOWN_DURATION_TICKS && this.getPoseTime() >= 0L;
    }

    @VisibleForTesting
    public void resetLastPoseChangeTick(final long syncedPoseTickTime) {
        this.entityData.set(LAST_POSE_CHANGE_TICK, syncedPoseTickTime);
    }

    protected boolean isFlapping() {
        return this.flyDist > this.nextFlap;
    }

    protected void onFlap() {
        this.nextFlap = this.flyDist + this.flapSpeed / 2.0F;
    }

    public List<LivingEntity> getTargets(MyceliumChicken chicken, int radius) {

        BlockPos pos = nestPos.orElseGet(chicken::getOnPos);

        AABB area = new AABB(pos).inflate(radius);

        return chicken.level().getEntitiesOfClass(
                LivingEntity.class,
                area,
                entity -> entity != chicken
                        && entity.isAlive()
                        && !(entity instanceof MyceliumChicken)
        );
    }

    public boolean canChickenChangePose() {
        return this.wouldNotSuffocateAtTargetPose(this.isSitting() ? Pose.STANDING : Pose.SITTING);
    }

    private static class MyceliumChickenMoveControl<T extends MyceliumChicken> extends MoveControl {

        public MyceliumChickenMoveControl(Mob mob) {
            super(mob);
        }

        @Override
        public void tick() {
            MyceliumChicken chicken = (MyceliumChicken) mob;
            if (this.operation == Operation.MOVE_TO
                    && chicken.isSitting()
                    && chicken.canChickenChangePose()
                    && !chicken.isLeashed()
                    && !chicken.isInPoseTransition()
            ) {
                chicken.standUp();
            }

            super.tick();
        }
    }



    //// Sound:

    public ChickenSoundVariant.ChickenSoundSet getSoundVariant() {
        ChickenSoundVariant soundSet = SoundEvents.CHICKEN_SOUNDS.get(ChickenSoundVariants.SoundSet.CLASSIC);
        return isBaby() ? soundSet.babySounds() : soundSet.adultSounds();
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return getSoundVariant().deathSound().value();
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return getSoundVariant().ambientSound().value();
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) {
        return getSoundVariant().hurtSound().value();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockState) {
        this.playSound(getSoundVariant().stepSound().value());
    }



    // ToDo

    @Override
    public long getPersistentAngerEndTime() {
        return 0;
    }

    @Override
    public void setPersistentAngerEndTime(long endTime) { }

    @Override
    public @Nullable EntityReference<LivingEntity> getPersistentAngerTarget() {
        return null;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable EntityReference<LivingEntity> persistentAngerTarget) { }

    @Override
    public void startPersistentAngerTimer() { }


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(LAST_POSE_CHANGE_TICK, 0L);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);

        output.putLong("LastPoseTick", this.entityData.get(LAST_POSE_CHANGE_TICK));

        output.putBoolean("carriesEgg", this.carriesEgg);
        output.putBoolean("carriesStolenEgg", this.carriesStolenEgg);

        output.putInt("idleAnimationTimeout", this.idleAnimationTimeout);
        output.putInt("featherTime", this.featherTime);
        output.putInt("trollCooldown", this.trollCooldown);

        if (nestPos.isPresent()) {
            output.putBoolean("hasNest", true);
            output.putInt("nestX", nestPos.get().getX());
            output.putInt("nestY", nestPos.get().getY());
            output.putInt("nestZ", nestPos.get().getZ());
        } else {
            output.putBoolean("hasNest", false);
        }
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.carriesEgg = input.getBooleanOr("carriesEgg", false);
        this.carriesStolenEgg = input.getBooleanOr("carriesStolenEgg", false);

        this.idleAnimationTimeout = input.getIntOr("idleAnimationTimeout", 0);
        this.featherTime = input.getIntOr("featherTime", resetFeatherTime());
        this.trollCooldown = input.getIntOr("trollCooldown", resetTrollCooldown());

        boolean hasNest = input.getBooleanOr("hasNest", false);
        if (hasNest) {
            nestPos = Optional.of(new BlockPos(
                    input.getIntOr("nestX",0),
                    input.getIntOr("nestY",0),
                    input.getIntOr("nestZ",0)
            ));
        } else {
            nestPos = Optional.empty();
        }

        long poseTick = input.getLongOr("LastPoseTick", 0L);
        if (poseTick < 0L) {
            this.setPose(Pose.SITTING);
        }

        this.resetLastPoseChangeTick(poseTick);
    }

}


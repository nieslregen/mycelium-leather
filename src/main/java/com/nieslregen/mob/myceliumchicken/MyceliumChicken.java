package com.nieslregen.mob.myceliumchicken;

import com.google.common.annotations.VisibleForTesting;
import com.nieslregen.block.ModBlocks;
import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.fox.Fox;
import net.minecraft.world.entity.animal.polarbear.PolarBear;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TurtleEggBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gamerules.GameRules;
import org.jspecify.annotations.Nullable;

import static com.nieslregen.datagen.ModEntityLootTableProvider.MYCELIUM_CHICKEN_DROP;
import static net.minecraft.world.entity.animal.camel.Camel.LAST_POSE_CHANGE_TICK;

public class MyceliumChicken extends Animal implements NeutralMob {

    public final AnimationState sitAnimationState = new AnimationState();
    public final AnimationState sitPoseAnimationState = new AnimationState();
    public final AnimationState sitUpAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();

    private boolean hasEgg = false;
    private int idleAnimationTimeout = 0;
    private int featherTime;


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
                .add(Attributes.ATTACK_DAMAGE, 3)
                .add(Attributes.FOLLOW_RANGE, 25)
                .add(Attributes.ATTACK_SPEED, .5)
                .add(Attributes.SCALE, 1.25);

    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        // They fear water
        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, (double)1.0F));
        this.goalSelector.addGoal(2, new MyceliumChicken.DefendEggGoal());
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 2.5D, false));
        this.goalSelector.addGoal(4, new MyceliumChickenBreedGoal(this, (double)1.0F));
        this.goalSelector.addGoal(4, new MyceliumChicken.LayEggGoal(this, 1f));
        this.goalSelector.addGoal(5, new TemptGoal(this, (double)1.0F, (i) -> i.is(ItemTags.CHICKEN_FOOD), false));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new MyceliumChicken.EggThiefGoal(this));
//        this.targetSelector.addGoal(2, new MyceliumChicken.ChickenHurtByTargetGoal(this));
//        this.targetSelector.addGoal(3, new MyceliumChicken.ChickenAttackPlayersGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Animal.class, 10, true, true, (target, level) -> this.isAngryAt(target, level) &&!this.isBaby()));
        this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal(this, false));
    }

    public boolean hasEgg() {
       return hasEgg;
    }

    private void setHasEgg(boolean hasEgg) {
        this.hasEgg = hasEgg;
    }

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

    private static class MyceliumChickenBreedGoal extends BreedGoal {
        private final MyceliumChicken thisEntity;

        public MyceliumChickenBreedGoal(final MyceliumChicken chicken, double speedModifier) {
            super(chicken, speedModifier);
            this.thisEntity = chicken;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && !this.thisEntity.hasEgg();
        }

        @Override
        protected void breed() {
            ServerPlayer loveCause = this.animal.getLoveCause();
            if (loveCause == null && this.partner.getLoveCause() != null) {
                loveCause = this.partner.getLoveCause();
            }

            if (loveCause != null) {
                loveCause.awardStat(Stats.ANIMALS_BRED);
                CriteriaTriggers.BRED_ANIMALS.trigger(loveCause, this.animal, this.partner, (AgeableMob)null);
            }
            this.thisEntity.setHasEgg(true);
            this.animal.setAge(6000);
            this.partner.setAge(6000);
            this.animal.resetLove();
            this.partner.resetLove();
            RandomSource random = this.animal.getRandom();
            if ((Boolean)getServerLevel(this.level).getGameRules().get(GameRules.MOB_DROPS)) {
                this.level.addFreshEntity(new ExperienceOrb(this.level, this.animal.getX(), this.animal.getY(), this.animal.getZ(), random.nextInt(7) + 1));
            }

        }
    }

    private class DefendEggGoal extends Goal {

        @Override
        public boolean canUse() {
            return false;
        }
    }

    private class LayEggGoal extends MoveToBlockGoal {
        private final MyceliumChicken chicken;

        public LayEggGoal(final MyceliumChicken chicken, double speedModifier) {
            super(chicken, speedModifier, 16);
            this.chicken = chicken;
        }

        @Override
        public boolean canUse() {
            return this.chicken.hasEgg(); // ToDo
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse(); // ToDo
        }

        @Override
        public void tick() {
            super.tick();
            BlockPos chickenPos = this.chicken.blockPosition();
            if (this.isReachedTarget()) {
                Level level = this.chicken.level();
                level.playSound((Entity) null, chickenPos, SoundEvents.CHICKEN_EGG, SoundSource.BLOCKS, 0.3F, 0.9F + level.getRandom().nextFloat() * 0.2F);

                BlockPos eggPos = this.blockPos.above();
//                BlockState eggState = (BlockState) ModBlocks.MYCELIUM_CHICKEN_NEST.defaultBlockState().setValue(MyceliumChickenNest.EGGS, this.chicken.random.nextInt(4) + 1);

                this.chicken.setHasEgg(false);
//                this.chicken.setLayingEgg(false);
                this.chicken.setInLoveTime(600);
            }
        }

        @Override
        protected boolean isValidTarget(LevelReader level, BlockPos pos) {
            return level.isEmptyBlock(pos.above()) && MyceliumChickenNest.isMycelium(level, pos);
        }
    }

    private class EggThiefGoal extends HurtByTargetGoal {

        public EggThiefGoal(PathfinderMob mob, Class<?>... ignoreDamageFromTheseTypes) {
            super(mob, ignoreDamageFromTheseTypes);
        }
    }
}

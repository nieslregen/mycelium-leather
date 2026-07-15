package com.nieslregen.mob.myceliumchicken;

import com.google.common.annotations.VisibleForTesting;
import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.block.ModBlocks;
import com.nieslregen.items.ModItems;
import com.nieslregen.mob.ModEntityTypes;
import com.nieslregen.tags.ModTags;
import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.fox.Fox;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

import static com.nieslregen.datagen.ModEntityLootTableProvider.MYCELIUM_CHICKEN_DROP;
import static net.minecraft.world.entity.animal.camel.Camel.LAST_POSE_CHANGE_TICK;

public class MyceliumChicken extends Animal implements NeutralMob {

    public final AnimationState sitAnimationState = new AnimationState();
    public final AnimationState sitPoseAnimationState = new AnimationState();
    public final AnimationState sitUpAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();

    public Optional<BlockPos> nestPos = Optional.empty();

    private boolean carriesEgg = false;
    private boolean carriesStolenEgg = false;
    private int idleAnimationTimeout = 0;
    private int featherTime;


    public MyceliumChicken(EntityType<? extends Animal> type, Level level) {
        super(type, level);
        this.featherTime = this.random.nextInt(6000) + 6000;
    }

    @Override
    public boolean isFood(final ItemStack itemStack) {
        return itemStack.is(ItemTags.CHICKEN_FOOD);
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
    // ToDo: Meal of Mushroom Isles gives you effects: Mining Speed increase, ... and SuspicouseEggEffect(dropping Susipicous eggs from time to time) for 10 Minutes,
    // ToDo: sitting animation
    // ToDo: trolling
    // ToDo: different feather drops

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RecoverEggGoal(this));
        this.goalSelector.addGoal(2, new ChaseEggThiefGoal(this));
        this.goalSelector.addGoal(3, new DefendNestGoal(this));
        this.goalSelector.addGoal(4, new ReturnToNestGoal(this));
        this.goalSelector.addGoal(5, new IncubateGoal(this));
        this.goalSelector.addGoal(6, new MyceliumChickenBreedGoal(this, (double)1.0F));
        this.goalSelector.addGoal(7, new MyceliumChicken.LayEggGoal(this, 1f));
        this.goalSelector.addGoal(8, new TemptGoal(this, (double)1.0F, (i) -> i.is(ModTags.Items.MYCELIUM_CHICKEN_FOOD), false));
        this.goalSelector.addGoal(9, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(10, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(11, new TrollGoal(this));
        this.goalSelector.addGoal(12, new WaterAvoidingRandomStrollGoal(this, (double)1.0F));
        this.goalSelector.addGoal(13, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(14, new RandomLookAroundGoal(this));

//        this.targetSelector.addGoal(1, new MyceliumChicken.ChaseEggThiefGoal(this));
        this.targetSelector.addGoal(2, new MyceliumChicken.DefendNestGoal(this));
//        this.targetSelector.addGoal(3, new MyceliumChicken.WarnGoal(this));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, Animal.class, 10, true, true, (target, level) -> this.isAngryAt(target, level) &&!this.isBaby()));
        this.targetSelector.addGoal(6, new ResetUniversalAngerTargetGoal(this, false));
    }

    public boolean carriesEgg() {
       return carriesEgg;
    }

    private void setCarriesEgg(boolean carriesEgg) {
        this.carriesEgg = carriesEgg;
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
            return super.canUse() && !this.thisEntity.carriesEgg();
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
            this.thisEntity.setCarriesEgg(true);
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

    private class IncubateGoal extends Goal {
        private final MyceliumChicken chicken;

        private IncubateGoal(MyceliumChicken chicken) {
            this.chicken = chicken;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public void start() {
            super.start();
            MyceliumLeatherMod.LOGGER.info("Start Incubating");
            if (chicken.nestPos.isPresent()){
                BlockState newState = level().getBlockState(chicken.nestPos.get()).setValue(MyceliumChickenNestBlock.IS_INCUBATING, true);
                chicken.level().setBlockAndUpdate(nestPos.get(), newState);

                chicken.level().setBlockAndUpdate(chicken.nestPos.get(), newState);

                if (chicken.carriesStolenEgg) {
                    chicken.level().setBlockAndUpdate(
                            chicken.nestPos.get(),
                            chicken.level().getBlockState(nestPos.get()).setValue(MyceliumChickenNestBlock.HAS_EGG, true)
                    );
                    chicken.carriesStolenEgg = false;
                }

                chicken.getNavigation().stop();
            }
        }


        @Override
        public void stop() {
            MyceliumLeatherMod.LOGGER.info("End Incubating");
            super.stop();
            if (chicken.nestPos.isPresent()){
                BlockState newState = level().getBlockState(chicken.nestPos.get()).setValue(MyceliumChickenNestBlock.IS_INCUBATING, false);
                chicken.level().setBlockAndUpdate(nestPos.get(), newState);
            }
        }

        @Override
        public void tick() {
            super.tick();
            if (chicken.nestPos.isEmpty()) {
                return;
            }

            BlockPos pos = chicken.nestPos.get();

            if (chicken.getNavigation().isDone()) {
                double x = pos.getX() + 0.5;
                double z = pos.getZ() + 0.5;

                chicken.getMoveControl().setWantedPosition(
                        x,
                        pos.getY(),
                        z,
                        0.15
                );
            }
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && chicken.nestPos.isPresent() && chicken.nestPos.get().closerToCenterThan(chicken.position(), 1.5f);
        }

        @Override
        public boolean canUse() {
            return chicken.nestPos.isPresent() && chicken.nestPos.get().closerToCenterThan(chicken.position(), 1.5f);
        }
    }

    private class ReturnToNestGoal extends Goal {

        private final MyceliumChicken chicken;

        public ReturnToNestGoal(MyceliumChicken chicken) {
            this.chicken = chicken;
        }

        @Override
        public boolean canUse() {
            return chicken.nestPos.isPresent()
                    && !chicken.isAngry()
                    && !chicken.nestPos.get()
                    .closerToCenterThan(chicken.position(), 1.75);
        }

        @Override
        public void start() {
            MyceliumLeatherMod.LOGGER.info("Start ReturnToNestGoal");

            BlockPos pos = chicken.nestPos.get();

            chicken.getNavigation().moveTo(
                    pos.getX() + 0.5,
                    pos.getY(),
                    pos.getZ() + 0.5,
                    1.25
            );
        }

        @Override
        public boolean canContinueToUse() {
            return chicken.nestPos.isPresent()
                    && !chicken.nestPos.get()
                    .closerToCenterThan(chicken.position(), 0.75);
        }

        @Override
        public void stop() {
            MyceliumLeatherMod.LOGGER.info("End ReturnToNestGoal");
            chicken.getNavigation().stop();
        }

        @Override
        public void tick() {
            if (chicken.nestPos.isPresent()) {
                BlockPos pos = chicken.nestPos.get();

                if (chicken.getNavigation().isDone()) {
                    chicken.getNavigation().moveTo(
                            pos.getX() + 0.5,
                            pos.getY(),
                            pos.getZ() + 0.5,
                            1.25
                    );
                }
            }
        }
    }

    private class DefendNestGoal extends MeleeAttackGoal {

        private final MyceliumChicken chicken;

        DefendNestGoal(MyceliumChicken chicken) {
            super(chicken, 1.5f, false);
            this.chicken = chicken;
        }

        private List<LivingEntity> getTargets() {
            if (chicken.nestPos.isEmpty()) {
                return Collections.emptyList();
            }

            AABB area = new AABB(chicken.nestPos.get()).inflate(3);

            return level().getEntitiesOfClass(
                    LivingEntity.class,
                    area,
                    entity -> entity != chicken
                            && entity.isAlive()
                            && !(entity instanceof MyceliumChicken)
            );
        }


        @Override
        public boolean canUse() {
            List<LivingEntity> targets = getTargets();

            if (!targets.isEmpty()) {
                chicken.setTarget(targets.getFirst());
                return super.canUse()
                        && !chicken.carriesStolenEgg;
            }
            return false;
        }


        @Override
        public boolean canContinueToUse() {
            return chicken.getTarget() != null
                    && chicken.getTarget().isAlive()
                    && getTargets().contains(chicken.getTarget())
                    && !chicken.carriesStolenEgg
                    && super.canContinueToUse();
        }


        @Override
        public void stop() {
            super.stop();
            chicken.setTarget(null);
            chicken.stopBeingAngry();
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
            return this.chicken.carriesEgg() && super.canUse(); // ToDo
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse(); // ToDo as long as the animation is not finished
        }

        @Override
        public double acceptedDistance() {
            return 2;
        }

        @Override
        public void tick() {
            super.tick();
            BlockPos chickenPos = this.chicken.blockPosition();
            if (this.isReachedTarget()) {
                Level level = this.chicken.level();
                level.playSound((Entity) null, chickenPos, SoundEvents.CHICKEN_EGG, SoundSource.BLOCKS, 0.3F, 0.9F + level.getRandom().nextFloat() * 0.2F);

                BlockPos eggPos = this.blockPos.above();
                BlockState eggState = (BlockState) ModBlocks.MYCELIUM_CHICKEN_NEST.defaultBlockState().setValue(com.nieslregen.mob.myceliumchicken.MyceliumChickenNestBlock.HAS_EGG, true);
                level.setBlock(eggPos, eggState, Block.UPDATE_ALL);
                level.gameEvent(GameEvent.BLOCK_PLACE, eggPos, GameEvent.Context.of(this.chicken, eggState));

                this.chicken.setCarriesEgg(false);
                this.chicken.setInLoveTime(600);
                this.chicken.nestPos = Optional.of(eggPos);
            }
        }

        @Override
        protected boolean isValidTarget(LevelReader level, BlockPos pos) {
            return level.isEmptyBlock(pos.above()) && MyceliumChickenNestBlock.isMycelium(level, pos);
        }

    }

    private class RecoverEggGoal extends Goal {
        private final MyceliumChicken chicken;

        public RecoverEggGoal(MyceliumChicken chicken) {
            this.chicken = chicken;
        }

        @Override
        public void start() {
            super.start();
            List<ItemEntity> items = getNearbyItems();
            if (!items.isEmpty()) {
                chicken.getNavigation().moveTo((Entity) items.getFirst(), (double) 1.2F);
            }
        }

        private List<ItemEntity> getNearbyItems() {
            return chicken
                    .level().getEntitiesOfClass(
                            ItemEntity.class,
                            chicken.getBoundingBox().inflate(8.0f, 8.0f, 8.0f),
                            (e) -> e.getItem().is(ModItems.getResourceKey(ModItems.MYCELIUM_CHICKEN_EGG)) && e.isAlive()
                    );
        }

        @Override
        public boolean canUse() {
            if (chicken.nestPos.isPresent()) {
                BlockPos nest = this.chicken.nestPos.get();
                BlockEntity entity = chicken.level().getBlockEntity(nest);

                if (entity instanceof MyceliumChickenNestEntity nestEntity) {
                    return !nestEntity.hasEgg(chicken.level().getBlockState(nest)) && !chicken.carriesStolenEgg;
                }
            }
            return false;
        }

        @Override
        public void tick() {
            super.tick();
            List<ItemEntity> items = getNearbyItems();
            if (!items.isEmpty()) {
                chicken.getNavigation().moveTo((Entity) items.getFirst(), (double) 1.2F);
                if (chicken.blockPosition().closerToCenterThan(items.getFirst().position(), 1F)) {
                    chicken.carriesStolenEgg = true;

                    items.getFirst().getItem().shrink(1);
                }
            }
        }
    }


    private class ChaseEggThiefGoal extends MeleeAttackGoal {
        MyceliumChicken chicken;

        public ChaseEggThiefGoal(MyceliumChicken chicken) {
            super(chicken, 2.0f, true);
            this.chicken = chicken;
        }

        private boolean isAngryAtPlayer() {
            if (chicken.nestPos.isPresent()) {
                BlockPos p = this.chicken.nestPos.get();
                BlockState state = this.chicken.level().getBlockState(p);

                return chicken.level().getBlockEntity(p) instanceof MyceliumChickenNestEntity nestEntity
                        && nestEntity.getThief().isPresent()
                        && !nestEntity.hasEgg(state)
                        && !chicken.carriesStolenEgg;
            }
            return false;
        }

        @Override
        public boolean canUse() {
            return isAngryAtPlayer() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return isAngryAtPlayer() && super.canContinueToUse();
        }

        @Override
        public void start() {
            MyceliumLeatherMod.LOGGER.info("Start Egg Thief Goal");
            super.start();
            if  (chicken.nestPos.isPresent()) {
                if (chicken.level().getBlockEntity(chicken.nestPos.get()) instanceof MyceliumChickenNestEntity nestEntity && nestEntity.getThief().isPresent()) {
                    chicken.setTarget(nestEntity.getThief().get());
                }
            }
        }

        @Override
        public void stop() {
            MyceliumLeatherMod.LOGGER.info("Stop Egg Thief Goal");
            super.stop();
            chicken.setTarget(null);
            chicken.stopBeingAngry();
        }
    }

    private class TrollGoal extends Goal {
        private final MyceliumChicken chicken;

        private TrollGoal(MyceliumChicken chicken) {
            this.chicken = chicken;
        }

        @Override
        public boolean canUse() {
            return false;
        }
    }
}


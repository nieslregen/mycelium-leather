package com.nieslregen.mob.myceliumsquirrel;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.items.ModItems;
import com.nieslregen.mob.HollowUser;
import com.nieslregen.mob.ModPoiTypes;
import com.nieslregen.mob.goals.squirrel.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
import net.minecraft.world.entity.monster.spider.Spider;
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

    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID;

    public int timeUntilResting;
    public int digTimer;
    public boolean needsToRest = false;
    public boolean carriesBaby = false;

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
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(DATA_FLAGS_ID, (byte)0);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new WallClimberNavigation(this, level);
    }

    @Override
    public boolean onClimbable() {
        return this.isClimbing();
    }

    public boolean isClimbing() {
        return ((Byte)this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }

    public void setClimbing(final boolean value) {
        byte flags = (Byte) this.entityData.get(DATA_FLAGS_ID);
        if (value) {
            flags = (byte) (flags | 1);
        } else {
            flags = (byte) (flags & -2);
        }

        this.entityData.set(DATA_FLAGS_ID, flags);
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

    public int resetTimeUntilResting() {
        timeUntilResting = this.random.nextInt(6000) + 6000;;
        needsToRest = false;
        return timeUntilResting;
    }

    public int resetDigTimer() {
        digTimer = this.random.nextInt(6000) + 6000;
        return digTimer;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            this.setClimbing(this.horizontalCollision);
        }
        MyceliumLeatherMod.LOGGER.info("is climbing: " + this.isClimbing());
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

    static {
        DATA_FLAGS_ID = SynchedEntityData.defineId(MyceliumSquirrel.class, EntityDataSerializers.BYTE);
    }

    public static final String TIME_UNTIL_RESTING_IDENTIFIER = "timeUntilResting";
    public static final String DIG_TIMER_IDENTIFIER = "digTimer";
    public static final String NEEDS_TO_REST_IDENTIFIER = "needsToRest";
    public static final String CARRIES_BABY_IDENTIFIER = "carriesBaby";

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt(TIME_UNTIL_RESTING_IDENTIFIER, timeUntilResting);
        output.putInt(DIG_TIMER_IDENTIFIER, digTimer);

        output.putBoolean(NEEDS_TO_REST_IDENTIFIER, needsToRest);
        output.putBoolean(CARRIES_BABY_IDENTIFIER, carriesBaby);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);

        input.getIntOr(TIME_UNTIL_RESTING_IDENTIFIER, resetTimeUntilResting());
        input.getIntOr(DIG_TIMER_IDENTIFIER, resetDigTimer());


    }
}

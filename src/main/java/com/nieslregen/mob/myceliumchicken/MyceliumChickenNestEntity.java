package com.nieslregen.mob.myceliumchicken;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.block.ModBlockEntities;
import com.nieslregen.mob.ModEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.Optional;

public class MyceliumChickenNestEntity extends BlockEntity {

    private final int MAX_EGG_AGE = 120 * 20;
    private final int MAX_TEMPERATURE = 500;
    private int eggAgeInTicks = 0;
    private int currentTemperature;
    private int emptyNestAge = 0;

    private Optional<LivingEntity> thief = Optional.empty();

    public boolean hasEgg(BlockState state) {
        return state.getValue(com.nieslregen.mob.myceliumchicken.MyceliumChickenNestBlock.HAS_EGG);
    }

    public MyceliumChickenNestEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.MYCELIUM_CHICKEN_NEST_ENTITY, worldPosition, blockState);

        if (blockState.getValue(MyceliumChickenNestBlock.HAS_EGG)) {
            currentTemperature = MAX_TEMPERATURE;
            emptyNestAge = 0;
        }
    }

    public static void serverTick(final Level level, final BlockPos blockPos, final BlockState blockState, final MyceliumChickenNestEntity entity) {

        if (level.isClientSide()) { return; }

        if (entity.hasEgg(blockState)) {
            MyceliumLeatherMod.LOGGER.info("Age: {}", entity.eggAgeInTicks);
            boolean isEggAlive = entity.regulateEggTemperature(
                    blockState.getValue(MyceliumChickenNestBlock.IS_INCUBATING)
            );

            if (isEggAlive && (entity.eggAgeInTicks >= entity.MAX_EGG_AGE)) {
                entity.hatchEgg(blockState, blockPos);
                entity.takeEgg(level, blockPos);
                entity.breakNest(level, blockPos, blockState, entity);
            }

            if (isEggAlive) {
                entity.eggAgeInTicks = entity.eggAgeInTicks + 1;
                entity.emptyNestAge = 0;
            } else {
                entity.takeEgg(level, blockPos);
                entity.breakNest(level, blockPos, blockState, entity);
            }
        } else {
            if (entity.emptyNestAge >= entity.MAX_EGG_AGE / 2) {
                entity.takeEgg(level, blockPos);
                entity.breakNest(level, blockPos, blockState, entity);
            } else {
                entity.emptyNestAge = entity.emptyNestAge + 1;
            }
        }
        entity.setChanged();
    }

    public void resetThief() {
        thief = Optional.empty();
    }

    public Optional<LivingEntity> getThief() {
        if (thief.isPresent()) {
            if (thief.get().isAlive()) {
                return thief;
            }

            thief = Optional.empty();
            return thief;
        }
        return Optional.empty();
    }

    public void stealEgg(final Level level, final BlockPos blockPos, final LivingEntity livingEntity) {
        thief = Optional.of(livingEntity);
        takeEgg(level, blockPos);
    }

    public void takeEgg(final Level level, final BlockPos blockPos) {
        BlockState state = level.getBlockState(blockPos)
                .setValue(MyceliumChickenNestBlock.IS_INCUBATING, false)
                .setValue(MyceliumChickenNestBlock.HAS_EGG, false);
        level.setBlockAndUpdate(blockPos, state);
    }

    public void breakNest(final Level level, final BlockPos blockPos, final BlockState blockState, final MyceliumChickenNestEntity entity) {
        entity.currentTemperature = 0;
        entity.eggAgeInTicks = 0;
        level.destroyBlock(blockPos, false);
    }

    public void hatchEgg(BlockState state, BlockPos pos) {
        MyceliumChicken chicken = (MyceliumChicken) ModEntityTypes.MYCELIUM_CHICKEN.create(level, EntitySpawnReason.BREEDING);
        if (chicken != null) {;
            chicken.setAge(-24000);
            chicken.snapTo(pos.getX(), pos.getY(), pos.getZ(), 0.0F, 0.0F);
            level.addFreshEntity(chicken);
        }
    }

    public boolean regulateEggTemperature(boolean isIncubating) {
        if (currentTemperature <= 0) {
            return false;
        }

        if (isIncubating) {
            if (currentTemperature >= MAX_TEMPERATURE) {
                return true;
            }
            currentTemperature++;
        } else {
            currentTemperature--;
        }
        return true;
    }

    public static final String EGG_AGE_IDENTIFIER = "eggAgeInTicks";
    public static final String TEMPERATURE_IDENTIFIER = "temperature";
    public static final String NEST_AGE_IDENTIFIER = "nestAge";

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

        eggAgeInTicks = input.getIntOr(EGG_AGE_IDENTIFIER, 0);
        currentTemperature = input.getIntOr(TEMPERATURE_IDENTIFIER, MAX_TEMPERATURE);
        emptyNestAge = input.getIntOr(NEST_AGE_IDENTIFIER, 0);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);

        output.putInt(EGG_AGE_IDENTIFIER, eggAgeInTicks);
        output.putInt(TEMPERATURE_IDENTIFIER, currentTemperature);
        output.putInt(NEST_AGE_IDENTIFIER, emptyNestAge);
    }
}

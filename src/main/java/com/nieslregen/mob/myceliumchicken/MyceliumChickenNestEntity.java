package com.nieslregen.mob.myceliumchicken;

import com.nieslregen.block.ModBlockEntities;
import com.nieslregen.mob.ModEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MyceliumChickenNestEntity extends BlockEntity {

    private final int MAX_EGG_AGE = 120 * 20;
    private final int MAX_TEMPERATURE = 100;
    private int eggAgeInTicks = 0;
    private int currentTemperature = 0;
    private boolean hasEgg = false;

    private MyceliumChicken parent;

    public boolean layEgg() {
        if (hasEgg) {
            return false;
        }
        hasEgg = true;
        currentTemperature = MAX_TEMPERATURE;
        return true;
    }

    public void breakEgg() {
        removeEgg();
    }

    public void removeEgg() {
        hasEgg = false;
        currentTemperature = 0;
        eggAgeInTicks = 0;
    }

    public boolean isIncubating() {
        if (parent != null) {
            // ToDo: when sitting on egg -> incubating
            return true;
        }
        return false;
    }

    public MyceliumChickenNestEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.MYCELIUM_CHICKEN_NEST_ENTITY, worldPosition, blockState);
    }

    public static void serverTick(final Level level, final BlockPos blockPos, final BlockState blockState, final MyceliumChickenNestEntity entity) {
        if (level.isClientSide()) { return; }

        if (entity.hasEgg) {
            boolean isEggAlive = entity.regulateEggTemperature(entity.isIncubating());

            if (isEggAlive && (entity.eggAgeInTicks >= entity.MAX_EGG_AGE)) {
                entity.hatchEgg(blockPos);
            }

            if (isEggAlive) {
                entity.eggAgeInTicks = entity.eggAgeInTicks + 1;
            }
        }
    }

    public void hatchEgg(BlockPos pos) {
        removeEgg();
        MyceliumChicken chicken = (MyceliumChicken) ModEntityTypes.MYCELIUM_CHICKEN.create(level, EntitySpawnReason.BREEDING);
        if (chicken != null) {
            chicken.setAge(-24000);
            chicken.snapTo(pos.getX(), pos.getY(), pos.getZ(), 0.0F, 0.0F);
            level.addFreshEntity(chicken);
        }
    }

    public boolean regulateEggTemperature(boolean isIncubating) {
        if (currentTemperature <= 0) {
            breakEgg();
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
}

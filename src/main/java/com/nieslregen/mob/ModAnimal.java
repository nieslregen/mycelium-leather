package com.nieslregen.mob;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

import static com.nieslregen.datagen.ModEntityLootTableProvider.MYCELIUM_CHICKEN_DROP;

public abstract class ModAnimal extends Animal {
    protected ModAnimal(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Override
    protected abstract @Nullable SoundEvent getDeathSound();

    @Override
    protected abstract @Nullable SoundEvent getHurtSound(DamageSource source);

    @Override
    protected abstract SoundEvent getAmbientSound();

    @Override
    protected abstract void playStepSound(BlockPos pos, BlockState blockState);

    protected abstract @Nullable ResourceKey<LootTable> getDeathLootTable();

    @Override
    protected void dropAllDeathLoot(ServerLevel level, DamageSource source) {
        super.dropAllDeathLoot(level, source);
        if (level() instanceof ServerLevel serverLevel && getDeathLootTable() != null) {
            dropFromGiftLootTable(serverLevel, getDeathLootTable(), this::spawnAtLocation);
        }
    }
}

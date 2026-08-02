package com.nieslregen.mob;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public abstract class SetupAnimal extends Animal {
    protected SetupAnimal(EntityType<? extends Animal> type, Level level) {
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
}

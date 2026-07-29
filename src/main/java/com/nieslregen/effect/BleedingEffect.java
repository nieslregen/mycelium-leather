package com.nieslregen.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.PoisonMobEffect;
import net.minecraft.world.entity.LivingEntity;

public class BleedingEffect extends PoisonMobEffect {
    public BleedingEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    public boolean applyEffectTick(final ServerLevel level, final LivingEntity mob, final int amplification) {
        mob.hurtServer(level, mob.damageSources().magic(), 1.0F);
        return true;
    }

}

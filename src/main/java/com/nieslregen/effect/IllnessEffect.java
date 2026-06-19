package com.nieslregen.effect;


import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class IllnessEffect extends MobEffect {

    protected IllnessEffect(MobEffectCategory category, int color) {//, ParticleOptions particleOptions) {
        super(category, color); //, particleOptions);
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity mob, int amplification) {
        // random
        mob.hurtServer(serverLevel, mob.damageSources().magic(), 1);
        mob.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 200));
        return super.applyEffectTick(serverLevel, mob, amplification);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification) {
        return super.shouldApplyEffectTickThisTick(tickCount, amplification);
    }
}

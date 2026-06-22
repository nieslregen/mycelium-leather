package com.nieslregen.effect;


import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class IllnessEffect extends MobEffect {
    private final RandomSource random;
    private Vec3 leanDir = Vec3.ZERO;

    protected IllnessEffect(MobEffectCategory category, int color) {
        random = RandomSource.create();

        super(category, color);
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity mob, int amplification) {
        mob.moveRelative(1, leanDir);
        mob.hurtMarked = true;
        return true;
    }

    @Override
    public void onEffectStarted(LivingEntity mob, int amplifier) {
        mob.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 200, amplifier, false, false));

        leanDir = new Vec3(
                random.nextGaussian(),
                0,
                random.nextGaussian()
        ).scale(0.025);

        super.onEffectStarted(mob, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification) {
        return true;
    }
}

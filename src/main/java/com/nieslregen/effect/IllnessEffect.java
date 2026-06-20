package com.nieslregen.effect;


import com.nieslregen.MyceliumLeatherMod;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.Random;

public class IllnessEffect extends MobEffect {
    private final RandomSource random;

    protected IllnessEffect(MobEffectCategory category, int color) {
       random = RandomSource.create();
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity mob, int amplification) {
        if (random.nextFloat() < 0.005F) {
            Vec3 randomV = new Vec3(
                    random.nextGaussian(),
                    0,
                    random.nextGaussian()
            ).normalize().scale(0.1);

            mob.addDeltaMovement(randomV);
            mob.hurtMarked = true;
            return true;
        }

        return applyEffectTick(serverLevel, mob, amplification);
    }

    @Override
    public void onEffectStarted(LivingEntity mob, int amplifier) {
        mob.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 200, amplifier, false, false));
        super.onEffectStarted(mob, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification) {
        return true;
    }
}

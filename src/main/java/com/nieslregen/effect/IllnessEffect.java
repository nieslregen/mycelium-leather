package com.nieslregen.effect;


import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import static com.nieslregen.datagen.ModEntityLootTableProvider.ILLNESS_DROP;

public class IllnessEffect extends MobEffect {
    private int shitEggTimer;

    protected IllnessEffect(MobEffectCategory category, int color) {
        super(category, color);
        resetTimer();
    }

    private void resetTimer() {
        shitEggTimer = 20 * 7;
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity mob, int amplification) {
        if (--shitEggTimer <= 0) {
            if (mob.dropFromGiftLootTable(serverLevel, ILLNESS_DROP, mob::spawnAtLocation)) {
                mob.playSound(SoundEvents.CHICKEN_EGG);
            }
        }
        return true;
    }

    @Override
    public void onEffectStarted(LivingEntity mob, int amplifier) {
        mob.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 200, amplifier, false, false));
        mob.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 200, amplifier, false, false));

        super.onEffectStarted(mob, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification) {
        return true;
    }
}

package com.nieslregen.items.customitems;

import com.nieslregen.datagen.ModDamageTypes;
import com.nieslregen.effect.ModEffects;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;


public class Dagger extends Item {
    private final boolean bleedingEnabled;
    private final EffectType effect;

    public enum EffectType {
        TEMPERATE, WARM, COLD, MUSHROOM
    }

    public Dagger(Properties properties) {
        super(properties);
        bleedingEnabled = false;
        this.effect = null;
    }

    public Dagger(Properties properties, EffectType effect, boolean bleedingEnabled) {
        super(properties);
        this.bleedingEnabled = bleedingEnabled;
        this.effect = effect;
    }

    @Override
    public void hurtEnemy(ItemStack itemStack, LivingEntity mob, LivingEntity attacker) {
        super.hurtEnemy(itemStack, mob, attacker);
        if (this.bleedingEnabled && effect != null) {

            switch (this.effect) {
                case TEMPERATE -> applyEffect(mob, ModEffects.BLEEDING, 20 * 3);
                case WARM -> attacker.heal(2);
                case COLD -> applyEffect(mob, MobEffects.SLOWNESS.value(), 20 * 2);
                case MUSHROOM -> {
                    applyEffect(mob, MobEffects.POISON.value(), 20 * 3);
                    applyEffect(mob, MobEffects.NAUSEA.value(), 20 * 5);
                }
            }
        }

        // Backstab
        if (attacker.level() instanceof ServerLevel serverLevel) {

            if (doesNotSee(mob, attacker)) {
                attacker.level().playSound(
                        null,
                        attacker.blockPosition(),
                        SoundEvents.PIGLIN_DEATH,
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F
                );
                mob.hurtServer(
                        serverLevel,
                        ModDamageTypes.create(serverLevel, ModDamageTypes.BACKSTAB),
                        8f);
            }
        }
    }

    private boolean doesNotSee(LivingEntity mob, LivingEntity attacker) {
        return attacker.getDirection() == mob.getDirection();
    }

    private void applyEffect(LivingEntity entity, MobEffect effect, int duration) {
        entity.addEffect(
                new MobEffectInstance(BuiltInRegistries
                        .MOB_EFFECT
                        .wrapAsHolder(effect),
                duration));
    }

    @Override
    public @Nullable DamageSource getItemDamageSource(LivingEntity attacker) {
        return new DamageSource(
                ModDamageTypes.create(
                        (ServerLevel)attacker.level(),
                        ModDamageTypes.STAB).typeHolder(),
                attacker
        );
    }
}

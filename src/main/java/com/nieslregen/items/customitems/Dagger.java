package com.nieslregen.items.customitems;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.effect.BleedingEffect;
import com.nieslregen.effect.ModEffects;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;


public class Dagger extends Item {
    private final boolean bleedingEnabled;
    private final MobEffect effect;

    public Dagger(Properties properties) {
        super(properties);
        bleedingEnabled = false;
        this.effect = null;
    }

    public Dagger(Properties properties, MobEffect effect, boolean bleedingEnabled) {
        super(properties);
        this.bleedingEnabled = bleedingEnabled;
        this.effect = effect;
    }

    @Override
    public void hurtEnemy(ItemStack itemStack, LivingEntity mob, LivingEntity attacker) {
        super.hurtEnemy(itemStack, mob, attacker);
        if (!this.bleedingEnabled && effect != null) {
            mob.addEffect(new MobEffectInstance(BuiltInRegistries
                    .MOB_EFFECT
                    .wrapAsHolder(effect),
                    20));
        }

        // Backstab
        if (attacker.getDirection() == mob.getDirection()) {
            attacker.level().playSound(
                    null,
                    attacker.blockPosition(),
                    SoundEvents.PIGLIN_DEATH,
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F
            );
            mob.hurtServer(
                    (ServerLevel) attacker.level(),
                    attacker.damageSources().playerAttack((Player)attacker),
                    8f);
        }
    }
}

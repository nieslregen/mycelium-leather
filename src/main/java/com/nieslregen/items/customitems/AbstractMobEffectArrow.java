package com.nieslregen.items.customitems;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class AbstractMobEffectArrow extends ArrowItem {

    private final List<MobEffect> effects;

    public AbstractMobEffectArrow(Properties properties, List<MobEffect> effects) {
        super(properties);
        this.effects = effects;
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack itemStack, LivingEntity owner, @Nullable ItemStack firedFromWeapon) {
        Arrow newArrow = new Arrow(level, owner, itemStack.copyWithCount(1), firedFromWeapon);

        for (MobEffect effect : this.effects) {
            newArrow.addEffect(
                    new MobEffectInstance(
                            BuiltInRegistries
                                    .MOB_EFFECT
                                    .wrapAsHolder(effect),
                            20 * 10
                    )
            );
        }

        return newArrow;
    }
}

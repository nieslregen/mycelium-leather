package com.nieslregen.mob.myceliumchicken;

import com.nieslregen.effect.ModEffects;
import com.nieslregen.items.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ThrownSuspiciousEgg extends ThrowableItemProjectile {

    public ThrownSuspiciousEgg(final Level level, final LivingEntity mob, final ItemStack itemStack) {
        super(EntityTypes.EGG, mob, level, itemStack);
    }

    public ThrownSuspiciousEgg(final Level level, final double x, final double y, final double z, final ItemStack itemStack) {
        super(EntityTypes.EGG, x, y, z, level, itemStack);
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (this.level() instanceof ServerLevel) {
            this.level().broadcastEntityEvent(this, (byte)3);
        }
        this.discard();
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        if (hitResult.getEntity() instanceof LivingEntity) {
            ((LivingEntity) hitResult.getEntity()).addEffect(
                    new MobEffectInstance(
                            BuiltInRegistries
                                    .MOB_EFFECT
                                    .wrapAsHolder(ModEffects.ILLNESS),
                            20 * 10
                    )
            );
            hitResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 0.0F);
        }
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.SUSPICIOUS_EGG;
    }
}

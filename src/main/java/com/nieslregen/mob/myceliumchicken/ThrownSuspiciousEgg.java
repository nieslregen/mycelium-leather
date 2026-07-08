package com.nieslregen.mob.myceliumchicken;

import com.nieslregen.items.ModItems;
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
    public ThrownSuspiciousEgg(EntityType<? extends ThrowableItemProjectile> type, Level level) {
        super(type, level);
    }

    public ThrownSuspiciousEgg(final Level level, final LivingEntity mob, final ItemStack itemStack) {
        super(EntityTypes.EGG, mob, level, itemStack);
    }

    public ThrownSuspiciousEgg(final Level level, final double x, final double y, final double z, final ItemStack itemStack) {
        super(EntityTypes.EGG, x, y, z, level, itemStack);
    }

    // ToDo
    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
    }

    // ToDo
    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.SUSPICIOUS_EGG;
    }
}

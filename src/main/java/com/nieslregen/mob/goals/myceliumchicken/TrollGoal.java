package com.nieslregen.mob.goals.myceliumchicken;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.items.ModItems;
import com.nieslregen.mob.myceliumchicken.MyceliumChicken;
import com.nieslregen.mob.myceliumchicken.ThrownSuspiciousEgg;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class TrollGoal extends Goal {
    private final MyceliumChicken chicken;

    public TrollGoal(MyceliumChicken chicken) {
        this.chicken = chicken;
    }

    @Override
    public boolean canUse() {
        return chicken.trollCooldown <= 0 && !chicken.isBaby();
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void start() {
        super.start();
        throwEgg();
        MyceliumLeatherMod.LOGGER.info("Troll");

    }

    private void throwEgg() {
        List<LivingEntity> entities = this.chicken.getTargets(this.chicken, 15);

        for (LivingEntity entity : entities) {
            MyceliumLeatherMod.LOGGER.info("entity: {}", entity);
        }

        entities.stream()
                .filter(entity -> entity instanceof Player)
                .findFirst()
                .ifPresent(player -> {
                    double xd = player.position().x;
                    double yd = player.position().y;
                    double zd = player.position().z;

                    ItemStack itemStack = new ItemStack(ModItems.SUSPICIOUS_EGG);

                    if (chicken.level() instanceof ServerLevel serverLevel) {
                        Projectile.spawnProjectileUsingShoot(
                                ThrownSuspiciousEgg::new,
                                serverLevel,
                                itemStack,
                                chicken,
                                xd,
                                yd,
                                zd,
                                1.5F,
                                1.0F);
                    }

                    chicken.playSound(chicken.getSoundVariant().hurtSound().value());
                });
    }

    @Override
    public void stop() {
        super.stop();
        chicken.resetTrollCooldown();
    }
}

package com.nieslregen.items.customitems;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MushroomPaste extends Item {
    public MushroomPaste(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(itemStack, level, entity);

        if (entity instanceof Player player ) {
            if (level instanceof ServerLevel serverLevel) {
                player.heal(2F);
            }
        }
        return result;
    }
}

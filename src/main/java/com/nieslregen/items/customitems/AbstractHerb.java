package com.nieslregen.items.customitems;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class AbstractHerb extends Item {
    public AbstractHerb(Properties properties) {
        super(properties);
    }

    public void returnBowl(LivingEntity entity) {
        if (entity instanceof Player player) {
            player.addItem(new ItemStack(Items.BOWL));
        }
    }
}

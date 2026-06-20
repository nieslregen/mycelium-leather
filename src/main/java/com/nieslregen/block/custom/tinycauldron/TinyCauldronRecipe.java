package com.nieslregen.block.custom.tinycauldron;

import net.minecraft.world.item.ItemStack;

import java.util.List;

public record TinyCauldronRecipe(
        int identifier,
        List<ItemStack> recipeComponents,
        ItemStack resultItem
) {}

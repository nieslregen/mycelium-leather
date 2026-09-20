package com.nieslregen.block.custom.cooking;

import net.minecraft.world.item.ItemStack;

import java.util.List;

public record CookingRecipe(
        int identifier,
        List<ItemStack> recipeComponents,
        ItemStack resultItem,
        boolean needsFire,
        UtilType type
) {
    enum UtilType {
        None, TinyCauldron, FryingPan
    }
}

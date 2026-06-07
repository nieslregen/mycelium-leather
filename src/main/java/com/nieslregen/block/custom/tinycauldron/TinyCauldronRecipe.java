package com.nieslregen.block.custom.tinycauldron;

import net.minecraft.world.item.Item;

import java.util.List;

public record TinyCauldronRecipe(
        List<Item> recipeComponents,
        Item resultItem
) {}

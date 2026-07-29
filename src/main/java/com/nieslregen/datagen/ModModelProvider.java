package com.nieslregen.datagen;

import com.nieslregen.block.ModBlocks;
import com.nieslregen.items.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;


public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricPackOutput output) {
        super((output));
    }
    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
        blockModelGenerators.createTrivialCube(ModBlocks.SCRATCHED_MUSHROOM_STEM);
        blockModelGenerators.createTrivialCube(ModBlocks.MUSHROOM_STEM_HOLLOW);
        blockModelGenerators.createTrivialCube(ModBlocks.MYCELIUM_CHICKEN_NEST);
        blockModelGenerators.createTrivialCube(ModBlocks.FRYING_PAN);
        blockModelGenerators.createTrivialCube(ModBlocks.FEAST_OF_THE_MUSHROOM_FIELDS);
        blockModelGenerators.createTrivialCube(ModBlocks.STOVE);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.generateFlatItem(ModItems.MYCELIUM_LEATHER, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.MYCELIUM_PATCH, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.MYCELIUM_PATCH_DRIED, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.ITEM_SPADE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.GRASS_PATCH, ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(ModItems.SOOT, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.SOOT_INK, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.SUSPICIOUS_FLASK, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.ARROW_OF_ILLNESS, ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(ModItems.TRUFFLE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.MUSHROOM_PASTE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.SUSPICIOUS_EGG, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.TOASTED_BREAD, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.SCRAMBLED_EGGS, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.SALT, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.PAN_FRIED_POTATOES, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.MYCELIUM_CHICKEN_EGG, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.MYCELIUM_CHICKEN_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.FEATHER_VARIANT_MUSHROOM, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.FEATHER_VARIANT_COLD, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.FEATHER_VARIANT_WARM, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.COPPER_DAGGER, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.IRON_DAGGER, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.GOLDEN_DAGGER_CLASSIC, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.GOLDEN_DAGGER_WARM, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.GOLDEN_DAGGER_COLD, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.GOLDEN_DAGGER_MUSHROOM, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.DIAMOND_DAGGER, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.WOODEN_SPADE, ModelTemplates.FLAT_ITEM);
    }
}

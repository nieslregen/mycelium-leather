package com.nieslregen.datagen;

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
//        blockModelGenerators.createTrivialCube(ModBlocks.HERBARIUM_PRESS);
//        blockModelGenerators.createTrivialCube(ModBlocks.CHARCOAL_PILE);
//        blockModelGenerators.createTrivialCube(ModBlocks.TINY_CAULDRON);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.generateFlatItem(ModItems.MYCELIUM_LEATHER, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.MYCELIUM_PATCH, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.MYCELIUM_PATCH_DRIED, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.SPADE, ModelTemplates.FLAT_HANDHELD_ITEM);

        itemModelGenerators.generateFlatItem(ModItems.GRASS_PATCH, ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(ModItems.SOOT, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.SOOT_INK, ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(ModItems.SUSPICIOUS_FLASK, ModelTemplates.FLAT_ITEM);
    }
}

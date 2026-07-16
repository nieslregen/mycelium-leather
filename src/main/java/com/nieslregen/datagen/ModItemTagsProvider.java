package com.nieslregen.datagen;


import com.nieslregen.items.ModItems;
import com.nieslregen.tags.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.references.ItemIds;
import net.minecraft.tags.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends FabricTagsProvider.ItemTagsProvider {


    public ModItemTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        tag(ItemTags.ARROWS).add(ModItems.getResourceKey(ModItems.ARROW_OF_ILLNESS));
        tag(ModTags.Items.MYCELIUM_CHICKEN_FOOD).add(ModItems.getResourceKey(ModItems.TRUFFLE));
        tag(ModTags.Items.FEATHERS)
                .add(ModItems.getResourceKey(ModItems.FEATHER_VARIANT_WARM))
                .add(ModItems.getResourceKey(ModItems.FEATHER_VARIANT_COLD))
                .add(ModItems.getResourceKey(ModItems.FEATHER_VARIANT_MUSHROOM))
                .add(ItemIds.FEATHER);
    }
}


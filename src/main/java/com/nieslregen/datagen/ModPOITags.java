package com.nieslregen.datagen;

import com.nieslregen.tags.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagEntry;
import net.minecraft.world.entity.ai.village.poi.PoiType;

import java.util.concurrent.CompletableFuture;

import static com.nieslregen.mob.ModPoiTypes.SQUIRREL_HOME_POI_KEY;

public class ModPOITags extends FabricTagsProvider<PoiType> {

    public ModPOITags(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, Registries.POINT_OF_INTEREST_TYPE, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        getOrCreateRawBuilder(ModTags.Poi.SQUIRREL_HOME_POI)
                .add(TagEntry.element(SQUIRREL_HOME_POI_KEY.identifier()));
    }
}

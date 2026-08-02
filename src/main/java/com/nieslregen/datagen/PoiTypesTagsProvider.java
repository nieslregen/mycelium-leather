package com.nieslregen.datagen;

import com.nieslregen.mob.ModPoiTypes;
import com.nieslregen.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;

import java.util.concurrent.CompletableFuture;

public class PoiTypesTagsProvider extends TagsProvider<PoiType> {

    public PoiTypesTagsProvider(final PackOutput output, final CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Registries.POINT_OF_INTEREST_TYPE, lookupProvider);
    }

    protected void addTags(final HolderLookup.Provider registries) {
        this.tag(ModTags.Poi.SQUIRREL_HOME_POI).add(new ResourceKey[]{ModPoiTypes.SQUIRREL_HOME});
    }
}

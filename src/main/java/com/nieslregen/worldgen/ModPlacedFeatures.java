package com.nieslregen.worldgen;

import com.nieslregen.MyceliumLeatherMod;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

import java.util.List;

import static com.nieslregen.worldgen.ModFeatures.HUGE_BLUE_MUSHROOM_WITH_HOLLOW;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> HUGE_BLUE_MUSHROOM_WITH_HOLLOW_PLACED =
            ResourceKey.create(
                    Registries.PLACED_FEATURE,
                    Identifier.fromNamespaceAndPath(
                            MyceliumLeatherMod.MOD_ID,
                            "huge_blue_mushroom_with_hollow_placed"
                    )
            );

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {

        HolderGetter<Feature> features =
                context.lookup(Registries.FEATURE);

        context.register(
                HUGE_BLUE_MUSHROOM_WITH_HOLLOW_PLACED,
                new PlacedFeature(
                        features.getOrThrow(
                                ModFeatures.HUGE_BLUE_MUSHROOM_WITH_HOLLOW
                        ),
                        List.of(
                                RarityFilter.onAverageOnceEvery(50),
                                InSquarePlacement.spread(),
                                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                                BiomeFilter.biome()
                        )
                )
        );
    }

    public static void init() {
    }
}

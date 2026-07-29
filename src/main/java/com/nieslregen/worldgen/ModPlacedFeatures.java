package com.nieslregen.worldgen;

import com.nieslregen.MyceliumLeatherMod;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> HUGE_BROWN_MUSHROOM_WITH_HOLLOW_PLACED_KEY =
            ResourceKey.create(
                    Registries.PLACED_FEATURE,
                    Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, "huge_brown_mushroom_with_hollow_placed")
            );

    public static void configure(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        context.register(
                HUGE_BROWN_MUSHROOM_WITH_HOLLOW_PLACED_KEY,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(ModConfiguredFeatures.HUGE_BROWN_MUSHROOM_WITH_HOLLOW_KEY),
                        List.of(
                                RarityFilter.onAverageOnceEvery(25), // spawns once every 10 chunks on average
                                BiomeFilter.biome(),
                                InSquarePlacement.spread(),
                                PlacementUtils.HEIGHTMAP_WORLD_SURFACE
                        )
    ));
    }
}

package com.nieslregen.worldgen;

import com.nieslregen.MyceliumLeatherMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import static com.nieslregen.worldgen.ModFeatures.HUGE_BROWN_MUSHROOM_WITH_HOLLOW;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?,?>> HUGE_BROWN_MUSHROOM_WITH_HOLLOW_KEY =
            ResourceKey.create(
                    Registries.CONFIGURED_FEATURE,
                    Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, "huge_brown_mushroom_with_hollow_key")
            );


    public static void configure(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HugeMushroomFeatureConfiguration config =
                new HugeMushroomFeatureConfiguration(
                        BlockStateProvider.simple(
                                Blocks.BROWN_MUSHROOM_BLOCK.defaultBlockState()
                                        .setValue(HugeMushroomBlock.DOWN, false)
                        ),
                        BlockStateProvider.simple(
                                Blocks.MUSHROOM_STEM.defaultBlockState()
                                        .setValue(HugeMushroomBlock.UP, false)
                                        .setValue(HugeMushroomBlock.DOWN, false)
                        ),
                        2,
                        BlockPredicate.matchesTag(BlockTags.HUGE_BROWN_MUSHROOM_CAN_PLACE_ON)
                );

        context.register(
                HUGE_BROWN_MUSHROOM_WITH_HOLLOW_KEY,
                new ConfiguredFeature<>(
                        HUGE_BROWN_MUSHROOM_WITH_HOLLOW,
                        config
                )
        );
    }
}

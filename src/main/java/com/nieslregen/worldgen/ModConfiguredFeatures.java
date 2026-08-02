package com.nieslregen.worldgen;

import com.nieslregen.MyceliumLeatherMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import static com.nieslregen.worldgen.ModFeatures.HUGE_BROWN_MUSHROOM_WITH_HOLLOW;
import static com.nieslregen.worldgen.ModFeatures.HUGE_RED_MUSHROOM_WITH_HOLLOW;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?,?>> HUGE_BROWN_MUSHROOM_WITH_HOLLOW_KEY =
            ResourceKey.create(
                    Registries.CONFIGURED_FEATURE,
                    Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, "huge_brown_mushroom_with_hollow_key")
            );

    public static final ResourceKey<ConfiguredFeature<?,?>> HUGE_RED_MUSHROOM_WITH_HOLLOW_KEY =
            ResourceKey.create(
                    Registries.CONFIGURED_FEATURE,
                    Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, "huge_red_mushroom_with_hollow_key")
            );


    public static void configure(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HugeMushroomFeatureConfiguration configBrown = getConfig(BlockTags.HUGE_BROWN_MUSHROOM_CAN_PLACE_ON);
        HugeMushroomFeatureConfiguration configRed = getConfig(BlockTags.HUGE_RED_MUSHROOM_CAN_PLACE_ON);


        context.register(
                HUGE_BROWN_MUSHROOM_WITH_HOLLOW_KEY,
                new ConfiguredFeature<>(
                        HUGE_BROWN_MUSHROOM_WITH_HOLLOW,
                        configBrown
        ));

        context.register(
                HUGE_RED_MUSHROOM_WITH_HOLLOW_KEY,
                new ConfiguredFeature<>(
                        HUGE_RED_MUSHROOM_WITH_HOLLOW,
                        configRed
        ));
    }


    private static HugeMushroomFeatureConfiguration getConfig(TagKey<Block> blockTag) {
        return new HugeMushroomFeatureConfiguration(
                BlockStateProvider.simple(
                        Blocks.BROWN_MUSHROOM_BLOCK.defaultBlockState()
                                .setValue(HugeMushroomBlock.DOWN, false)
                ),
                BlockStateProvider.simple(
                        Blocks.MUSHROOM_STEM.defaultBlockState()
                                .setValue(HugeMushroomBlock.UP, false)
                                .setValue(HugeMushroomBlock.DOWN, false)
                ),
                2, // ToDo: Make dynamic
                BlockPredicate.matchesTag(blockTag)
        );
    }


}

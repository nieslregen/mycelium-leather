package com.nieslregen.worldgen;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.block.ModBlocks;
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

import static com.nieslregen.worldgen.ModFeatures.HUGE_BLUE_MUSHROOM_WITH_HOLLOW;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?,?>> HUGE_BLUE_MUSHROOM_WITH_HOLLOW_KEY =
            ResourceKey.create(
                    Registries.CONFIGURED_FEATURE,
                    Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, "huge_blue_mushroom_with_hollow_key")
            );

    public static void configure(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HugeMushroomFeatureConfiguration configBlue = getConfig(BlockTags.HUGE_BROWN_MUSHROOM_CAN_PLACE_ON);
        context.register(
                HUGE_BLUE_MUSHROOM_WITH_HOLLOW_KEY,
                new ConfiguredFeature<>(
                        HUGE_BLUE_MUSHROOM_WITH_HOLLOW,
                        configBlue
        ));
    }


    private static HugeMushroomFeatureConfiguration getConfig(TagKey<Block> blockTag) {
        return new HugeMushroomFeatureConfiguration(
                BlockStateProvider.simple(
                        ModBlocks.BLUE_MUSHROOM_BLOCK.defaultBlockState()
                                .setValue(HugeMushroomBlock.DOWN, false)
                ),
                BlockStateProvider.simple(
                        Blocks.MUSHROOM_STEM.defaultBlockState()
                                .setValue(HugeMushroomBlock.UP, false)
                                .setValue(HugeMushroomBlock.DOWN, false)
                ),
                3, // ToDo: Make dynamic
                BlockPredicate.matchesTag(blockTag)
        );
    }


}

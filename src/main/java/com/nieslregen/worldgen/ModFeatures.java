package com.nieslregen.worldgen;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.block.ModBlocks;
import com.nieslregen.worldgen.hugemushroom.HugeBlueMushroomWithHollowFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class ModFeatures {

    public static final ResourceKey<Feature> HUGE_BLUE_MUSHROOM_WITH_HOLLOW =
            ResourceKey.create(
                    Registries.FEATURE,
                    Identifier.fromNamespaceAndPath(
                            MyceliumLeatherMod.MOD_ID,
                            "huge_blue_mushroom_with_hollow"
                    )
            );

    public static void bootstrap(BootstrapContext<Feature> context) {
        context.register(
                HUGE_BLUE_MUSHROOM_WITH_HOLLOW,
                new HugeBlueMushroomWithHollowFeature(
                        BlockStateProvider.holderOf(
                                ModBlocks.BLUE_MUSHROOM_BLOCK
                                        .defaultBlockState()
                                        .setValue(HugeMushroomBlock.DOWN, false)
                        ),

                        BlockStateProvider.holderOf(
                                Blocks.MUSHROOM_STEM
                                        .defaultBlockState()
                                        .setValue(HugeMushroomBlock.UP, false)
                                        .setValue(HugeMushroomBlock.DOWN, false)
                        ),

                        3,

                        BlockPredicate.matchesTag(
                                BlockTags.HUGE_BROWN_MUSHROOM_CAN_PLACE_ON
                        )
                )
        );
    }

    public static void init() {
    }
}

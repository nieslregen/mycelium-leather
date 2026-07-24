package com.nieslregen.worldgen.hugemushroom;

import com.mojang.serialization.Codec;
import com.nieslregen.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.AbstractHugeMushroomFeature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;

public abstract class AbstractHugeMushroomWithHollow extends AbstractHugeMushroomFeature {

    public AbstractHugeMushroomWithHollow(Codec<HugeMushroomFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    protected void placeTrunk(WorldGenLevel level, RandomSource random, BlockPos origin, HugeMushroomFeatureConfiguration config, int treeHeight, BlockPos.MutableBlockPos blockPos) {
        for (int dy = 0; dy < treeHeight; ++dy) {
            blockPos.set(origin).move(Direction.UP, dy);
            if (dy != treeHeight - 2) {
                this.placeMushroomBlock(level, blockPos, config.stemProvider().getState(level, random, origin));
            } else {
                this.placeMushroomBlock(level, blockPos, ModBlocks.MUSHROOM_STEM_HOLLOW.defaultBlockState());
            }
        }
    }
}
// FeatureUtils.register(
//         context,
//         HUGE_BROWN_MUSHROOM,
//         Feature.HUGE_BROWN_MUSHROOM,
//         new HugeMushroomFeatureConfiguration(
//                 BlockStateProvider
//                         .simple((BlockState)((BlockState)Blocks.BROWN_MUSHROOM_BLOCK.defaultBlockState().setValue(HugeMushroomBlock.UP, true)).setValue(HugeMushroomBlock.DOWN, false)), BlockStateProvider.simple((BlockState)((BlockState)Blocks.MUSHROOM_STEM.defaultBlockState().setValue(HugeMushroomBlock.UP, false)).setValue(HugeMushroomBlock.DOWN, false)), 3, BlockPredicate.matchesTag(BlockTags.HUGE_BROWN_MUSHROOM_CAN_PLACE_ON)));
//

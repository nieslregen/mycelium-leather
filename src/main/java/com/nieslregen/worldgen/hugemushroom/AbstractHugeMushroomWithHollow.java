package com.nieslregen.worldgen.hugemushroom;

import com.mojang.serialization.Codec;
import com.nieslregen.block.ModBlockEntities;
import com.nieslregen.block.ModBlocks;
import com.nieslregen.block.custom.mushroomstem.MushroomStemHollowBlock;
import com.nieslregen.block.custom.mushroomstem.ScratchedMushroomStemBlock;
import com.nieslregen.mob.CustomOccupant;
import com.nieslregen.mob.ModEntityTypes;
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
        Direction randomDir = Direction.getRandom(random);
        if (randomDir.equals(Direction.UP) || randomDir.equals(Direction.DOWN)) {
            randomDir = Direction.NORTH;
        }
        for (int dy = 0; dy < treeHeight; ++dy) {
            blockPos.set(origin).move(Direction.UP, dy);
            if (dy != treeHeight - 2) {
                if (dy < treeHeight - 2 && random.nextFloat() < .3) {
                    this.placeMushroomBlock(level, blockPos,  ModBlocks.SCRATCHED_MUSHROOM_STEM.defaultBlockState().setValue(ScratchedMushroomStemBlock.FACING, randomDir));
                } else {
                    this.placeMushroomBlock(level, blockPos, config.stemProvider().getState(level, random, origin));
                }
            } else {
                this.placeMushroomBlock(level, blockPos, ModBlocks.MUSHROOM_STEM_HOLLOW.defaultBlockState().setValue(MushroomStemHollowBlock.FACING, randomDir));
                level.getBlockEntity(blockPos, ModBlockEntities.MUSHROOM_STEM_HOLLOW_ENTITY).ifPresent(hollow -> {
                    int numSquirrel = random.nextInt(1,2);

                    for (int i = 0; i < numSquirrel; ++i) {
                        hollow.storeMob(CustomOccupant.create(random.nextInt(599), ModEntityTypes.SQUIRREL));
                    }
                });
            }
        }
    }
}

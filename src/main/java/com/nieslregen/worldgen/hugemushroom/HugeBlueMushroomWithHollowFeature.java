package com.nieslregen.worldgen.hugemushroom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nieslregen.block.ModBlockEntities;
import com.nieslregen.block.ModBlocks;
import com.nieslregen.block.custom.mushroomstem.MushroomStemHollowBlock;
import com.nieslregen.block.custom.mushroomstem.ScratchedMushroomStemBlock;
import com.nieslregen.mob.CustomOccupant;
import com.nieslregen.mob.ModEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.AbstractHugeMushroomFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record HugeBlueMushroomWithHollowFeature(
        Holder<BlockStateProvider> capProvider,
        Holder<BlockStateProvider> stemProvider,
        int foliageRadius,
        BlockPredicate canPlaceOn
) implements AbstractHugeMushroomFeature {

    public static final MapCodec<HugeBlueMushroomWithHollowFeature> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    instance.group(
                            BlockStateProvider.CODEC
                                    .fieldOf("cap_provider")
                                    .forGetter(HugeBlueMushroomWithHollowFeature::capProvider),

                            BlockStateProvider.CODEC
                                    .fieldOf("stem_provider")
                                    .forGetter(HugeBlueMushroomWithHollowFeature::stemProvider),

                            Codec.INT
                                    .optionalFieldOf("foliage_radius", 2)
                                    .forGetter(HugeBlueMushroomWithHollowFeature::foliageRadius),

                            BlockPredicate.CODEC
                                    .fieldOf("can_place_on")
                                    .forGetter(HugeBlueMushroomWithHollowFeature::canPlaceOn)
                    ).apply(instance, HugeBlueMushroomWithHollowFeature::new)
            );

    @Override
    public MapCodec<HugeBlueMushroomWithHollowFeature> codec() {
        return CODEC;
    }

    @Override
    public void placeTrunk(WorldGenLevel level, RandomSource random, BlockPos origin, int treeHeight, BlockPos.MutableBlockPos blockPos) {
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
                    this.placeMushroomBlock(level, blockPos, (this.stemProvider().value()).getState(level, random, origin));
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

    @Override
    public int getTreeRadiusForHeight(
            int trunkHeight,
            int treeHeight,
            int leafRadius,
            int yo
    ) {
        return yo <= 3 ? 0 : leafRadius;
    }

    @Override
    public void makeCap(
            WorldGenLevel level,
            RandomSource random,
            BlockPos origin,
            int treeHeight,
            BlockPos.MutableBlockPos blockPos
    ) {
        for (int dy = treeHeight - 1; dy <= treeHeight; ++dy) {
            int radius = dy < treeHeight
                    ? this.foliageRadius
                    : this.foliageRadius - 1;

            int center = this.foliageRadius - 2;

            for (int dx = -radius; dx <= radius; ++dx) {
                for (int dz = -radius; dz <= radius; ++dz) {
                    boolean minX = dx == -radius;
                    boolean maxX = dx == radius;
                    boolean minZ = dz == -radius;
                    boolean maxZ = dz == radius;

                    boolean xEdge = minX || maxX;
                    boolean zEdge = minZ || maxZ;

                    if (dy >= treeHeight || xEdge != zEdge) {
                        blockPos.setWithOffset(origin, dx, dy, dz);

                        BlockState state =
                                this.capProvider.value().getState(
                                        level,
                                        random,
                                        origin
                                );

                        if (state.hasProperty(HugeMushroomBlock.WEST)
                                && state.hasProperty(HugeMushroomBlock.EAST)
                                && state.hasProperty(HugeMushroomBlock.NORTH)
                                && state.hasProperty(HugeMushroomBlock.SOUTH)
                                && state.hasProperty(HugeMushroomBlock.UP)) {

                            state = state
                                    .setValue(
                                            HugeMushroomBlock.UP,
                                            dy >= treeHeight - 1
                                    )
                                    .setValue(
                                            HugeMushroomBlock.WEST,
                                            dx < -center
                                    )
                                    .setValue(
                                            HugeMushroomBlock.EAST,
                                            dx > center
                                    )
                                    .setValue(
                                            HugeMushroomBlock.NORTH,
                                            dz < -center
                                    )
                                    .setValue(
                                            HugeMushroomBlock.SOUTH,
                                            dz > center
                                    );
                        }
                        this.placeMushroomBlock(level, blockPos, state);
                    }
                }
            }
        }
    }
}
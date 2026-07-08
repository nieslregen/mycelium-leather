package com.nieslregen.mob.myceliumchicken;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class MyceliumChickenEggBlock extends Block {
    public MyceliumChickenEggBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (!isOnMycelium(level, pos)) {
            level.destroyBlock(pos, false);
        }
    }

    // Make chicken aggro
    @Override
    public void stepOn(Level level, BlockPos pos, BlockState onState, Entity entity) {
        super.stepOn(level, pos, onState, entity);
    }

    // implement hetching, if chicken is too long not hatching then it breaks
    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);
    }

    public static boolean isOnMycelium(final BlockGetter level, final BlockPos pos) {
        return level.getBlockState(pos.below()).is(Blocks.MYCELIUM);
    }



}

package com.nieslregen.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jspecify.annotations.Nullable;

public abstract class RotationalEntityBlock extends BaseEntityBlock {
    public static final EnumProperty<Direction> FACING = EnumProperty.create("facing", Direction.class);
    private boolean onlyHorizontal;

    public RotationalEntityBlock(Properties properties, boolean onlyHorizontal) {
        super(properties);
        registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
        this.onlyHorizontal = onlyHorizontal;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = onlyHorizontal ? context.getHorizontalDirection().getOpposite() :  context.getNearestLookingDirection().getOpposite();
        return this.defaultBlockState().setValue(FACING, facing);
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public abstract @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState);
}

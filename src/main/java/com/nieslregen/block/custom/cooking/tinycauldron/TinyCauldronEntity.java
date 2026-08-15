package com.nieslregen.block.custom.cooking.tinycauldron;

import com.nieslregen.block.custom.cooking.AbstractCookingUtilEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import static com.nieslregen.block.ModBlockEntities.TINY_CAULDRON_ENTITY;

public class TinyCauldronEntity extends AbstractCookingUtilEntity {


    public TinyCauldronEntity(BlockPos worldPosition, BlockState blockState) {
        super(TINY_CAULDRON_ENTITY, worldPosition, blockState);;
    }
}


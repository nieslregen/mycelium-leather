package com.nieslregen.block.custom.cooking.tinycauldron;

import com.nieslregen.block.custom.cooking.AbstractCookingUtilEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import static com.nieslregen.block.ModBlockEntities.FRYING_PAN_ENTITY;

public class FryingPanEntity extends AbstractCookingUtilEntity {
    public FryingPanEntity(BlockPos worldPosition, BlockState blockState) {
        super(FRYING_PAN_ENTITY, worldPosition, blockState);
    }
}

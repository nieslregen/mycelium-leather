package com.nieslregen.block.custom.mushroomstem;

import com.nieslregen.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ScratchedMushroomStemEntity extends BlockEntity {

    public ScratchedMushroomStemEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SCRATCHED_MUSHROOM_STEM_ENTITY, pos, state);
    }

    public ScratchedMushroomStemEntity(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
    }
}

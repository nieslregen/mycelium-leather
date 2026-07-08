package com.nieslregen.block.custom.mushroomstem;

import com.nieslregen.block.ModBlockEntities;
import com.nieslregen.block.custom.MobBlockContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class MushroomStemHollowEntity extends MobBlockContainer {

    public MushroomStemHollowEntity(BlockPos blockPosition, BlockState blockState) {
        super(ModBlockEntities.MUSHROOM_STEM_HOLLOW_ENTITY, blockPosition, blockState);
    }

    public MushroomStemHollowEntity(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
    }

}

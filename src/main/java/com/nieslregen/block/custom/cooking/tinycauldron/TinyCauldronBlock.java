package com.nieslregen.block.custom.cooking.tinycauldron;

import com.nieslregen.block.ModBlockEntities;
import com.nieslregen.block.custom.cooking.AbstractCookingUtilBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class TinyCauldronBlock extends AbstractCookingUtilBlock {

    public TinyCauldronBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new TinyCauldronEntity(worldPosition, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return createTickerHelper(
                type,
                ModBlockEntities.TINY_CAULDRON_ENTITY,
                TinyCauldronEntity::serverTick
        );
    }
}

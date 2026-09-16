package com.nieslregen.block.custom.cooking.fryingpan;

import com.nieslregen.block.ModBlockEntities;
import com.nieslregen.block.custom.cooking.AbstractCookingUtilBlock;
import com.nieslregen.block.custom.cooking.tinycauldron.TinyCauldronEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class FryingPanBlock extends AbstractCookingUtilBlock {
    public FryingPanBlock(Properties properties) {
        super(properties);
    }


    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new FryingPanEntity(worldPosition, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return createTickerHelper(
                type,
                ModBlockEntities.FRYING_PAN_ENTITY,
                TinyCauldronEntity::serverTick
        );
    }
}

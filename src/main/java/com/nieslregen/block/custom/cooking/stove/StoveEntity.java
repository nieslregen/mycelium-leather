package com.nieslregen.block.custom.cooking.stove;

import com.nieslregen.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class StoveEntity extends BlockEntity {

    public static final String FUEL_IDENTIFIER = "fuel";
    private int fuel = 0;
    private final int FUEL_PER_LOG = 20 * 30;

    public StoveEntity( BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.STOVE_ENTITY, worldPosition, blockState);
    }

    public static void serverTick(final Level level, final BlockPos blockPos, final BlockState blockState, final StoveEntity entity) {

        if (blockState.getValue(StoveBlock.LIT)) {
            if (entity.fuel <= 0) {
                if (consumeLog(level, blockPos, blockState)) {
                    entity.fuel = entity.FUEL_PER_LOG;
                } else {
                    level.setBlockAndUpdate(blockPos, blockState.setValue(StoveBlock.LIT, false));
                }
            } else {
                entity.fuel--;
            }
        }

    }

    private static boolean consumeLog(Level level, BlockPos blockPos, BlockState blockState) {
        if (blockState.getValue(StoveBlock.LOG) > 0) {
            level.setBlockAndUpdate(blockPos, blockState.setValue(StoveBlock.LOG, blockState.getValue(StoveBlock.LOG) - 1));
            return true;
        }
        return false;
    }


    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        fuel = input.getIntOr(FUEL_IDENTIFIER, 0);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putInt(FUEL_IDENTIFIER, fuel);
    }
}

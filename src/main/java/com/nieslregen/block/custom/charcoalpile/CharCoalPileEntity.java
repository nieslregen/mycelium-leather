package com.nieslregen.block.custom.charcoalpile;

import com.nieslregen.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class CharCoalPileEntity extends BlockEntity {

    private int burnProgress = 0;
    private static final int maxBurnProgress = 15000;
    private boolean ignited = false;
    private boolean finished = false;

    private final String IGNITED_IDENTIFIER = "ignited";
    private final String BURN_PROGRESS_IDENTIFIER = "burn_progress";
    private final String FINISHED_IDENTIFIER = "finished";

    private static final Component DEFAULT_NAME = Component.translatable("container.soot_trap_furnace");

    public CharCoalPileEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.CHARCOAL_PILE_ENTITY, worldPosition, blockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, CharCoalPileEntity entity) {
        if (entity.ignited) {

            if (!state.getValue(CharCoalPileBlock.LIT)) {
                level.setBlockAndUpdate(pos, state.setValue(CharCoalPileBlock.LIT, true));
            }

            entity.burnProgress ++;

            if ((entity.burnProgress >= maxBurnProgress / 2) && state.getValue(CharCoalPileBlock.STAGE) < 1) {
                level.setBlockAndUpdate(
                        pos,
                        state.setValue(CharCoalPileBlock.STAGE, 1)
                );
            }

            if ((!entity.finished) && (entity.burnProgress >= maxBurnProgress)) {
                entity.ignited = false;
                entity.finished = true;
                level.setBlockAndUpdate(
                        pos,
                        state
                                .setValue(CharCoalPileBlock.STAGE, 2)
                                .setValue(CharCoalPileBlock.LIT, false)
                );
            }
        }
    }

    public boolean ignite() {
        if (ignited || finished) {
            return false;
        }
        ignited = true;
        return true;
    }

    public boolean isFinished() {
        return finished;
    }

    public float getBurnProgressRatio() {
        return (float) burnProgress / (float) maxBurnProgress;
    }


    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        ignited = input.getBooleanOr(IGNITED_IDENTIFIER, false);
        burnProgress = input.getIntOr(BURN_PROGRESS_IDENTIFIER, 0);
        finished = input.getBooleanOr(FINISHED_IDENTIFIER, false);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putBoolean(IGNITED_IDENTIFIER, ignited);
        output.putInt(BURN_PROGRESS_IDENTIFIER, burnProgress);
        output.putBoolean(FINISHED_IDENTIFIER, finished);
    }
}

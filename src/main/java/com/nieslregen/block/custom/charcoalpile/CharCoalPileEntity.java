package com.nieslregen.block.custom.charcoalpile;

import com.nieslregen.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CharCoalPileEntity extends BlockEntity {

    private int burnProgress = 0;
    private static final int maxBurnProgress = 10000;
    private boolean ignited = false;
    private boolean finished = false;

    private static final Component DEFAULT_NAME = Component.translatable("container.soot_trap_furnace");
//    private final NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);

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

    public boolean isIgnited() {
        return ignited;
    }

    public boolean isFinished() {
        return finished;
    }

    public float getBurnProgressRatio() {
        return (float) burnProgress / (float) maxBurnProgress;
    }

}

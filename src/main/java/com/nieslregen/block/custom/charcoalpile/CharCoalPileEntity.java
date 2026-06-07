package com.nieslregen.block.custom.charcoalpile;

import com.nieslregen.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharCoalPileEntity extends BlockEntity {
    private static final Logger log = LoggerFactory.getLogger(CharCoalPileEntity.class); //implements ImplementedContainer

    private int burnProgress = 0;
    private static final int maxBurnProgress = 100;
    private boolean ignited = false;
    private boolean finished = false;

    private static final Component DEFAULT_NAME = Component.translatable("container.soot_trap_furnace");
//    private final NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);

    public CharCoalPileEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.CHARCOAL_PILE_ENTITY, worldPosition, blockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, CharCoalPileEntity entity) {
        if (entity.ignited) {
            entity.burnProgress ++;

            if (entity.burnProgress >= maxBurnProgress) {
                entity.ignited = false;
                entity.finished = true;
            }
        }
    }

    public boolean ignite() {
        if (ignited) {
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

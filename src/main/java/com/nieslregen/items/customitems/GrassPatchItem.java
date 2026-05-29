package com.nieslregen.items.customitems;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;


public class GrassPatchItem extends Item {

    public GrassPatchItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Level level = context.getLevel();
        Block clickedBlock =  level.getBlockState(context.getClickedPos()).getBlock();

        if (!level.isClientSide() && Blocks.DIRT.equals(clickedBlock)) {
            level.setBlockAndUpdate(context.getClickedPos(), Blocks.GRASS_BLOCK.defaultBlockState());
            context.getItemInHand().consume(1, context.getPlayer());
            return InteractionResult.SUCCESS;
        }
    return InteractionResult.FAIL;
    }
}

package com.nieslregen.items.customitems;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class MyceliumSporeItem extends Item {
    public MyceliumSporeItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Level level = context.getLevel();
        Block clickedBlock =  level.getBlockState(context.getClickedPos()).getBlock();

        if (!level.isClientSide() && Blocks.DIRT.equals(clickedBlock)) {
            // ToDo: replace with Mycelium spore block
            level.setBlockAndUpdate(context.getClickedPos(), Blocks.MYCELIUM.defaultBlockState());
        }
        context.getItemInHand().consume(1, context.getPlayer());
        return InteractionResult.SUCCESS;
    }
}

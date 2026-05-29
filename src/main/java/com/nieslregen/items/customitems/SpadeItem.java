package com.nieslregen.items.customitems;

import com.nieslregen.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Map;

public class SpadeItem extends Item {

    private static final Map<Block, Block> SPADE_MAP = Map.of(
            Blocks.MYCELIUM, Blocks.DIRT,
            Blocks.GRASS_BLOCK, Blocks.DIRT
    );

    public SpadeItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Level level = context.getLevel();
        Block clickedBlock = level.getBlockState(context.getClickedPos()).getBlock();

        if(SPADE_MAP.containsKey(clickedBlock) && !level.isClientSide()) {
            //Server side
            int x = context.getClickedPos().getX();
            int y = context.getClickedPos().getY();
            int z = context.getClickedPos().getZ();

            ItemStack drop;
            if (Blocks.MYCELIUM.equals(clickedBlock)) {
                drop = new ItemStack(ModItems.MYCELIUM_PATCH);
                Block.popResourceFromFace(level, new BlockPos(x,y,z), Direction.UP,drop);
            }
            if (Blocks.GRASS_BLOCK.equals(clickedBlock)) {
                drop = new ItemStack(ModItems.GRASS_PATCH);
                Block.popResourceFromFace(level, new BlockPos(x,y,z), Direction.UP,drop);
            }
            if (context.getPlayer() != null) {
                context.getItemInHand().hurtAndBreak(1, context.getPlayer(), context.getHand().asEquipmentSlot());
            }

            level.setBlockAndUpdate(context.getClickedPos(), SPADE_MAP.get(clickedBlock).defaultBlockState());
        }
        return InteractionResult.SUCCESS;
    }
}

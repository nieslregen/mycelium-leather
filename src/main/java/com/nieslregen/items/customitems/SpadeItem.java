package com.nieslregen.items.customitems;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.block.ModBlocks;
import com.nieslregen.block.custom.mushroomstem.MushroomStemHollowBlock;
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
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;
import java.util.Optional;

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

        if((SPADE_MAP.containsKey(clickedBlock) || isException(clickedBlock)) && !level.isClientSide()) {
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

            Optional<BlockState> newState = Optional.empty();
            if (SPADE_MAP.containsKey(clickedBlock)) {
                newState = Optional.of(SPADE_MAP.get(clickedBlock).defaultBlockState());
            }

            if (Blocks.MUSHROOM_STEM.equals(clickedBlock)) {
                newState = Optional.of(ModBlocks
                        .MUSHROOM_STEM_HOLLOW
                        .defaultBlockState()
                        .setValue(
                                MushroomStemHollowBlock.FACING,
                                context.getHorizontalDirection().getOpposite())
                );
            }

            if (context.getPlayer() != null) {
                context.getItemInHand().hurtAndBreak(1, context.getPlayer(), context.getHand().asEquipmentSlot());
            }

            newState.ifPresent(b -> level.setBlockAndUpdate(context.getClickedPos(), b));
        }
        return InteractionResult.SUCCESS;
    }

    // ToDo: find out, if there was a change between 26.1.2 and 26.2. that swapped the order of registration of blocks and items
    private boolean isException(Block block) {
        return block.equals(Blocks.MUSHROOM_STEM);
    }
}

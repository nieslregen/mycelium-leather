package com.nieslregen.block.custom.mushroomstem;

import com.mojang.serialization.MapCodec;
import com.nieslregen.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

public class ScratchedMushroomStemBlock extends BaseEntityBlock {

    public static final int MAX_STAGE = 2;
    public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, MAX_STAGE);

    public ScratchedMushroomStemBlock(Properties properties) {
        super(properties);
        registerDefaultState(
                getStateDefinition()
                        .any()
                        .setValue(STAGE, 0)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STAGE);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(ScratchedMushroomStemBlock::new);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new ScratchedMushroomStemEntity(worldPosition, blockState);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(STAGE) < 2) {
            level.setBlock(
                    pos,
                    (BlockState) state.setValue(STAGE, state.getValue(STAGE) + 1), 2
            );
        }
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (state.getValue(STAGE) == 2) {
            if (ModItems.MUSHROOM_PASTE.equals(player.getItemInHand(hand).getItem())) {
                itemStack.grow(1);
                return resetStage(level, pos, state);
            } else if (itemStack.isEmpty()) {
                player.setItemInHand(hand, new ItemStack(ModItems.MUSHROOM_PASTE));
                return resetStage(level, pos, state);
            }
        }
        return InteractionResult.FAIL;
    }

    private InteractionResult resetStage(Level level, BlockPos pos, BlockState state) {
        level.setBlock(
                pos,
                (BlockState) state.setValue(STAGE, 0), 2
        );
        return InteractionResult.SUCCESS;
    }
}

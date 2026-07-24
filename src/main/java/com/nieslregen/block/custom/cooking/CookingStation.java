package com.nieslregen.block.custom.cooking;

import com.mojang.serialization.MapCodec;
import com.nieslregen.MyceliumLeatherMod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class CookingStation extends Block {

    public static final BooleanProperty LIT = BooleanProperty.create("lit");

    public CookingStation(Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any().setValue(LIT, false));
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return simpleCodec(CookingStation::new);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        level.setBlockAndUpdate(pos, state.setValue(LIT, !state.getValue(LIT)));
        MyceliumLeatherMod.LOGGER.info("Cooking station is now: {}", state.getValue(LIT));
        return InteractionResult.SUCCESS;
    }
}

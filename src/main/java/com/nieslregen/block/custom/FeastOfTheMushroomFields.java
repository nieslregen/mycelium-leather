package com.nieslregen.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class FeastOfTheMushroomFields extends Block {
    public FeastOfTheMushroomFields(Properties properties) {
        super(properties);
    }


    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        super.useItemOn(itemStack, state, level, pos, player, hand, hitResult);


        player.addEffect(new MobEffectInstance(MobEffects.SPEED, 20 * 60), player);
        state.getBlock().popResource(level, pos, new ItemStack(Items.BOWL));
        level.destroyBlock(pos, false);
        return InteractionResult.SUCCESS;
    }
}

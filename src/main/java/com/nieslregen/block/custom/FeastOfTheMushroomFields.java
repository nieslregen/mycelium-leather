package com.nieslregen.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FeastOfTheMushroomFields extends Block {

    private static final VoxelShape SHAPE;

    public FeastOfTheMushroomFields(Properties properties) {
        super(properties);
    }


    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        super.useItemOn(itemStack, state, level, pos, player, hand, hitResult);

        if (level != null) {
            player.addEffect(new MobEffectInstance(MobEffects.SPEED, 20 * 60), player);
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 20 * 60, 2), player);
            player.heal(8f);
            player.getFoodData().eat(10,10);
            state.getBlock().popResource(level, pos, new ItemStack(Items.BOWL));
            level.destroyBlock(pos, false);
        }


        return InteractionResult.SUCCESS;
    }

    @Override
    protected VoxelShape getShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean useShapeForLightOcclusion(final BlockState state) {
        return true;
    }

    static {
        SHAPE = Block.column((double) 12.0F, (double) 0.0F, (double) 6.0F);
    }
}

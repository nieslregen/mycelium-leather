package com.nieslregen.mob.myceliumchicken;

import com.mojang.serialization.MapCodec;
import com.nieslregen.block.custom.herbariumpress.HerbariumPressEntity;
import com.nieslregen.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

public class MyceliumChickenNest extends BaseEntityBlock {

    public MyceliumChickenNest(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(MyceliumChickenNest::new);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new MyceliumChickenNestEntity(worldPosition, blockState);
    }


    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (!isOnMycelium(level, pos)) {
            level.destroyBlock(pos, false);
        }
    }

//    @Override
//    public void stepOn(Level level, BlockPos pos, BlockState onState, Entity entity) {
//        super.stepOn(level, pos, onState, entity);
//    }
    public ItemStack takeEgg() {
//        if (hasEgg()) {
            return new ItemStack(ModItems.MYCELIUM_CHICKEN_EGG);
//        }
//        return ItemStack.EMPTY;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (player.getItemInHand(hand).isEmpty()) {
            ItemStack result = takeEgg();
            player.setItemInHand(hand, result);
        }
    return InteractionResult.SUCCESS;
    }

    // implement hetching, if chicken is too long not hatching then it breaks
    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);
    }

//    public boolean hasEgg() {
//
//    }

    public static boolean isOnMycelium(final BlockGetter level, final BlockPos pos) {
        return isMycelium(level, pos.below());
    }

    public static boolean isMycelium(final BlockGetter level, final BlockPos pos) {
        return level.getBlockState(pos).is(Blocks.MYCELIUM);
    }



}

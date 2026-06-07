package com.nieslregen.block.custom.tinycauldron;

import com.mojang.serialization.MapCodec;
import com.nieslregen.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class TinyCauldronBlock extends BaseEntityBlock {

    private static final VoxelShape SHAPE;

    private static final List<Item> INGREDIENTS = List.of(Items.HONEY_BOTTLE, ModItems.SOOT);
    private static final Logger log = LoggerFactory.getLogger(TinyCauldronBlock.class);

    public TinyCauldronBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(TinyCauldronBlock::new);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new TinyCauldronEntity(worldPosition, blockState);
    }


    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide()) {return InteractionResult.SUCCESS;}
        ItemStack item = player.getItemInHand(hand);
        TinyCauldronEntity entity;

        if (level.getBlockEntity(pos) instanceof TinyCauldronEntity) {
            entity = (TinyCauldronEntity)level.getBlockEntity(pos);

            if (!item.isEmpty()) {
                if (isValidIngredient(item.getItem())) {
                    entity.placeIngredient((ServerLevel) level, player, item);
                }
            } else {


            }
        }
        return InteractionResult.SUCCESS;
    }


    public boolean isValidIngredient(Item item) {
        return INGREDIENTS.contains(item);
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
        SHAPE = Block.column((double)12.0F, (double)0.0F, (double)6.0F);
    }

}

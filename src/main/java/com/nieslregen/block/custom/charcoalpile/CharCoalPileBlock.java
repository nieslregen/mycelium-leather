package com.nieslregen.block.custom.charcoalpile;

import com.mojang.serialization.MapCodec;
import com.nieslregen.block.ModBlockEntities;
import com.nieslregen.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CharCoalPileBlock extends BaseEntityBlock {

    public CharCoalPileBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(CharCoalPileBlock::new);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CharCoalPileEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(final Level level, final BlockState blockState, final BlockEntityType<T> type) {
        return createTickerHelper(
                type,
                ModBlockEntities.CHARCOAL_PILE_ENTITY,
                CharCoalPileEntity::serverTick
        );
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> drops = new ArrayList<>();

        Optional<BlockEntity> optEntity = Optional.ofNullable(builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY));

        optEntity.ifPresent(blockEntity -> {
            if (blockEntity instanceof CharCoalPileEntity) {
                CharCoalPileEntity entity = (CharCoalPileEntity)blockEntity;
                if (entity.isFinished()) {
                    drops.add(new ItemStack(Items.CHARCOAL, 5));
                    drops.add(new ItemStack(ModItems.SOOT, 2));
                } else if (entity.getBurnProgressRatio() > 0.5F) {
                    drops.add(new ItemStack(ModItems.SOOT, 1));
                }
            }
        });

        return drops;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        ItemStack itemInHand = player.getItemInHand(hand);

        if (level.getBlockEntity(pos) instanceof CharCoalPileEntity) {
            CharCoalPileEntity entity = (CharCoalPileEntity)level.getBlockEntity(pos);

            if (Items.FLINT_AND_STEEL.equals(itemInHand.getItem())) {
                entity.ignite();
            }
        }
        return InteractionResult.SUCCESS;
    }

    //    @Override
//    public void animateTick(final BlockState state, final Level level, final BlockPos pos, final RandomSource random) {
//        if ((Boolean)state.getValue(LIT)) {
//            double x = pos.getX() + 0.5;
//            double y = pos.getY();
//            double z = pos.getZ() + 0.5;
//            if (random.nextDouble() < 0.1) {
//                level.playLocalSound(x, y, z, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
//            }
//
//            Direction direction = state.getValue(FACING);
//            Direction.Axis axis = direction.getAxis();
//            double r = 0.52;
//            double ss = random.nextDouble() * 0.6 - 0.3;
//            double dx = axis == Direction.Axis.X ? direction.getStepX() * 0.52 : ss;
//            double dy = random.nextDouble() * 6.0 / 16.0;
//            double dz = axis == Direction.Axis.Z ? direction.getStepZ() * 0.52 : ss;
//            level.addParticle(ParticleTypes.SMOKE, x + dx, y + dy, z + dz, 0.0, 0.0, 0.0);
//            level.addParticle(ParticleTypes.FLAME, x + dx, y + dy, z + dz, 0.0, 0.0, 0.0);
//        }
//    }
}

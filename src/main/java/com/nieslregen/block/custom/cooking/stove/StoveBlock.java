package com.nieslregen.block.custom.cooking.stove;

import com.mojang.serialization.MapCodec;
import com.nieslregen.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

public class StoveBlock extends BaseEntityBlock {

    public static final BooleanProperty LIT = BooleanProperty.create("lit");
    public static final IntegerProperty LOG = IntegerProperty.create("log", 0, 4);
    public static final EnumProperty<Direction> FACING = EnumProperty.create("facing", Direction.class);

    public StoveBlock(Properties properties) {
        super(properties);
        registerDefaultState(
                this.stateDefinition.any()
                        .setValue(LIT, false)
                        .setValue(LOG, 0)
                        .setValue(FACING, Direction.NORTH)
        );
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(StoveBlock::new);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
        builder.add(LOG);
        builder.add(FACING);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (itemStack.is(Items.FLINT_AND_STEEL) && !state.getValue(LIT) && state.getValue(LOG) != 0) {
            level.setBlockAndUpdate(pos, state.setValue(LIT, true));
            return reduceItemDurability(itemStack, player, hand);
        }

        if (isShovel(itemStack) && state.getValue(LIT)) {
            level.setBlockAndUpdate(pos, state.setValue(LIT, false));
            player.playSound(SoundEvents.FIRE_EXTINGUISH, 1.0F, 1.0F);
            return reduceItemDurability(itemStack, player, hand);
        }

        if (itemStack.is(ItemTags.LOGS)) {
            addLog(level, pos, state);
            itemStack.consume(1, player);
            player.playSound(SoundEvents.WOOD_PLACE, 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    private InteractionResult reduceItemDurability(ItemStack itemStack, Player player, InteractionHand hand) {
        itemStack.hurtAndBreak(1, player, hand);
        return InteractionResult.SUCCESS;
    }

    public boolean isShovel(ItemStack itemStack) {
        return itemStack.is(Items.WOODEN_SHOVEL)
                || itemStack.is(Items.STONE_SHOVEL)
                || itemStack.is(Items.COPPER_SHOVEL)
                || itemStack.is(Items.IRON_SHOVEL)
                || itemStack.is(Items.GOLDEN_SHOVEL)
                || itemStack.is(Items.DIAMOND_SHOVEL)
                || itemStack.is(Items.NETHERITE_SHOVEL);
    }

    private boolean addLog(Level level, BlockPos pos, BlockState state) {
        int logsDeposited = state.getValue(LOG);
        if (logsDeposited < 4) {
            level.setBlockAndUpdate(pos, state.setValue(LOG, logsDeposited + 1));
            return true;
        }
        return false;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new StoveEntity(worldPosition, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return createTickerHelper(
                type,
                ModBlockEntities.STOVE_ENTITY,
                StoveEntity::serverTick
        );
    }
}

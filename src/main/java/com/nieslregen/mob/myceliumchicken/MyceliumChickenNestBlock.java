package com.nieslregen.mob.myceliumchicken;

import com.mojang.serialization.MapCodec;
import com.nieslregen.block.ModBlockEntities;
import com.nieslregen.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public class MyceliumChickenNestBlock extends BaseEntityBlock {

    private static final VoxelShape SHAPE;

    public static final BooleanProperty HAS_EGG = BooleanProperty.create("has_egg");
    public static final BooleanProperty IS_INCUBATING = BooleanProperty.create("incubating");

    public MyceliumChickenNestBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any()
                .setValue(HAS_EGG, false)
                .setValue(IS_INCUBATING, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HAS_EGG,  IS_INCUBATING);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(com.nieslregen.mob.myceliumchicken.MyceliumChickenNestBlock::new);
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

    public ItemStack takeEgg(MyceliumChickenNestEntity entity, BlockState state, LivingEntity player) {
        if (state.getValue(HAS_EGG)) {
            entity.stealEgg(entity.getLevel(), entity.getBlockPos(),player);
            return new ItemStack(ModItems.MYCELIUM_CHICKEN_EGG);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return createTickerHelper(
                type,
                ModBlockEntities.MYCELIUM_CHICKEN_NEST_ENTITY,
                MyceliumChickenNestEntity::serverTick
        );
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (player.getItemInHand(hand).isEmpty()) {
            if (level.getBlockEntity(pos) instanceof MyceliumChickenNestEntity entity) {
                ItemStack result = takeEgg(entity, state, player);
                player.setItemInHand(hand, result);
            }
        }
    return InteractionResult.SUCCESS;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);
    }

    public static boolean isOnMycelium(final BlockGetter level, final BlockPos pos) {
        return isMycelium(level, pos.below());
    }

    public static boolean isMycelium(final BlockGetter level, final BlockPos pos) {
        return level.getBlockState(pos).is(Blocks.MYCELIUM);
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
        SHAPE = Block.column((double)12.0F, (double)0.0F, (double)0.1F);
    }

}

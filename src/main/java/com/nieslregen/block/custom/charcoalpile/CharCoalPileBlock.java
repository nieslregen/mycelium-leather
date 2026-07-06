package com.nieslregen.block.custom.charcoalpile;

import com.mojang.serialization.MapCodec;
import com.nieslregen.block.ModBlockEntities;
import com.nieslregen.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CharCoalPileBlock extends BaseEntityBlock {

    private static final VoxelShape SHAPE;

    public CharCoalPileBlock(Properties properties) {
        super(properties);
        registerDefaultState(
                getStateDefinition()
                        .any()
                        .setValue(STAGE, 0)
                        .setValue(LIT, false)
        );
    }

    public static final int MAX_STAGE = 2;
    public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, MAX_STAGE);
    public static final BooleanProperty LIT = BooleanProperty.create("lit");

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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STAGE, LIT);
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> drops = new ArrayList<>();

        Optional<BlockEntity> optEntity = Optional.ofNullable(builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY));

        optEntity.ifPresent(blockEntity -> {
            if (blockEntity instanceof CharCoalPileEntity) {
                CharCoalPileEntity entity = (CharCoalPileEntity)blockEntity;
                if (entity.isFinished()) {
                    drops.add(new ItemStack(Items.CHARCOAL, 15));
                    drops.add(new ItemStack(ModItems.SOOT, 2));
                } else if (entity.getBurnProgressRatio() > 0.5F) {
                    drops.add(new ItemStack(Items.CHARCOAL, 10));
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
                itemInHand.hurtAndBreak(1, player, hand);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {

        if (state.getValue(LIT)) {
            SimpleParticleType smokeParticle = ParticleTypes.CAMPFIRE_COSY_SMOKE;
            level.addAlwaysVisibleParticle(
                    smokeParticle,
                    true,
                    pos.getX() + 0.5 + random.nextDouble() / 3.0 * (random.nextBoolean() ? 1 : -1),
                    pos.getY() + random.nextDouble() + random.nextDouble(),
                    pos.getZ() + 0.5 + random.nextDouble() / 3.0 * (random.nextBoolean() ? 1 : -1),
                    0.0,
                    0.07,
                    0.0
            );
            level.addParticle(
                    ParticleTypes.SMOKE,
                    pos.getX() + 0.5 + random.nextDouble() / 4.0 * (random.nextBoolean() ? 1 : -1),
                    pos.getY() + 0.4,
                    pos.getZ() + 0.5 + random.nextDouble() / 4.0 * (random.nextBoolean() ? 1 : -1),
                    0.0,
                    0.005,
                    0.0
            );

        }
    }

    @Override
    protected VoxelShape getShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState state) { return true; }

    static {
        SHAPE = Block.column((double)16.0F, (double)0.0F, (double)12.0F);
    }
}

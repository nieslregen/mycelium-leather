package com.nieslregen.block.custom.mushroomstem;

import com.mojang.serialization.MapCodec;
import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.block.ModBlockEntities;
import com.nieslregen.mob.ModEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jspecify.annotations.Nullable;

public class MushroomStemHollowBlock extends BaseEntityBlock {

    public static final EnumProperty<Direction> FACING = EnumProperty.create("facing", Direction.class);

    public MushroomStemHollowBlock(Properties properties) {
        super(properties);
        registerDefaultState(
                getStateDefinition()
                        .any()
                        .setValue(FACING, Direction.NORTH)
        );
    }


    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack destroyedWith) {
        super.playerDestroy(level, player, pos, state, blockEntity, destroyedWith);
        MyceliumLeatherMod.LOGGER.info("Here 1");
        if (blockEntity instanceof MushroomStemHollowEntity e) {
            MyceliumLeatherMod.LOGGER.info("Here 2");

            e.storedOccupants.forEach(
                    occupantData ->
                    e.releaseOccupant(level, pos, state, occupantData.toOccupant(), null, ModEntityTypes.SQUIRREL)
            );
        }
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(MushroomStemHollowBlock::new);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new MushroomStemHollowEntity(worldPosition, blockState);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return createTickerHelper(
                type,
                ModBlockEntities.MUSHROOM_STEM_HOLLOW_ENTITY,
                MushroomStemHollowEntity::serverTick
        );
    }
}

package com.nieslregen.block.custom.herbariumpress;

import com.nieslregen.block.ModBlockEntities;
import com.nieslregen.block.container.ImplementedContainer;
import com.nieslregen.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.Map;

public class HerbariumPressEntity extends BlockEntity implements ImplementedContainer, MenuProvider, WorldlyContainer {
    public static final int CONTAINER_SIZE = 2;
    private final NonNullList<ItemStack> items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);

    private final ContainerData dataAccess;

    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{1};
    private static final int[] SLOTS_FOR_SIDES = new int[]{0};

    // Pressing process
    private int pressProgress = 0;
    protected static int maxProgress = 300;

    private static final Map<Item, Item> RECIPES = Map.of(
            ModItems.MYCELIUM_PATCH_DRIED, ModItems.MYCELIUM_LEATHER
    );

    public static final Component CONTAINER_TITLE = Component.translatable("container.herbariumpress");

    public HerbariumPressEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.HERBARIUM_PRESS_ENTITY, pos, state);
        this.dataAccess = new ContainerData() {
            @Override
            public int get(int dataId) {
                int var10000;
                switch (dataId) {
                    case 0 -> var10000 = HerbariumPressEntity.this.pressProgress;
                    default -> var10000 = 0;
                }

                return var10000;
            }

            @Override
            public void set(int dataId, int value) {
                switch (dataId) {
                    case 0 -> HerbariumPressEntity.this.pressProgress = value;
                }

            }

            @Override
            public int getCount() {
                return 1;
            }
        };
    }

    public static void serverTick(final Level level, final BlockPos blockPos, final BlockState blockState, final HerbariumPressEntity entity) {
        if(level.isClientSide()) {
            return;
        }

        if (isPressable(entity.getItem(0).getItem())) {
            entity.pressProgress = entity.pressProgress + 1;

            if (entity.pressProgress >= entity.maxProgress) {
                entity.pressProgress = 0;
                entity.removeItem(0,1);

                if (entity.getItem(1).count() <1 ) {
                    entity.setItem(1, new ItemStack(ModItems.MYCELIUM_LEATHER));
                } else {
                    entity.getItem(1).grow(1);
                }

            }

        } else if (entity.pressProgress != 0) {
            entity.pressProgress = 0;
        }
    }

    public static boolean isPressable(final Item item) {
        return RECIPES.containsKey(item);
    }



    @Override
    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    public @NonNull Component getDisplayName() {
        return CONTAINER_TITLE;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new HerbariumPressMenu(containerId, inventory, this, this.dataAccess);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        ContainerHelper.loadAllItems(input, this.items);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        ContainerHelper.saveAllItems(output, this.items);
        super.saveAdditional(output);
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        if (direction == Direction.DOWN) {
            return SLOTS_FOR_DOWN;
        } else {
            return direction == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(final int slot, final ItemStack itemStack, @org.jspecify.annotations.Nullable final Direction direction) {
        return this.canPlaceItem(slot, itemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(final int slot, final ItemStack itemStack, final Direction direction) {
        return direction == Direction.DOWN && slot == 1 ? itemStack.is(Items.WATER_BUCKET) || itemStack.is(Items.BUCKET) : true;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    @Override
    public void setChanged() {
        super.setChanged();

        if (level == null) return;

        BlockState state = getBlockState();
        level.sendBlockUpdated(worldPosition, state, state, Block.UPDATE_ALL);
    }
}

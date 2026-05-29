package com.nieslregen.block.herbariumpress;

import com.nieslregen.block.ModMenuType;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class HerbariumPressMenu extends AbstractContainerMenu {
    private final Container inputContainer;
    private final Container outputContainer;

    protected final ContainerData pressData;

    final Slot inputSlot;
    final Slot resultSlot;

    // client-side constructor
    public HerbariumPressMenu(final int containerId, final Inventory inventory) {
        this(containerId, inventory, new SimpleContainer(HerbariumPressEntity.CONTAINER_SIZE), new SimpleContainerData(1));
    }

    // server-side constructor
    public HerbariumPressMenu(final int containerId, Inventory inventory, final Container container, final ContainerData containerData) {
        super(ModMenuType.HERBARIUM_PRESS_MENU_TYPE, containerId);
        checkContainerSize(container, HerbariumPressEntity.CONTAINER_SIZE);

        this.pressData = containerData;
        this.addDataSlots(containerData);

        this.inputContainer = container;
        this.outputContainer = container;

        container.startOpen(inventory.player);

        this.inputSlot = this.addSlot(new Slot(this.inputContainer, 0, (26 + 3 * 18), (17 + 0 * 18) ));
        this.resultSlot = this.addSlot(new Slot(this.outputContainer, 1, (26 + 3 * 18 ), (17 + 2 * 18) ));

        this.addStandardInventorySlots(inventory, 8, 84);
    }

    public int getPressingProgress() {
        return pressData.get(0);
    }


    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        Slot slot = this.slots.get(slotIndex);

        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();
        ItemStack clicked = stack.copy();

        if (slotIndex < this.inputContainer.getContainerSize()) {
            if (!this.moveItemStackTo(stack, this.inputContainer.getContainerSize(), this.slots.size(), true)) {
                return ItemStack.EMPTY;
            }
        } else if (!this.moveItemStackTo(stack, 0, this.inputContainer.getContainerSize(), false)) {
            return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        return clicked;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.inputContainer.stillValid(player);
    }



}

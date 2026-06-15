package com.nieslregen.block.custom.tinycauldron;

import com.nieslregen.block.container.ImplementedContainer;
import com.nieslregen.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.nieslregen.block.ModBlockEntities.TINY_CAULDRON_ENTITY;

public class TinyCauldronEntity extends BlockEntity implements ImplementedContainer {

    private final NonNullList<ItemStack> items = NonNullList.withSize(16, ItemStack.EMPTY);

    private List<TinyCauldronRecipe> recipes = List.of(
            new TinyCauldronRecipe(List.of(Items.HONEY_BOTTLE, ModItems.SOOT), ModItems.SOOT_INK)
    );


    public TinyCauldronEntity(BlockPos worldPosition, BlockState blockState) {
        super(TINY_CAULDRON_ENTITY, worldPosition, blockState);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    // For reference: CampfireBlockEntity
    public boolean placeIngredient(final ServerLevel level, final LivingEntity entity, final ItemStack itemStack) {
        for (int slot = 0; slot < items.size(); slot++) {
            ItemStack stack = items.get(slot);
            if (stack.isEmpty()) {
                this.items.set(
                        slot,
                        itemStack.consumeAndReturn(1, entity)
                );
                level.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(entity, this.getBlockState()));
                this.markUpdated();

                Optional<Item> result = checkRecipe();
                if (result.isPresent()) {
                    for (int i = 0; i < items.size(); i++) {
                        items.get(i).shrink(1);
                        level.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(entity, this.getBlockState()));
                        level.sendBlockUpdated(entity.getOnPos(), this.getBlockState(), this.getBlockState(), 3);
                    }
                    Containers.dropItemStack(level, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(result.get()));
                }

                return true;
            }
        }
        return false;
    }

    private Optional<Item> checkRecipe() {
        List<Item> currentIngredients = new ArrayList<>();
        for (int slot = 0; slot < items.size(); slot++) {
            ItemStack stackOfSlot = items.get(slot);
            if (!stackOfSlot.isEmpty()) {
                currentIngredients.add(stackOfSlot.getItem());
            }
        }

        for (TinyCauldronRecipe recipe : recipes) {
            if (new HashSet<>(recipe.recipeComponents()).equals(new HashSet<>(currentIngredients))) {
                return Optional.of(recipe.resultItem());
            }
        }
        return Optional.empty();
    }


    private void markUpdated() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }


}


package com.nieslregen.block.custom.tinycauldron;

import com.nieslregen.block.container.ImplementedContainer;
import com.nieslregen.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.*;

import static com.nieslregen.block.ModBlockEntities.TINY_CAULDRON_ENTITY;

public class TinyCauldronEntity extends BlockEntity implements ImplementedContainer {

    private final NonNullList<ItemStack> items = NonNullList.withSize(16, ItemStack.EMPTY);

    private ItemStack brewingResult = ItemStack.EMPTY;
    private int currentBrewTime = 0;
    private final int brewTime = 200;
    private final String CURRENT_BREW_TIME_IDENTIFIER = "current_brew_time";
    private final String BREWING_RESULT_IDENTIFIER = "brewing_result";
    private final String BREWING_RESULT_AMOUNT_IDENTIFIER = "brewing_result_amount";

    private final List<TinyCauldronRecipe> recipes = List.of(
            new TinyCauldronRecipe(
                    1,
                    List.of(new ItemStack(Items.HONEY_BOTTLE), new ItemStack(ModItems.SOOT)),
                    new ItemStack(ModItems.SOOT_INK)
            ),
            new TinyCauldronRecipe(
                    2,
                    List.of(new ItemStack(Items.RED_MUSHROOM, 2), new ItemStack(Items.ROTTEN_FLESH, 3), new ItemStack(Items.ARROW), new ItemStack(ModItems.SUSPICIOUS_FLASK)),
                    new ItemStack(ModItems.ARROW_OF_ILLNESS)
            )
    );

    private final List<List<Item>> recipesAsItemList = new ArrayList<>();


    public TinyCauldronEntity(BlockPos worldPosition, BlockState blockState) {
        super(TINY_CAULDRON_ENTITY, worldPosition, blockState);
        recipes.forEach(recipe -> {
            recipesAsItemList.add(convertItemStackListToItemList(recipe.recipeComponents()));
        });
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, TinyCauldronEntity entity) {
        if (entity.brewingResult != ItemStack.EMPTY) {

            if (!state.getValue(TinyCauldronBlock.BREWING)) {
                level.setBlockAndUpdate(pos, state.setValue(TinyCauldronBlock.BREWING, true));
            }

            if (entity.currentBrewTime < entity.brewTime) {
                entity.currentBrewTime = entity.currentBrewTime + 1;
            } else {
                Containers.dropItemStack(
                        level,
                        entity.getBlockPos().getX(),
                        entity.getBlockPos().getY(),
                        entity.getBlockPos().getZ(),
                        entity.brewingResult
                );

                entity.currentBrewTime = 0;
                entity.brewingResult = ItemStack.EMPTY;
                level.setBlockAndUpdate(pos, state.setValue(TinyCauldronBlock.BREWING, false));
            }
        }
    }

    public boolean placeIngredient(final ServerLevel level, final LivingEntity entity, final ItemStack itemStack, final BlockPos pos) {

        for (int slot = 0; slot < items.size(); slot++) {
            ItemStack stack = items.get(slot);
            if (stack.isEmpty()) {
                this.items.set(
                        slot,
                        itemStack.consumeAndReturn(1, entity)
                );
                level.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(entity, this.getBlockState()));
                this.markUpdated();

                Optional<ItemStack> result = checkRecipe();
                if (result.isPresent()) {
                    for (int i = 0; i < items.size(); i++) {
                        items.get(i).shrink(1);
                        level.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(entity, this.getBlockState()));
                        level.sendBlockUpdated(entity.getOnPos(), this.getBlockState(), this.getBlockState(), 3);
                    }
                    BlockEntity blockEntity = level.getBlockEntity(pos);
                    if (blockEntity instanceof TinyCauldronEntity) {
                        TinyCauldronEntity tinyCauldron = (TinyCauldronEntity) blockEntity;
                        tinyCauldron.brewingResult = result.get();
                    }
                }
                return true;
            }
        }
        return false;
    }

    private Optional<ItemStack> checkRecipe() {
        List<Item> currentIngredients = convertItemStackListToItemList(items);

        int index = 0;
        for (TinyCauldronRecipe recipe : recipes) {
            if (isSubset(recipesAsItemList.get(index), currentIngredients)) {

                if (isSubset(currentIngredients, recipesAsItemList.get(index))) {
                    return Optional.of(recipe.resultItem().copy());
                } else {
                    return Optional.empty();
                }
            }
            index ++;
        }
        return Optional.of(new ItemStack(ModItems.SUSPICIOUS_FLASK, currentIngredients.size()));
    }


    private void markUpdated() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    private boolean isSubset(List<Item> source, List<Item> toBeValidated) {
        List<Item> sourceTmp = new ArrayList<>(source);
        List<Item> toBeValidatedTmp = new ArrayList<>(toBeValidated);

        for (Item item : toBeValidatedTmp) {
            if (sourceTmp.contains(item)) {
                sourceTmp.remove(item);
            } else {
                return  false;
            }
        }
        return true;
    }

    private List<Item> convertItemStackListToItemList(List<ItemStack> stacks) {
        List<Item> items = new ArrayList<>();
        for (int slot = 0; slot < stacks.size(); slot++) {
            ItemStack stack = stacks.get(slot);
            if (!stack.isEmpty()) {
                for (int i = 0; i < stack.count(); i++) {
                    items.add(stack.getItem());
                }
            }
        }
        return items;
    }

    private Optional<TinyCauldronRecipe> getRecipeByIdentifier(int identifier) {
        return recipes.stream()
                .filter(r -> r.identifier() == identifier)
                .findFirst();
    }

    private Optional<Integer> findRecipeByBrewingResult(ItemStack itemStack) {
        return recipes.stream()
                .filter(r -> r.resultItem().getItem() == itemStack.getItem())
                .findFirst()
                .map(TinyCauldronRecipe::identifier);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        ContainerHelper.loadAllItems(input, this.items);

        currentBrewTime = input.getIntOr(CURRENT_BREW_TIME_IDENTIFIER, 0);

        int amount = input.getIntOr(BREWING_RESULT_AMOUNT_IDENTIFIER, 0);
        Optional<Integer> identifier = input.getInt(BREWING_RESULT_IDENTIFIER);

        Item b = ModItems.SUSPICIOUS_FLASK;

        if (identifier.isPresent()) {
            Optional<TinyCauldronRecipe> optRecipe = getRecipeByIdentifier(identifier.get());
            if  (optRecipe.isPresent()) {
                b = optRecipe.get().resultItem().getItem();
            }
        }
        brewingResult = new ItemStack(b, amount);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, this.items);
        output.putInt(CURRENT_BREW_TIME_IDENTIFIER, currentBrewTime);

        if (brewingResult != ItemStack.EMPTY) {
            findRecipeByBrewingResult(brewingResult).ifPresent(identifier -> output.putInt(BREWING_RESULT_IDENTIFIER, identifier));
            output.putInt(BREWING_RESULT_AMOUNT_IDENTIFIER, brewingResult.getCount());
        }

    }
}


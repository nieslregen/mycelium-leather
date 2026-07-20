package com.nieslregen.block.custom.cooking;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.block.ModBlocks;
import com.nieslregen.block.container.ImplementedContainer;
import com.nieslregen.block.custom.cooking.fryingpan.FryingPanEntity;
import com.nieslregen.block.custom.cooking.tinycauldron.TinyCauldronEntity;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AbstractCookingUtilEntity extends BlockEntity implements ImplementedContainer {

    private final NonNullList<ItemStack> items = NonNullList.withSize(16, ItemStack.EMPTY);

    // ToDo: rework the filtering for the correct recipe, because this approach might be very resource hungry

    private ItemStack brewingResult = ItemStack.EMPTY;
    private int currentBrewTime = 0;
    private final int brewTime = 200;
    private final String CURRENT_BREW_TIME_IDENTIFIER = "current_brew_time";
    private final String BREWING_RESULT_IDENTIFIER = "brewing_result";
    private final String BREWING_RESULT_AMOUNT_IDENTIFIER = "brewing_result_amount";

    private final List<CookingRecipe> recipes = List.of(
            new CookingRecipe(
                    1,
                    List.of(new ItemStack(Items.HONEY_BOTTLE), new ItemStack(ModItems.SOOT)),
                    new ItemStack(ModItems.SOOT_INK),
                    false,
                    CookingRecipe.UtilType.TinyCauldron
            ),
            new CookingRecipe(
                    2,
                    List.of(
                            new ItemStack(Items.RED_MUSHROOM, 2),
                            new ItemStack(Items.ROTTEN_FLESH, 3),
                            new ItemStack(Items.ARROW),
                            new ItemStack(ModItems.SUSPICIOUS_FLASK)
                    ),
                    new ItemStack(ModItems.ARROW_OF_ILLNESS),
                    false,
                    CookingRecipe.UtilType.TinyCauldron
            ),
            new CookingRecipe(
                    3,
                    List.of(
                            new ItemStack(Items.BROWN_MUSHROOM, 3),
                            new ItemStack(Items.RED_MUSHROOM, 3),
                            new ItemStack(ModItems.TRUFFLE, 2),
                            new ItemStack(Items.MUSHROOM_STEW),
                            new ItemStack(ModItems.MYCELIUM_CHICKEN_EGG),
                            new ItemStack(ModItems.SALT)
                    ),
                    new ItemStack(ModBlocks.FEAST_OF_THE_MUSHROOM_FIELDS),
                    true,
                    CookingRecipe.UtilType.FryingPan
            ),
            new CookingRecipe(4, List.of(new ItemStack(Items.EGG, 3), new ItemStack(ModItems.SALT)), new ItemStack(ModItems.SCRAMBLED_EGGS, 3), true, CookingRecipe.UtilType.FryingPan),
            new CookingRecipe(5, List.of(new ItemStack(Items.BROWN_EGG, 3), new ItemStack(ModItems.SALT)), new ItemStack(ModItems.SCRAMBLED_EGGS, 3), true, CookingRecipe.UtilType.FryingPan),
            new CookingRecipe(6, List.of(new ItemStack(Items.BLUE_EGG, 3), new ItemStack(ModItems.SALT)), new ItemStack(ModItems.SCRAMBLED_EGGS, 3), true, CookingRecipe.UtilType.FryingPan),
            new CookingRecipe(7, List.of(new ItemStack(ModItems.MYCELIUM_CHICKEN_EGG, 3), new ItemStack(ModItems.SALT)), new ItemStack(ModItems.SCRAMBLED_EGGS, 3), true, CookingRecipe.UtilType.FryingPan),
            new CookingRecipe(8, List.of(new ItemStack(Items.POTATO, 2)), new ItemStack(ModItems.PAN_FRIED_POTATOES), true, CookingRecipe.UtilType.FryingPan),

            new CookingRecipe(9, List.of(new ItemStack(Items.CHICKEN)), new ItemStack(Items.COOKED_CHICKEN), true, CookingRecipe.UtilType.FryingPan),
            new CookingRecipe(10, List.of(new ItemStack(Items.MUTTON)), new ItemStack(Items.COOKED_MUTTON), true, CookingRecipe.UtilType.FryingPan),
            new CookingRecipe(11, List.of(new ItemStack(Items.BEEF)), new ItemStack(Items.COOKED_BEEF), true, CookingRecipe.UtilType.FryingPan),
            new CookingRecipe(12, List.of(new ItemStack(Items.PORKCHOP)), new ItemStack(Items.COOKED_PORKCHOP), true, CookingRecipe.UtilType.FryingPan),
            new CookingRecipe(13, List.of(new ItemStack(Items.RABBIT)), new ItemStack(Items.COOKED_RABBIT), true, CookingRecipe.UtilType.FryingPan),
            new CookingRecipe(14, List.of(new ItemStack(Items.COD)), new ItemStack(Items.COOKED_COD), true, CookingRecipe.UtilType.FryingPan),
            new CookingRecipe(15, List.of(new ItemStack(Items.SALMON)), new ItemStack(Items.COOKED_SALMON), true, CookingRecipe.UtilType.FryingPan)
    );

    private final List<List<Item>> recipesAsItemList = new ArrayList<>();


    public AbstractCookingUtilEntity(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
        recipes.forEach(recipe -> {
            recipesAsItemList.add(convertItemStackListToItemList(recipe.recipeComponents()));
        });
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AbstractCookingUtilEntity entity) {
        if (entity.brewingResult != ItemStack.EMPTY) {

            if (!state.getValue(AbstractCookingUtilBlock.BREWING)) {
                level.setBlockAndUpdate(pos, state.setValue(AbstractCookingUtilBlock.BREWING, true));
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
                level.setBlockAndUpdate(pos, state.setValue(AbstractCookingUtilBlock.BREWING, false));
            }
        }
    }

    public boolean placeIngredient(final ServerLevel level, final LivingEntity entity, final ItemStack itemStack, final BlockPos pos) {

        boolean isLit = false;
        BlockState blockState = level.getBlockState(pos.below());

        if (blockState.getBlock().equals(Blocks.CAMPFIRE)) {
            blockState.getValue(CampfireBlock.LIT);
            isLit = true;
        }

        for (int slot = 0; slot < items.size(); slot++) {
            ItemStack stack = items.get(slot);
            if (stack.isEmpty()) {
                this.items.set(
                        slot,
                        itemStack.consumeAndReturn(1, entity)
                );
                level.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(entity, this.getBlockState()));
                this.markUpdated();

                Optional<ItemStack> result = checkRecipe(isLit, level.getBlockEntity(pos));
                if (result.isPresent()) {
                    for (int i = 0; i < items.size(); i++) {
                        items.get(i).shrink(1);
                        level.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(entity, this.getBlockState()));
                        level.sendBlockUpdated(entity.getOnPos(), this.getBlockState(), this.getBlockState(), 3);
                    }
                    BlockEntity blockEntity = level.getBlockEntity(pos);
                    if (blockEntity instanceof AbstractCookingUtilEntity util) {
                        util.brewingResult = result.get();
                    }
                }
                return true;
            }
        }
        return false;
    }

    private Optional<ItemStack> checkRecipe(boolean litStatus, BlockEntity blockEntity) {
        List<Item> currentIngredients = convertItemStackListToItemList(items);

        int index = 0;
        for (CookingRecipe recipe : recipes) {

            if (litStatus == recipe.needsFire() && recipe.type().equals(getEntityUtilType(blockEntity))) {
                if (isSubset(recipesAsItemList.get(index), currentIngredients)) {
                    if (isSubset(currentIngredients, recipesAsItemList.get(index))) {
                        // ToDo: rework cauldron recipe resultItem because it touches the data directly
                        return Optional.of(recipe.resultItem().copy());
                    } else {
                        return Optional.empty();
                    }
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

    private Optional<CookingRecipe> getRecipeByIdentifier(int identifier) {
        return recipes.stream()
                .filter(r -> r.identifier() == identifier)
                .findFirst();
    }

    private Optional<Integer> findRecipeByBrewingResult(ItemStack itemStack) {
        return recipes.stream()
                .peek(x -> MyceliumLeatherMod.LOGGER.info("equation: {} eqauls {}", x.resultItem().getItem(), itemStack.getItem()))
                .filter(r -> r.resultItem().getItem() == itemStack.getItem())
                .findFirst()
                .map(CookingRecipe::identifier);
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
            Optional<CookingRecipe> optRecipe = getRecipeByIdentifier(identifier.get());
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

    private CookingRecipe.UtilType getEntityUtilType(BlockEntity entity) {
        if (entity instanceof TinyCauldronEntity) {
            return CookingRecipe.UtilType.TinyCauldron;
        }

        if (entity instanceof FryingPanEntity) {
            return CookingRecipe.UtilType.FryingPan;
        }
        return CookingRecipe.UtilType.None;
    }
}

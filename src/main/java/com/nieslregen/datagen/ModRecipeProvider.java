package com.nieslregen.datagen;

import com.nieslregen.block.ModBlocks;
import com.nieslregen.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
        return new RecipeProvider(provider, recipeOutput) {
            @Override
            public void buildRecipes() {
                List<ItemLike> MYCELIUM_SMELTABLES = List.of(ModItems.MYCELIUM_PATCH);
                oreSmelting(MYCELIUM_SMELTABLES, RecipeCategory.MISC, CookingBookCategory.MISC, ModItems.MYCELIUM_PATCH_DRIED, .05f, 100, "mycelium-leather");

                shaped(RecipeCategory.MISC, ModItems.MYCELIUM_LEATHER)
                        .pattern("DDD")
                        .pattern("DDD")
                        .pattern("DDD")
                        .define('D', ModItems.MYCELIUM_PATCH_DRIED)
                        .unlockedBy(getHasName(ModItems.MYCELIUM_PATCH_DRIED), has(ModItems.MYCELIUM_PATCH_DRIED))
                        .group("mycelium-leather")
                        .save(output, "mycelium_leather_from_dried_mycelium_leather");

                shaped(RecipeCategory.MISC, Items.BOOK)
                        .pattern("   ")
                        .pattern("PP ")
                        .pattern("PL ")
                        .define('L', ModItems.MYCELIUM_LEATHER)
                        .unlockedBy(getHasName(ModItems.MYCELIUM_LEATHER), has(ModItems.MYCELIUM_LEATHER))
                        .define('P', Items.PAPER)
                        .unlockedBy(getHasName(Items.PAPER), has(Items.PAPER))
                        .group("mycelium-leather")
                        .save(output, "book_from_mycelium_leather_and_paper");

                shaped(RecipeCategory.MISC, Items.BUNDLE)
                        .pattern(" S ")
                        .pattern(" L ")
                        .pattern("   ")
                        .define('L', ModItems.MYCELIUM_LEATHER)
                        .unlockedBy(getHasName(ModItems.MYCELIUM_LEATHER), has(ModItems.MYCELIUM_LEATHER))
                        .define('S', Items.STRING)
                        .unlockedBy(getHasName(Items.STRING), has(Items.STRING))
                        .group("mycelium-leather")
                        .save(output, "bundle_from_mycelium_leather_and_string");

                shaped(RecipeCategory.MISC, Items.ITEM_FRAME)
                        .pattern("SSS")
                        .pattern("SLS")
                        .pattern("SSS")
                        .define('L', ModItems.MYCELIUM_LEATHER)
                        .unlockedBy(getHasName(ModItems.MYCELIUM_LEATHER), has(ModItems.MYCELIUM_LEATHER))
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                        .group("mycelium-leather")
                        .save(output, "item_frame_from_mycelium_leather_and_stick");

                shaped(RecipeCategory.MISC, ModItems.MYCELIUM_PATCH)
                        .pattern("   ")
                        .pattern("WB ")
                        .pattern("RW ")
                        .define('W', Items.WHEAT)
                        .define('B', Items.BROWN_MUSHROOM)
                        .define('R', Items.RED_MUSHROOM)
                        .unlockedBy(getHasName(Items.WHEAT), has(Items.WHEAT))
                        .unlockedBy(getHasName(Items.BROWN_MUSHROOM), has(Items.BROWN_MUSHROOM))
                        .unlockedBy(getHasName(Items.RED_MUSHROOM), has(Items.RED_MUSHROOM))
                        .group("mycelium-leather")
                        .save(output, "mycelium_patch_from_mushrooms_and_wheat");

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.HERBARIUM_PRESS)
                        .pattern("SSS")
                        .pattern("IRI")
                        .pattern("SSS")
                        .define('S', Items.OAK_SLAB)
                        .define('I', Items.IRON_NUGGET)
                        .define('R', Items.STRING)
                        .unlockedBy(getHasName(Items.OAK_SLAB), has(Items.OAK_SLAB))
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .unlockedBy(getHasName(Items.STRING), has(Items.STRING))
                        .group("mycelium-leather")
                        .save(output, "press_from_oak_slabs");

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.HERBARIUM_PRESS)
                        .pattern("SSS")
                        .pattern("IRI")
                        .pattern("SSS")
                        .define('S', Items.ACACIA_SLAB)
                        .define('I', Items.IRON_NUGGET)
                        .define('R', Items.STRING)
                        .unlockedBy(getHasName(Items.ACACIA_SLAB), has(Items.ACACIA_SLAB))
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .unlockedBy(getHasName(Items.STRING), has(Items.STRING))
                        .group("mycelium-leather")
                        .save(output, "press_from_acacia_slabs");

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.HERBARIUM_PRESS)
                        .pattern("SSS")
                        .pattern("IRI")
                        .pattern("SSS")
                        .define('S', Items.BIRCH_SLAB)
                        .define('I', Items.IRON_NUGGET)
                        .define('R', Items.STRING)
                        .unlockedBy(getHasName(Items.BIRCH_SLAB), has(Items.BIRCH_SLAB))
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .unlockedBy(getHasName(Items.STRING), has(Items.STRING))
                        .group("mycelium-leather")
                        .save(output, "press_from_birch_slabs");

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.HERBARIUM_PRESS)
                        .pattern("SSS")
                        .pattern("IRI")
                        .pattern("SSS")
                        .define('S', Items.CHERRY_SLAB)
                        .define('I', Items.IRON_NUGGET)
                        .define('R', Items.STRING)
                        .unlockedBy(getHasName(Items.CHERRY_SLAB), has(Items.CHERRY_SLAB))
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .unlockedBy(getHasName(Items.STRING), has(Items.STRING))
                        .group("mycelium-leather")
                        .save(output, "press_from_cherry_slabs");

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.HERBARIUM_PRESS)
                        .pattern("SSS")
                        .pattern("IRI")
                        .pattern("SSS")
                        .define('S', Items.CRIMSON_SLAB)
                        .define('I', Items.IRON_NUGGET)
                        .define('R', Items.STRING)
                        .unlockedBy(getHasName(Items.CRIMSON_SLAB), has(Items.CRIMSON_SLAB))
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .unlockedBy(getHasName(Items.STRING), has(Items.STRING))
                        .group("mycelium-leather")
                        .save(output, "press_from_crimson_slabs");

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.HERBARIUM_PRESS)
                        .pattern("SSS")
                        .pattern("IRI")
                        .pattern("SSS")
                        .define('S', Items.DARK_OAK_SLAB)
                        .define('I', Items.IRON_NUGGET)
                        .define('R', Items.STRING)
                        .unlockedBy(getHasName(Items.DARK_OAK_SLAB), has(Items.DARK_OAK_SLAB))
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .unlockedBy(getHasName(Items.STRING), has(Items.STRING))
                        .group("mycelium-leather")
                        .save(output, "press_from_dark_oak_slabs");

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.HERBARIUM_PRESS)
                        .pattern("SSS")
                        .pattern("IRI")
                        .pattern("SSS")
                        .define('S', Items.JUNGLE_SLAB)
                        .define('I', Items.IRON_NUGGET)
                        .define('R', Items.STRING)
                        .unlockedBy(getHasName(Items.JUNGLE_SLAB), has(Items.JUNGLE_SLAB))
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .unlockedBy(getHasName(Items.STRING), has(Items.STRING))
                        .group("mycelium-leather")
                        .save(output, "press_from_jungle_slabs");

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.HERBARIUM_PRESS)
                        .pattern("SSS")
                        .pattern("IRI")
                        .pattern("SSS")
                        .define('S', Items.SPRUCE_SLAB)
                        .define('I', Items.IRON_NUGGET)
                        .define('R', Items.STRING)
                        .unlockedBy(getHasName(Items.SPRUCE_SLAB), has(Items.SPRUCE_SLAB))
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .unlockedBy(getHasName(Items.STRING), has(Items.STRING))
                        .group("mycelium-leather")
                        .save(output, "press_from_spruce_slabs");

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.HERBARIUM_PRESS)
                        .pattern("SSS")
                        .pattern("IRI")
                        .pattern("SSS")
                        .define('S', Items.MANGROVE_SLAB)
                        .define('I', Items.IRON_NUGGET)
                        .define('R', Items.STRING)
                        .unlockedBy(getHasName(Items.MANGROVE_SLAB), has(Items.MANGROVE_SLAB))
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .unlockedBy(getHasName(Items.STRING), has(Items.STRING))
                        .group("mycelium-leather")
                        .save(output, "press_from_mangrove_slabs");

                shaped(RecipeCategory.TOOLS, ModItems.ITEM_SPADE)
                        .pattern("   ")
                        .pattern(" S ")
                        .pattern("  I")
                        .define('I', Items.IRON_INGOT)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                        .group("spade")
                        .save(output, "spade_from_stick_and_iron_ingot_legacy");

                shaped(RecipeCategory.TOOLS, ModItems.ITEM_SPADE)
                        .pattern("  I")
                        .pattern(" S ")
                        .pattern("   ")
                        .define('I', Items.IRON_INGOT)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                        .group("spade")
                        .save(output, "spade_from_stick_and_iron_ingot");

                shaped(RecipeCategory.COMBAT, Items.LEATHER_BOOTS)
                        .pattern("   ")
                        .pattern("L L")
                        .pattern("L L")
                        .define('L', ModItems.MYCELIUM_LEATHER)
                        .unlockedBy(getHasName(ModItems.MYCELIUM_LEATHER), has(ModItems.MYCELIUM_LEATHER))
                        .save(output, "leather_boots_from_mycelium_leather");

                shaped(RecipeCategory.COMBAT, Items.LEATHER_HELMET)
                        .pattern("   ")
                        .pattern("LLL")
                        .pattern("L L")
                        .define('L', ModItems.MYCELIUM_LEATHER)
                        .unlockedBy(getHasName(ModItems.MYCELIUM_LEATHER), has(ModItems.MYCELIUM_LEATHER))
                        .save(output, "leather_helmet_from_mycelium_leather");

                shaped(RecipeCategory.COMBAT, Items.LEATHER_LEGGINGS)
                        .pattern("LLL")
                        .pattern("L L")
                        .pattern("L L")
                        .define('L', ModItems.MYCELIUM_LEATHER)
                        .unlockedBy(getHasName(ModItems.MYCELIUM_LEATHER), has(ModItems.MYCELIUM_LEATHER))
                        .save(output, "leather_pants_from_mycelium_leather");

                shaped(RecipeCategory.COMBAT, Items.LEATHER_CHESTPLATE)
                        .pattern("L L")
                        .pattern("LLL")
                        .pattern("LLL")
                        .define('L', ModItems.MYCELIUM_LEATHER)
                        .unlockedBy(getHasName(ModItems.MYCELIUM_LEATHER), has(ModItems.MYCELIUM_LEATHER))
                        .save(output, "leather_tunic_from_mycelium_leather");

                shaped(RecipeCategory.TRANSPORTATION, Items.LEATHER_HORSE_ARMOR)
                        .pattern("L L")
                        .pattern("LLL")
                        .pattern("L L")
                        .define('L', ModItems.MYCELIUM_LEATHER)
                        .unlockedBy(getHasName(ModItems.MYCELIUM_LEATHER), has(ModItems.MYCELIUM_LEATHER))
                        .save(output, "leather_horse_armor_from_mycelium_leather");

                shaped(RecipeCategory.TRANSPORTATION, Items.SADDLE)
                        .pattern("   ")
                        .pattern(" L ")
                        .pattern("LIL")
                        .define('L', ModItems.MYCELIUM_LEATHER)
                        .define('I', Items.IRON_INGOT)
                        .unlockedBy(getHasName(ModItems.MYCELIUM_LEATHER), has(ModItems.MYCELIUM_LEATHER))
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .save(output, "saddle_from_mycelium_leather_and_iron_ingot");
            }
        };
    }

    @Override
    public String getName() {
        return "Mycelium Leather Mod";
    }
}

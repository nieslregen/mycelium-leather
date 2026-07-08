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

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.HERBARIUM_PRESS)
                        .pattern("SSS")
                        .pattern("IRI")
                        .pattern("SSS")
                        .define('S', Items.PALE_OAK_SLAB)
                        .define('I', Items.IRON_NUGGET)
                        .define('R', Items.STRING)
                        .unlockedBy(getHasName(Items.PALE_OAK_SLAB), has(Items.PALE_OAK_SLAB))
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .unlockedBy(getHasName(Items.STRING), has(Items.STRING))
                        .group("mycelium-leather")
                        .save(output, "press_from_pale_oak_slabs");


                shaped(RecipeCategory.TOOLS, ModItems.SPADE)
                        .pattern("   ")
                        .pattern(" S ")
                        .pattern("  I")
                        .define('I', Items.IRON_INGOT)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                        .group("spade")
                        .save(output, "spade_from_stick_and_iron_ingot_legacy");

                shaped(RecipeCategory.TOOLS, ModItems.SPADE)
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

                shaped(RecipeCategory.TRANSPORTATION, Items.HARNESS.black())
                        .pattern("LLL")
                        .pattern("GWG")
                        .pattern("   ")
                        .define('L', ModItems.MYCELIUM_LEATHER)
                        .define('W', Items.WOOL.black())
                        .define('G', Items.GLASS)
                        .unlockedBy(getHasName(ModItems.MYCELIUM_LEATHER), has(ModItems.MYCELIUM_LEATHER))
                        .unlockedBy(getHasName(Items.WOOL.black()), has(Items.WOOL.black()))
                        .unlockedBy(getHasName(Items.GLASS), has(Items.GLASS))
                        .save(output, "harness_from_mycelium_leather_and_black_wool");

                shaped(RecipeCategory.TRANSPORTATION, Items.HARNESS.blue())
                        .pattern("LLL")
                        .pattern("GWG")
                        .pattern("   ")
                        .define('L', ModItems.MYCELIUM_LEATHER)
                        .define('W', Items.WOOL.blue())
                        .define('G', Items.GLASS)
                        .unlockedBy(getHasName(ModItems.MYCELIUM_LEATHER), has(ModItems.MYCELIUM_LEATHER))
                        .unlockedBy(getHasName(Items.WOOL.blue()), has(Items.WOOL.blue()))
                        .unlockedBy(getHasName(Items.GLASS), has(Items.GLASS))
                        .save(output, "harness_from_mycelium_leather_and_blue_wool");

                shaped(RecipeCategory.TRANSPORTATION, Items.HARNESS.brown())
                        .pattern("LLL")
                        .pattern("GWG")
                        .pattern("   ")
                        .define('L', ModItems.MYCELIUM_LEATHER)
                        .define('W', Items.HARNESS.brown())
                        .define('G', Items.GLASS)
                        .unlockedBy(getHasName(ModItems.MYCELIUM_LEATHER), has(ModItems.MYCELIUM_LEATHER))
                        .unlockedBy(getHasName(Items.HARNESS.brown()), has(Items.HARNESS.brown()))
                        .unlockedBy(getHasName(Items.GLASS), has(Items.GLASS))
                        .save(output, "harness_from_mycelium_leather_and_brown_wool");

                shaped(RecipeCategory.TRANSPORTATION, Items.HARNESS.cyan())
                        .pattern("LLL")
                        .pattern("GWG")
                        .pattern("   ")
                        .define('L', ModItems.MYCELIUM_LEATHER)
                        .define('W', Items.WOOL.cyan())
                        .define('G', Items.GLASS)
                        .unlockedBy(getHasName(ModItems.MYCELIUM_LEATHER), has(ModItems.MYCELIUM_LEATHER))
                        .unlockedBy(getHasName(Items.WOOL.cyan()), has(Items.WOOL.cyan()))
                        .unlockedBy(getHasName(Items.GLASS), has(Items.GLASS))
                        .save(output, "harness_from_mycelium_leather_and_cyan_wool");

                shaped(RecipeCategory.TRANSPORTATION, Items.HARNESS.green())
                        .pattern("LLL")
                        .pattern("GWG")
                        .pattern("   ")
                        .define('L', ModItems.MYCELIUM_LEATHER)
                        .define('W', Items.WOOL.green())
                        .define('G', Items.GLASS)
                        .unlockedBy(getHasName(ModItems.MYCELIUM_LEATHER), has(ModItems.MYCELIUM_LEATHER))
                        .unlockedBy(getHasName(Items.WOOL.green()), has(Items.WOOL.green()))
                        .unlockedBy(getHasName(Items.GLASS), has(Items.GLASS))
                        .save(output, "harness_from_mycelium_leather_and_green_wool");

                shaped(RecipeCategory.TRANSPORTATION, Items.HARNESS.gray())
                        .pattern("LLL")
                        .pattern("GWG")
                        .pattern("   ")
                        .define('L', ModItems.MYCELIUM_LEATHER)
                        .define('W', Items.WOOL.gray())
                        .define('G', Items.GLASS)
                        .unlockedBy(getHasName(ModItems.MYCELIUM_LEATHER), has(ModItems.MYCELIUM_LEATHER))
                        .unlockedBy(getHasName(Items.WOOL.gray()), has(Items.WOOL.gray()))
                        .unlockedBy(getHasName(Items.GLASS), has(Items.GLASS))
                        .save(output, "harness_from_mycelium_leather_and_gray_wool");

                shaped(RecipeCategory.TRANSPORTATION, Items.HARNESS.lightBlue())
                        .pattern("LLL")
                        .pattern("GWG")
                        .pattern("   ")
                        .define('L', ModItems.MYCELIUM_LEATHER)
                        .define('W', Items.WOOL.lightBlue())
                        .define('G', Items.GLASS)
                        .unlockedBy(getHasName(ModItems.MYCELIUM_LEATHER), has(ModItems.MYCELIUM_LEATHER))
                        .unlockedBy(getHasName(Items.WOOL.lightBlue()), has(Items.WOOL.lightBlue()))
                        .unlockedBy(getHasName(Items.GLASS), has(Items.GLASS))
                        .save(output, "harness_from_mycelium_leather_and_light_blue_wool");

                shaped(RecipeCategory.TRANSPORTATION, Items.HARNESS.lightGray())
                        .pattern("LLL")
                        .pattern("GWG")
                        .pattern("   ")
                        .define('L', ModItems.MYCELIUM_LEATHER)
                        .define('W', Items.WOOL.lightGray())
                        .define('G', Items.GLASS)
                        .unlockedBy(getHasName(ModItems.MYCELIUM_LEATHER), has(ModItems.MYCELIUM_LEATHER))
                        .unlockedBy(getHasName(Items.WOOL.lightGray()), has(Items.WOOL.lightGray()))
                        .unlockedBy(getHasName(Items.GLASS), has(Items.GLASS))
                        .save(output, "harness_from_mycelium_leather_and_light_gray_wool");

                shaped(RecipeCategory.TRANSPORTATION, Items.HARNESS.lime())
                        .pattern("LLL")
                        .pattern("GWG")
                        .pattern("   ")
                        .define('L', ModItems.MYCELIUM_LEATHER)
                        .define('W', Items.WOOL.lime())
                        .define('G', Items.GLASS)
                        .unlockedBy(getHasName(ModItems.MYCELIUM_LEATHER), has(ModItems.MYCELIUM_LEATHER))
                        .unlockedBy(getHasName(Items.WOOL.lime()), has(Items.WOOL.lime()))
                        .unlockedBy(getHasName(Items.GLASS), has(Items.GLASS))
                        .save(output, "harness_from_mycelium_leather_and_lime_wool");

                shaped(RecipeCategory.TRANSPORTATION, Items.HARNESS.magenta())
                        .pattern("LLL")
                        .pattern("GWG")
                        .pattern("   ")
                        .define('L', ModItems.MYCELIUM_LEATHER)
                        .define('W', Items.WOOL.magenta())
                        .define('G', Items.GLASS)
                        .unlockedBy(getHasName(ModItems.MYCELIUM_LEATHER), has(ModItems.MYCELIUM_LEATHER))
                        .unlockedBy(getHasName(Items.WOOL.magenta()), has(Items.WOOL.magenta()))
                        .unlockedBy(getHasName(Items.GLASS), has(Items.GLASS))
                        .save(output, "harness_from_mycelium_leather_and_magenta_wool");

                shaped(RecipeCategory.TRANSPORTATION, Items.HARNESS.orange())
                        .pattern("LLL")
                        .pattern("GWG")
                        .pattern("   ")
                        .define('L', ModItems.MYCELIUM_LEATHER)
                        .define('W', Items.WOOL.orange())
                        .define('G', Items.GLASS)
                        .unlockedBy(getHasName(ModItems.MYCELIUM_LEATHER), has(ModItems.MYCELIUM_LEATHER))
                        .unlockedBy(getHasName(Items.WOOL.orange()), has(Items.WOOL.orange()))
                        .unlockedBy(getHasName(Items.GLASS), has(Items.GLASS))
                        .save(output, "harness_from_mycelium_leather_and_orange_wool");

                shaped(RecipeCategory.TRANSPORTATION, Items.HARNESS.pink())
                        .pattern("LLL")
                        .pattern("GWG")
                        .pattern("   ")
                        .define('L', ModItems.MYCELIUM_LEATHER)
                        .define('W', Items.WOOL.pink())
                        .define('G', Items.GLASS)
                        .unlockedBy(getHasName(ModItems.MYCELIUM_LEATHER), has(ModItems.MYCELIUM_LEATHER))
                        .unlockedBy(getHasName(Items.WOOL.pink()), has(Items.WOOL.pink()))
                        .unlockedBy(getHasName(Items.GLASS), has(Items.GLASS))
                        .save(output, "harness_from_mycelium_leather_and_pink_wool");

                shaped(RecipeCategory.TRANSPORTATION, Items.HARNESS.purple())
                        .pattern("LLL")
                        .pattern("GWG")
                        .pattern("   ")
                        .define('L', ModItems.MYCELIUM_LEATHER)
                        .define('W', Items.WOOL.purple())
                        .define('G', Items.GLASS)
                        .unlockedBy(getHasName(ModItems.MYCELIUM_LEATHER), has(ModItems.MYCELIUM_LEATHER))
                        .unlockedBy(getHasName(Items.WOOL.purple()), has(Items.WOOL.purple()))
                        .unlockedBy(getHasName(Items.GLASS), has(Items.GLASS))
                        .save(output, "harness_from_mycelium_leather_and_purple_wool");

                shaped(RecipeCategory.TRANSPORTATION, Items.HARNESS.red())
                        .pattern("LLL")
                        .pattern("GWG")
                        .pattern("   ")
                        .define('L', ModItems.MYCELIUM_LEATHER)
                        .define('W', Items.WOOL.red())
                        .define('G', Items.GLASS)
                        .unlockedBy(getHasName(ModItems.MYCELIUM_LEATHER), has(ModItems.MYCELIUM_LEATHER))
                        .unlockedBy(getHasName(Items.WOOL.red()), has(Items.WOOL.red()))
                        .unlockedBy(getHasName(Items.GLASS), has(Items.GLASS))
                        .save(output, "harness_from_mycelium_leather_and_red_wool");

                shaped(RecipeCategory.TRANSPORTATION, Items.HARNESS.white())
                        .pattern("LLL")
                        .pattern("GWG")
                        .pattern("   ")
                        .define('L', ModItems.MYCELIUM_LEATHER)
                        .define('W', Items.WOOL.white())
                        .define('G', Items.GLASS)
                        .unlockedBy(getHasName(ModItems.MYCELIUM_LEATHER), has(ModItems.MYCELIUM_LEATHER))
                        .unlockedBy(getHasName(Items.WOOL.white()), has(Items.WOOL.white()))
                        .unlockedBy(getHasName(Items.GLASS), has(Items.GLASS))
                        .save(output, "harness_from_mycelium_leather_and_white_wool");

                shaped(RecipeCategory.TRANSPORTATION, Items.HARNESS.yellow())
                        .pattern("LLL")
                        .pattern("GWG")
                        .pattern("   ")
                        .define('L', ModItems.MYCELIUM_LEATHER)
                        .define('W', Items.WOOL.yellow())
                        .define('G', Items.GLASS)
                        .unlockedBy(getHasName(ModItems.MYCELIUM_LEATHER), has(ModItems.MYCELIUM_LEATHER))
                        .unlockedBy(getHasName(Items.WOOL.yellow()), has(Items.WOOL.yellow()))
                        .unlockedBy(getHasName(Items.GLASS), has(Items.GLASS))
                        .save(output, "harness_from_mycelium_leather_and_yellow_wool");

                shapeless(RecipeCategory.MISC, Items.DYE.black())
                        .requires(Items.BONE_MEAL)
                        .requires(ModItems.SOOT_INK)
                        .unlockedBy(getHasName(ModItems.SOOT_INK), has(ModItems.SOOT_INK))
                        .unlockedBy(getHasName(Items.BONE_MEAL), has(Items.BONE_MEAL))
                        .save(output, "black_dye_from_soot_ink");

                shaped(RecipeCategory.MISC, Items.WRITABLE_BOOK)
                        .pattern("   ")
                        .pattern("BI ")
                        .pattern(" F ")
                        .define('B', Items.BOOK)
                        .define('I', ModItems.SOOT_INK)
                        .define('F', Items.FEATHER)
                        .unlockedBy(getHasName(Items.BOOK), has(Items.BOOK))
                        .unlockedBy(getHasName(ModItems.SOOT_INK), has(ModItems.SOOT_INK))
                        .unlockedBy(getHasName(Items.FEATHER), has(Items.FEATHER))
                        .save(output, "writable_book_from_soot_ink");

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TINY_CAULDRON)
                        .pattern("I I")
                        .pattern("III")
                        .pattern("SSS")
                        .define('I', Items.IRON_INGOT)
                        .define('S', Items.OAK_SLAB)
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .unlockedBy(getHasName(Items.OAK_SLAB), has(Items.OAK_SLAB))
                        .group("tiny_cauldron")
                        .save(output, "tiny_cauldron_from_oak_slab");


                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TINY_CAULDRON)
                        .pattern("I I")
                        .pattern("III")
                        .pattern("SSS")
                        .define('I', Items.IRON_INGOT)
                        .define('S', Items.ACACIA_SLAB)
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .unlockedBy(getHasName(Items.ACACIA_SLAB), has(Items.ACACIA_SLAB))
                        .group("tiny_cauldron")
                        .save(output, "tiny_cauldron_from_acacia_slab");

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TINY_CAULDRON)
                        .pattern("I I")
                        .pattern("III")
                        .pattern("SSS")
                        .define('I', Items.IRON_INGOT)
                        .define('S', Items.BIRCH_SLAB)
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .unlockedBy(getHasName(Items.BIRCH_SLAB), has(Items.BIRCH_SLAB))
                        .group("tiny_cauldron")
                        .save(output, "tiny_cauldron_from_birch_slab");

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TINY_CAULDRON)
                        .pattern("I I")
                        .pattern("III")
                        .pattern("SSS")
                        .define('I', Items.IRON_INGOT)
                        .define('S', Items.CHERRY_SLAB)
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .unlockedBy(getHasName(Items.CHERRY_SLAB), has(Items.CHERRY_SLAB))
                        .group("tiny_cauldron")
                        .save(output, "tiny_cauldron_from_cherry_slab");


                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TINY_CAULDRON)
                        .pattern("I I")
                        .pattern("III")
                        .pattern("SSS")
                        .define('I', Items.IRON_INGOT)
                        .define('S', Items.CRIMSON_SLAB)
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .unlockedBy(getHasName(Items.CRIMSON_SLAB), has(Items.CRIMSON_SLAB))
                        .group("tiny_cauldron")
                        .save(output, "tiny_cauldron_from_crimson_slab");

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TINY_CAULDRON)
                        .pattern("I I")
                        .pattern("III")
                        .pattern("SSS")
                        .define('I', Items.IRON_INGOT)
                        .define('S', Items.DARK_OAK_SLAB)
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .unlockedBy(getHasName(Items.DARK_OAK_SLAB), has(Items.DARK_OAK_SLAB))
                        .group("tiny_cauldron")
                        .save(output, "tiny_cauldron_from_dark_oak_slab");


                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TINY_CAULDRON)
                        .pattern("I I")
                        .pattern("III")
                        .pattern("SSS")
                        .define('I', Items.IRON_INGOT)
                        .define('S', Items.JUNGLE_SLAB)
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .unlockedBy(getHasName(Items.JUNGLE_SLAB), has(Items.JUNGLE_SLAB))
                        .group("tiny_cauldron")
                        .save(output, "tiny_cauldron_from_jungle_slab");


                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TINY_CAULDRON)
                        .pattern("I I")
                        .pattern("III")
                        .pattern("SSS")
                        .define('I', Items.IRON_INGOT)
                        .define('S', Items.SPRUCE_SLAB)
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .unlockedBy(getHasName(Items.SPRUCE_SLAB), has(Items.SPRUCE_SLAB))
                        .group("tiny_cauldron")
                        .save(output, "tiny_cauldron_from_spruce_slab");


                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TINY_CAULDRON)
                        .pattern("I I")
                        .pattern("III")
                        .pattern("SSS")
                        .define('I', Items.IRON_INGOT)
                        .define('S', Items.MANGROVE_SLAB)
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .unlockedBy(getHasName(Items.MANGROVE_SLAB), has(Items.MANGROVE_SLAB))
                        .group("tiny_cauldron")
                        .save(output, "tiny_cauldron_from_mangrove_slab");

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TINY_CAULDRON)
                        .pattern("I I")
                        .pattern("III")
                        .pattern("SSS")
                        .define('I', Items.IRON_INGOT)
                        .define('S', Items.PALE_OAK_SLAB)
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .unlockedBy(getHasName(Items.PALE_OAK_SLAB), has(Items.PALE_OAK_SLAB))
                        .group("tiny_cauldron")
                        .save(output, "tiny_cauldron_from_pale_oak_slab");


                //

                shaped(RecipeCategory.MISC, ModBlocks.CHARCOAL_PILE)
                        .pattern("DGD")
                        .pattern("SWS")
                        .pattern("WFW")
                        .define('D', Items.DIRT)
                        .define('S', Items.STICK)
                        .define('W', Items.OAK_LOG)
                        .define('G', ModItems.GRASS_PATCH)
                        .define('F', Items.CAMPFIRE)
                        .unlockedBy(getHasName(ModItems.GRASS_PATCH), has(ModItems.GRASS_PATCH))
                        .unlockedBy(getHasName(Items.DIRT), has(Items.DIRT))
                        .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                        .unlockedBy(getHasName(Items.OAK_LOG), has(Items.OAK_LOG))
                        .unlockedBy(getHasName(Items.CAMPFIRE), has(Items.CAMPFIRE))
                        .group("charcoal_pile")
                        .save(output, "charcoal_pile_from_oak_log");

                shaped(RecipeCategory.MISC, ModBlocks.CHARCOAL_PILE)
                        .pattern("DGD")
                        .pattern("SWS")
                        .pattern("WFW")
                        .define('D', Items.DIRT)
                        .define('S', Items.STICK)
                        .define('W', Items.ACACIA_LOG)
                        .define('G', ModItems.GRASS_PATCH)
                        .define('F', Items.CAMPFIRE)
                        .unlockedBy(getHasName(ModItems.GRASS_PATCH), has(ModItems.GRASS_PATCH))
                        .unlockedBy(getHasName(Items.DIRT), has(Items.DIRT))
                        .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                        .unlockedBy(getHasName(Items.ACACIA_LOG), has(Items.ACACIA_LOG))
                        .unlockedBy(getHasName(Items.CAMPFIRE), has(Items.CAMPFIRE))
                        .group("charcoal_pile")
                        .save(output, "charcoal_pile_from_acacia_log");


                shaped(RecipeCategory.MISC, ModBlocks.CHARCOAL_PILE)
                        .pattern("DGD")
                        .pattern("SWS")
                        .pattern("WFW")
                        .define('D', Items.DIRT)
                        .define('S', Items.STICK)
                        .define('W', Items.BIRCH_LOG)
                        .define('G', ModItems.GRASS_PATCH)
                        .define('F', Items.CAMPFIRE)
                        .unlockedBy(getHasName(ModItems.GRASS_PATCH), has(ModItems.GRASS_PATCH))
                        .unlockedBy(getHasName(Items.DIRT), has(Items.DIRT))
                        .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                        .unlockedBy(getHasName(Items.BIRCH_LOG), has(Items.BIRCH_LOG))
                        .unlockedBy(getHasName(Items.CAMPFIRE), has(Items.CAMPFIRE))
                        .group("charcoal_pile")
                        .save(output, "charcoal_pile_from_birch_log");

                shaped(RecipeCategory.MISC, ModBlocks.CHARCOAL_PILE)
                        .pattern("DGD")
                        .pattern("SWS")
                        .pattern("WFW")
                        .define('D', Items.DIRT)
                        .define('S', Items.STICK)
                        .define('W', Items.CHERRY_LOG)
                        .define('G', ModItems.GRASS_PATCH)
                        .define('F', Items.CAMPFIRE)
                        .unlockedBy(getHasName(ModItems.GRASS_PATCH), has(ModItems.GRASS_PATCH))
                        .unlockedBy(getHasName(Items.DIRT), has(Items.DIRT))
                        .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                        .unlockedBy(getHasName(Items.CHERRY_LOG), has(Items.CHERRY_LOG))
                        .unlockedBy(getHasName(Items.CAMPFIRE), has(Items.CAMPFIRE))
                        .group("charcoal_pile")
                        .save(output, "charcoal_pile_from_cherry_log");


                shaped(RecipeCategory.MISC, ModBlocks.CHARCOAL_PILE)
                        .pattern("DGD")
                        .pattern("SWS")
                        .pattern("WFW")
                        .define('D', Items.DIRT)
                        .define('S', Items.STICK)
                        .define('W', Items.DARK_OAK_LOG)
                        .define('G', ModItems.GRASS_PATCH)
                        .define('F', Items.CAMPFIRE)
                        .unlockedBy(getHasName(ModItems.GRASS_PATCH), has(ModItems.GRASS_PATCH))
                        .unlockedBy(getHasName(Items.DIRT), has(Items.DIRT))
                        .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                        .unlockedBy(getHasName(Items.DARK_OAK_LOG), has(Items.DARK_OAK_LOG))
                        .unlockedBy(getHasName(Items.CAMPFIRE), has(Items.CAMPFIRE))
                        .group("charcoal_pile")
                        .save(output, "charcoal_pile_from_dark_oak_log");


                shaped(RecipeCategory.MISC, ModBlocks.CHARCOAL_PILE)
                        .pattern("DGD")
                        .pattern("SWS")
                        .pattern("WFW")
                        .define('D', Items.DIRT)
                        .define('S', Items.STICK)
                        .define('W', Items.JUNGLE_LOG)
                        .define('G', ModItems.GRASS_PATCH)
                        .define('F', Items.CAMPFIRE)
                        .unlockedBy(getHasName(ModItems.GRASS_PATCH), has(ModItems.GRASS_PATCH))
                        .unlockedBy(getHasName(Items.DIRT), has(Items.DIRT))
                        .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                        .unlockedBy(getHasName(Items.JUNGLE_LOG), has(Items.JUNGLE_LOG))
                        .unlockedBy(getHasName(Items.CAMPFIRE), has(Items.CAMPFIRE))
                        .group("charcoal_pile")
                        .save(output, "charcoal_pile_from_jungle_log");

                shaped(RecipeCategory.MISC, ModBlocks.CHARCOAL_PILE)
                        .pattern("DGD")
                        .pattern("SWS")
                        .pattern("WFW")
                        .define('D', Items.DIRT)
                        .define('S', Items.STICK)
                        .define('W', Items.SPRUCE_LOG)
                        .define('G', ModItems.GRASS_PATCH)
                        .define('F', Items.CAMPFIRE)
                        .unlockedBy(getHasName(ModItems.GRASS_PATCH), has(ModItems.GRASS_PATCH))
                        .unlockedBy(getHasName(Items.DIRT), has(Items.DIRT))
                        .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                        .unlockedBy(getHasName(Items.SPRUCE_LOG), has(Items.SPRUCE_LOG))
                        .unlockedBy(getHasName(Items.CAMPFIRE), has(Items.CAMPFIRE))
                        .group("charcoal_pile")
                        .save(output, "charcoal_pile_from_spruce_log");

                shaped(RecipeCategory.MISC, ModBlocks.CHARCOAL_PILE)
                        .pattern("DGD")
                        .pattern("SWS")
                        .pattern("WFW")
                        .define('D', Items.DIRT)
                        .define('S', Items.STICK)
                        .define('W', Items.MANGROVE_LOG)
                        .define('G', ModItems.GRASS_PATCH)
                        .define('F', Items.CAMPFIRE)
                        .unlockedBy(getHasName(ModItems.GRASS_PATCH), has(ModItems.GRASS_PATCH))
                        .unlockedBy(getHasName(Items.DIRT), has(Items.DIRT))
                        .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                        .unlockedBy(getHasName(Items.MANGROVE_LOG), has(Items.MANGROVE_LOG))
                        .unlockedBy(getHasName(Items.CAMPFIRE), has(Items.CAMPFIRE))
                        .group("charcoal_pile")
                        .save(output, "charcoal_pile_from_mangrove_log");

                shaped(RecipeCategory.MISC, ModBlocks.CHARCOAL_PILE)
                        .pattern("DGD")
                        .pattern("SWS")
                        .pattern("WFW")
                        .define('D', Items.DIRT)
                        .define('S', Items.STICK)
                        .define('W', Items.PALE_OAK_LOG)
                        .define('G', ModItems.GRASS_PATCH)
                        .define('F', Items.CAMPFIRE)
                        .unlockedBy(getHasName(ModItems.GRASS_PATCH), has(ModItems.GRASS_PATCH))
                        .unlockedBy(getHasName(Items.DIRT), has(Items.DIRT))
                        .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                        .unlockedBy(getHasName(Items.PALE_OAK_LOG), has(Items.PALE_OAK_LOG))
                        .unlockedBy(getHasName(Items.CAMPFIRE), has(Items.CAMPFIRE))
                        .group("charcoal_pile")
                        .save(output, "charcoal_pile_from_pale_oak_log");
            }
        };
    }

    @Override
    public String getName() {
        return "Mycelium Leather Mod";
    }
}

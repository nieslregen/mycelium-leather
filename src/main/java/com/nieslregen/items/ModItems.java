package com.nieslregen.items;


import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.effect.ModEffects;
import com.nieslregen.items.customitems.*;
import com.nieslregen.mob.ModEntityTypes;
import com.nieslregen.mob.myceliumchicken.SuspiciousEggItem;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.function.Function;

public class ModItems {

    public static final float BASE_DMG_DAGGER = 2;
    public static final float BASE_AS_DAGGER = -1.2f;

    public static final Item MYCELIUM_LEATHER = registerItem("mycelium-leather", Item::new);
    public static final Item MYCELIUM_PATCH = registerItem("mycelium-patch", properties -> new AbstractPatchItem(properties.stacksTo(16), Blocks.MYCELIUM));
    public static final Item MYCELIUM_PATCH_DRIED = registerItem("mycelium-patch-dried", Item::new);
    public static final Item GRASS_PATCH = registerItem("grass-patch", properties -> new AbstractPatchItem(properties.stacksTo(16), Blocks.GRASS_BLOCK));
    public static final Item ITEM_SPADE = registerItem("spade",  properties -> new SpadeItem(properties.durability(250)));

    public static final Item SOOT = registerItem("soot", Item::new);
    public static final Item SOOT_INK = registerItem("soot-ink", Item::new);

    public static final Item SUSPICIOUS_FLASK = registerItem("suspicious-flask", Item::new);
    public static final Item ARROW_OF_ILLNESS = registerItem("arrow-of-illness", properties -> new AbstractMobEffectArrow(properties, List.of(ModEffects.ILLNESS)));

    public static final Item CRAWLER_SPAWN_EGG = registerItem("crawler_spawn_egg", properties -> new SpawnEggItem(properties.spawnEgg(ModEntityTypes.CRAWLER)));
    public static final Item MYCELIUM_SQUIRREL_SPAWN_EGG = registerItem("mycelium_squirrel_spawn_egg", properties -> new SpawnEggItem(properties.spawnEgg(ModEntityTypes.SQUIRREL)));
    public static final Item MYCELIUM_CHICKEN_SPAWN_EGG = registerItem("mycelium_chicken_spawn_egg", properties -> new SpawnEggItem(properties.spawnEgg(ModEntityTypes.MYCELIUM_CHICKEN)));
    public static final Item SUSPICIOUS_EGG = registerItem("suspicious_egg", SuspiciousEggItem::new);
    public static final Item MYCELIUM_CHICKEN_EGG = registerItem("mycelium_chicken_egg", Item::new);
    public static final Item MUSHROOM_PASTE = registerItem("mushroom_paste", properties -> new MushroomPaste(properties.food(new FoodProperties(1,0.1f, false)).stacksTo(4)));
    public static final Item SALT = registerItem("salt", Item::new);
    public static final Item FEATHER_VARIANT_WARM = registerItem("feather_warm", Item::new);
    public static final Item FEATHER_VARIANT_MUSHROOM = registerItem("feather_mushroom", Item::new);
    public static final Item FEATHER_VARIANT_COLD = registerItem("feather_cold", Item::new);
    public static final Item TRUFFLE = registerItem("truffle", properties -> new Item(properties.food(new FoodProperties(1,0.2f,true))));
    public static final Item TOASTED_BREAD = registerItem("toasted_bread", properties -> new Item(properties.food(new FoodProperties(6,0.6f,true))));
    public static final Item SCRAMBLED_EGGS = registerItem("scrambled_eggs", properties -> new Item(properties.food(new FoodProperties(6,0.6F,true))));
    public static final Item PAN_FRIED_POTATOES = registerItem("pan_fried_potatoes", properties -> new Item(properties.food(new FoodProperties(6,0.6F,true))));
    public static final Item COPPER_DAGGER = registerItem("copper_dagger", properties -> new Dagger(properties.sword(ToolMaterial.COPPER, BASE_DMG_DAGGER, BASE_AS_DAGGER)));
    public static final Item IRON_DAGGER = registerItem("iron_dagger", properties -> new Dagger(properties.sword(ToolMaterial.IRON, BASE_DMG_DAGGER, BASE_AS_DAGGER)));
    public static final Item GOLDEN_DAGGER_CLASSIC = registerItem("golden_dagger", properties -> new Dagger(properties.sword(ToolMaterial.GOLD, BASE_DMG_DAGGER, BASE_AS_DAGGER), Dagger.EffectType.TEMPERATE, true));
    public static final Item GOLDEN_DAGGER_WARM = registerItem("golden_dagger_warm", properties -> new Dagger(properties.sword(ToolMaterial.GOLD, BASE_DMG_DAGGER, BASE_AS_DAGGER), Dagger.EffectType.WARM, true));
    public static final Item GOLDEN_DAGGER_COLD = registerItem("golden_dagger_cold", properties -> new Dagger(properties.sword(ToolMaterial.GOLD, BASE_DMG_DAGGER, BASE_AS_DAGGER), Dagger.EffectType.COLD, true));
    public static final Item GOLDEN_DAGGER_MUSHROOM = registerItem("golden_dagger_mushroom", properties -> new Dagger(properties.sword(ToolMaterial.GOLD, BASE_DMG_DAGGER, BASE_AS_DAGGER), Dagger.EffectType.MUSHROOM, true));
    public static final Item DIAMOND_DAGGER = registerItem("diamond_dagger", properties -> new Dagger(properties.sword(ToolMaterial.DIAMOND, BASE_DMG_DAGGER, BASE_AS_DAGGER)));
    public static final Item WOODEN_SPADE = registerItem("wooden_spade", properties -> new SpadeItem(properties.durability(59)));


    private static Item registerItem(String name, Function<Item.Properties, Item> function) {
        return Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, name),
                function.apply(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, name)))));
    }

    public static ResourceKey<Item> getResourceKey(Item item) {
        return BuiltInRegistries.ITEM.getResourceKey(item).get();
    }

    public static void registerModItems() {
        MyceliumLeatherMod.LOGGER.info("Register Mod Items for: {}", MyceliumLeatherMod.MOD_ID);

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(output -> output.accept(SOOT));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(output -> output.accept(SOOT_INK));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(output -> output.accept(MYCELIUM_LEATHER));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(output -> output.accept(SUSPICIOUS_FLASK));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS).register(output -> output.accept(MYCELIUM_PATCH));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS).register(output -> output.accept(MYCELIUM_PATCH_DRIED));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS).register(output -> output.accept(GRASS_PATCH));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(output -> output.accept(ITEM_SPADE));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(output -> output.accept(WOODEN_SPADE));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(output -> output.accept(ARROW_OF_ILLNESS));

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(output -> output.accept(MUSHROOM_PASTE));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(output -> output.accept(SUSPICIOUS_EGG));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(output -> output.accept(TRUFFLE));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(output -> output.accept(TOASTED_BREAD));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(output -> output.accept(SCRAMBLED_EGGS));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(output -> output.accept(PAN_FRIED_POTATOES));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.SPAWN_EGGS).register(output -> output.accept(MYCELIUM_CHICKEN_SPAWN_EGG));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.SPAWN_EGGS).register(output -> output.accept(MYCELIUM_SQUIRREL_SPAWN_EGG));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.SPAWN_EGGS).register(output -> output.accept(CRAWLER_SPAWN_EGG));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(output -> output.accept(MYCELIUM_CHICKEN_EGG));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(output -> output.accept(FEATHER_VARIANT_COLD));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(output -> output.accept(FEATHER_VARIANT_WARM));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(output -> output.accept(FEATHER_VARIANT_MUSHROOM));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(output -> output.accept(COPPER_DAGGER));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(output -> output.accept(IRON_DAGGER));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(output -> output.accept(GOLDEN_DAGGER_CLASSIC));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(output -> output.accept(GOLDEN_DAGGER_COLD));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(output -> output.accept(GOLDEN_DAGGER_WARM));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(output -> output.accept(GOLDEN_DAGGER_MUSHROOM));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(output -> output.accept(DIAMOND_DAGGER));

    }


}

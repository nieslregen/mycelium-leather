package com.nieslregen.items;


import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.effect.ModEffects;
import com.nieslregen.items.customitems.AbstractMobEffectArrow;
import com.nieslregen.items.customitems.AbstractPatchItem;
import com.nieslregen.items.customitems.SpadeItem;
import com.nieslregen.mob.ModEntityTypes;
import com.nieslregen.mob.myceliumchicken.SuspiciousEggItem;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Function;

public class ModItems {

    public static final Item MYCELIUM_LEATHER = registerItem("mycelium-leather", Item::new);
    public static final Item MYCELIUM_PATCH = registerItem("mycelium-patch", properties -> new AbstractPatchItem(properties.stacksTo(16), Blocks.MYCELIUM));
    public static final Item MYCELIUM_PATCH_DRIED = registerItem("mycelium-patch-dried", Item::new);

    public static final Item GRASS_PATCH = registerItem("grass-patch", properties -> new AbstractPatchItem(properties.stacksTo(16), Blocks.GRASS_BLOCK));
    public static final Item ITEM_SPADE = registerItem("spade",  properties -> new SpadeItem(properties.durability(64)));

    public static final Item SOOT = registerItem("soot", Item::new);
    public static final Item SOOT_INK = registerItem("soot-ink", Item::new);

    public static final Item SUSPICIOUS_FLASK = registerItem("suspicious-flask", Item::new);

    public static final Item ARROW_OF_ILLNESS = registerItem("arrow-of-illness", properties -> new AbstractMobEffectArrow(properties, ModEffects.ILLNESS));

    public static final Item MYCELIUM_CHICKEN_SPAWN_EGG = registerItem(
            "mycelium_chicken_spawn_egg",
            properties -> new SpawnEggItem(properties.spawnEgg(ModEntityTypes.MYCELIUM_CHICKEN))
    );
    // Squirrel dig for truffles
    public static final Item SUSPICIOUS_EGG = registerItem("suspicious_egg", SuspiciousEggItem::new);
    public static final Item MYCELIUM_CHICKEN_EGG = registerItem("mycelium_chicken_egg", Item::new);
    public static final Item MUSHROOM_PASTE = registerItem("mushroom_paste", Item::new);
    public static final Item TRUFFLE = registerItem("truffle", Item::new);

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
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(output -> output.accept(ARROW_OF_ILLNESS));

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(output -> output.accept(MUSHROOM_PASTE));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS).register(output -> output.accept(SUSPICIOUS_EGG));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(output -> output.accept(TRUFFLE));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.SPAWN_EGGS).register(output -> output.accept(MYCELIUM_CHICKEN_SPAWN_EGG));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS).register(output -> output.accept(MYCELIUM_CHICKEN_EGG));

    }


}

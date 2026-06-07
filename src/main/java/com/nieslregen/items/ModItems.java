package com.nieslregen.items;


import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.items.customitems.GrassPatchItem;
import com.nieslregen.items.customitems.MyceliumPatchItem;
import com.nieslregen.items.customitems.SpadeItem;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class ModItems {

    public static final Item MYCELIUM_LEATHER = registerItem("mycelium-leather", Item::new);
    public static final Item MYCELIUM_PATCH = registerItem("mycelium-patch", properties -> new MyceliumPatchItem(properties.stacksTo(64)));
    public static final Item MYCELIUM_PATCH_DRIED = registerItem("mycelium-patch-dried", Item::new);

    public static final Item GRASS_PATCH = registerItem("grass-patch", properties -> new GrassPatchItem(properties.stacksTo(16)));
    public static final Item ITEM_SPADE = registerItem("spade",  properties -> new SpadeItem(properties.durability(64)));

    // ToDo: make soot ink form soot and honey within a cauldron. Get soot as by product form upgraded furnace
    // ne eher kochtopf mit ganz schamelen untersetzer: wenn nichts drunter -> tinte herstellen, sonst ofen drunter und als quasi herd verwenden
    public static final Item SOOT = registerItem("soot", Item::new);
    public static final Item SOOT_INK = registerItem("soot-ink", Item::new);

    //ToDo: upgraded book: can switch pages (in general make it easier to edit full pages), also herbarium maybe as an extension?

    //ToDo: deco extends from head or look at head an copy

    public static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, name), item);
    }

    private static Item registerItem(String name, Function<Item.Properties, Item> function) {
        return Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, name),
                function.apply(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, name)))));
    }

    public static void registerModItems() {
        MyceliumLeatherMod.LOGGER.info("Register Mod Items for: {}", MyceliumLeatherMod.MOD_ID);

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(output -> output.accept(SOOT));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(output -> output.accept(SOOT_INK));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(output -> output.accept(MYCELIUM_LEATHER));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS).register(output -> output.accept(MYCELIUM_PATCH));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS).register(output -> output.accept(MYCELIUM_PATCH_DRIED));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS).register(output -> output.accept(GRASS_PATCH));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES). register(output -> output.accept(ITEM_SPADE));

    }
}

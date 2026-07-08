package com.nieslregen.items;


import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.effect.ModEffects;
import com.nieslregen.items.customitems.AbstractMobEffectArrow;
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
    public static final Item SPADE = registerItem("spade", properties -> new SpadeItem(properties.durability(250)));

    public static final Item SOOT = registerItem("soot", Item::new);
    public static final Item SOOT_INK = registerItem("soot-ink", Item::new);

    public static final Item SUSPICIOUS_FLASK = registerItem("suspicious-flask", Item::new);

    public static final Item ARROW_OF_ILLNESS = registerItem("arrow-of-illness", properties -> new AbstractMobEffectArrow(properties, ModEffects.ILLNESS));

    private static Item registerItem(String name, Function<Item.Properties, Item> function) {
        return Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, name),
                function.apply(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, name)))));
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
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(output -> output.accept(SPADE));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(output -> output.accept(ARROW_OF_ILLNESS));

    }


}

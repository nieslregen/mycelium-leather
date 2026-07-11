package com.nieslregen.tab;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.block.ModBlocks;
import com.nieslregen.items.ModItems;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTabs {

    // Note: To create multiple tabs just define more CreativeModTabs
    public static final CreativeModeTab MODE_TAB = Registry.register(
            BuiltInRegistries.CREATIVE_MODE_TAB,
            Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, "mod_items"),
            FabricCreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.MYCELIUM_LEATHER))
                    .title(Component.translatable("creativemodtab.mycelium_leather.mod_items"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.ITEM_SPADE);
                        output.accept(ModItems.GRASS_PATCH);
                        output.accept(ModItems.MYCELIUM_PATCH);
                        output.accept(ModItems.MYCELIUM_PATCH_DRIED);
                        output.accept(ModItems.MYCELIUM_LEATHER);
                        output.accept(ModBlocks.HERBARIUM_PRESS);

                        output.accept(ModItems.SOOT);
                        output.accept(ModItems.SOOT_INK);
                        output.accept(ModItems.SUSPICIOUS_FLASK);
                        output.accept(ModItems.ARROW_OF_ILLNESS);
                        output.accept(ModBlocks.TINY_CAULDRON);
                        output.accept(ModBlocks.CHARCOAL_PILE);

                        output.accept(ModItems.TRUFFLE);
                        output.accept(ModItems.MUSHROOM_PASTE);
                        output.accept(ModItems.SUSPICIOUS_EGG);
                        output.accept(ModItems.MYCELIUM_CHICKEN_SPAWN_EGG);
                        output.accept(ModBlocks.MUSHROOM_STEM_HOLLOW);
                        output.accept(ModBlocks.SCRATCHED_MUSHROOM_STEM);
                    })
                    .build()
    );

    public static void registerModCreativeTabs() {
        MyceliumLeatherMod.LOGGER.info("Registering ModCreativeTabs " + MyceliumLeatherMod.MOD_ID);
    }
}

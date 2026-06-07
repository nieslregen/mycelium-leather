package com.nieslregen.block;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.block.custom.herbariumpress.HerbariumPressBlock;
import com.nieslregen.block.custom.charcoalpile.CharCoalPileBlock;
import com.nieslregen.block.custom.tinycauldron.TinyCauldronBlock;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class ModBlocks {

    public static final Block HERBARIUM_PRESS = registerBlock("herbarium_press_block", properties -> new HerbariumPressBlock(
            properties
                    .strength(1.5f)
                    .sound(SoundType.WOOD)
    ));

    public static final Block CHARCOAL_PILE = registerBlock("charcoal_pile_block", properties -> new CharCoalPileBlock(
            properties
                    .strength(1.5f)
                    .sound(SoundType.STONE)
    ));

    public static final Block TINY_CAULDRON = registerBlock("tiny_cauldron_block", properties -> new TinyCauldronBlock(
            properties
                    .strength(1.5f)
                    .sound(SoundType.IRON)
    ));


    private static Block registerBLock(String name, Block block) {
        return Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, name), block);
    }

    private static Block registerBlock(String name, Function<BlockBehaviour.Properties, Block> function) {
        Block toRegister = function.apply(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, name))));
        registerBlockItem(name, toRegister);
        return Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, name), toRegister);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(
                BuiltInRegistries.ITEM,
                Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, name),
                new BlockItem(
                        block,
                        new Item.Properties()
                                .useBlockDescriptionPrefix()
                                .setId(ResourceKey
                                        .create(
                                                Registries.ITEM,
                                                Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, name)))));
    }

    public static void registerModBlocks() {
        MyceliumLeatherMod.LOGGER.info("Register Mod Blocks for " + MyceliumLeatherMod.MOD_ID);

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(output -> output.accept(HERBARIUM_PRESS));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(output -> output.accept(CHARCOAL_PILE));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(output -> output.accept(TINY_CAULDRON));
    }
}

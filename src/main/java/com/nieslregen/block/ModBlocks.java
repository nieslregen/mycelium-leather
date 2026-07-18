package com.nieslregen.block;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.block.custom.FeastOfTheMushroomFields;
import com.nieslregen.block.custom.charcoalpile.CharCoalPileBlock;
import com.nieslregen.block.custom.cooking.fryingpan.FryingPanBlock;
import com.nieslregen.block.custom.herbariumpress.HerbariumPressBlock;
import com.nieslregen.block.custom.mushroomstem.MushroomStemHollowBlock;
import com.nieslregen.block.custom.mushroomstem.ScratchedMushroomStemBlock;
import com.nieslregen.block.custom.cooking.tinycauldron.TinyCauldronBlock;
import com.nieslregen.mob.myceliumchicken.MyceliumChickenNestBlock;
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
                    .instabreak()
    ));

    public static final Block TINY_CAULDRON = registerBlock("tiny_cauldron_block", properties -> new TinyCauldronBlock(
            properties
                    .strength(1.5f)
                    .sound(SoundType.IRON)
    ));

    public static final Block FRYING_PAN = registerBlock("frying_pan_block", properties -> new FryingPanBlock(
            properties
                    .strength(1.5f)
                    .sound(SoundType.IRON)
    ));

    public static final Block MYCELIUM_CHICKEN_NEST = registerBlock("mycelium_chicken_nest_block", MyceliumChickenNestBlock::new);

    public static final Block SCRATCHED_MUSHROOM_STEM = registerBlock("scratched_mushroom_stem", properties -> new ScratchedMushroomStemBlock(
            properties
                    .randomTicks()
    ));

    public static final Block MUSHROOM_STEM_HOLLOW = registerBlock("mushroom_stem_hollow", MushroomStemHollowBlock::new);
    public static final Block FEAST_OF_THE_MUSHROOM_FIELDS = registerBlock("feast_of_the_mushroom_fields", FeastOfTheMushroomFields::new);

    public static ResourceKey<Block> getResourceKey(Block block) {
        return BuiltInRegistries.BLOCK.getResourceKey(block).get();
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
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(output -> output.accept(FRYING_PAN));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(output -> output.accept(SCRATCHED_MUSHROOM_STEM));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(output -> output.accept(MUSHROOM_STEM_HOLLOW));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(output -> output.accept(MYCELIUM_CHICKEN_NEST));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(output -> output.accept(FEAST_OF_THE_MUSHROOM_FIELDS));
    }
}

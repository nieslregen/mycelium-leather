package com.nieslregen.tags;

import com.nieslregen.MyceliumLeatherMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {

        // example: Blocks that need specific tag/item to be mined

        private static TagKey<Block> createTag(String name) {
            return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> MYCELIUM_CHICKEN_FOOD = createTag("mycelium_food");
        public static final TagKey<Item> FEATHERS = createTag("feathers");

        private static TagKey<Item> createTag(String name) {
            return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, name));
        }
    }

    public static class Poi {
        public static final TagKey<PoiType> SQUIRREL_HOME_POI = createTag("hollow_poi");

        private static TagKey<PoiType> createTag(String name) {
            return TagKey.create(Registries.POINT_OF_INTEREST_TYPE, Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, name));
        }
    }
}

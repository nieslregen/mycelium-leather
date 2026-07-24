package com.nieslregen.mob;

import com.nieslregen.MyceliumLeatherMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;

public class ModPoiTypes {

    public static final TagKey<PoiType> SQUIRREL_HOME = create("squirrel_home");

    public ModPoiTypes() {}

    public static void init() {

    }

    private static TagKey<PoiType> create(final String name) {
        return TagKey.create(
                Registries.POINT_OF_INTEREST_TYPE,
                Identifier.fromNamespaceAndPath(
                        MyceliumLeatherMod.MOD_ID,
                        name
                )
        );
    }

}

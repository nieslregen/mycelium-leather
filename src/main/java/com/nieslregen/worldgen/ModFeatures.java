package com.nieslregen.worldgen;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.worldgen.hugemushroom.HugeBrownMushroomWithHollowFeature;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;

public class ModFeatures {

    public static void init(){

    }

    public static final Feature<HugeMushroomFeatureConfiguration> HUGE_BROWN_MUSHROOM_WITH_HOLLOW =
            Registry.register(
                    BuiltInRegistries.FEATURE,
                    Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID,
                            "huge_brown_mushroom_with_hollow"),
                    new HugeBrownMushroomWithHollowFeature(HugeMushroomFeatureConfiguration.CODEC)
            );
}

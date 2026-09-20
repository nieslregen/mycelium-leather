package com.nieslregen.datagen;

import com.mojang.serialization.MapCodec;
import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.worldgen.hugemushroom.HugeBlueMushroomWithHollowFeature;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.Feature;

import java.util.function.Function;

public class ModFeatureTypes {

    public static final MapCodec<HugeBlueMushroomWithHollowFeature> HUGE_BLUE_MUSHROOM_WITH_HOLLOW =
            register(
                    "huge_blue_mushroom_with_hollow",
                    HugeBlueMushroomWithHollowFeature.CODEC
            );

    private static <T extends Feature> MapCodec<T> register(
            String name,
            MapCodec<T> codec
    ) {
        return Registry.register(
                BuiltInRegistries.FEATURE_TYPE,
                Identifier.fromNamespaceAndPath(
                        MyceliumLeatherMod.MOD_ID,
                        name
                ),
                codec
        );
    }

    public static void init() {
    }
}

//    public static void bootstrap(
//            BootstrapContext<MapCodec<? extends Feature>> context
//    ) {
//        context.register(
//                HUGE_BLUE_MUSHROOM_WITH_HOLLOW,
//                HugeBlueMushroomWithHollowFeature.CODEC
//        );
//    }
//}
//        HugeBlueMushroomWithHollowFeature configBlue = getConfig(BlockTags.HUGE_BROWN_MUSHROOM_CAN_PLACE_ON);
//        context.register(
//                HUGE_BLUE_MUSHROOM_WITH_HOLLOW_KEY,
//                new FeatureTypes(
//                        HUGE_BLUE_MUSHROOM_WITH_HOLLOW,
//                        configBlue
//                ) {
//                });
//    }


//    private static HugeBlueMushroomWithHollowFeature getConfig(TagKey<Block> blockTag) {
//        return new HugeBlueMushroomWithHollowFeature(
//                BlockStateProvider.simple(
//                        ModBlocks.BLUE_MUSHROOM_BLOCK.defaultBlockState()
//                                .setValue(HugeMushroomBlock.DOWN, false)
//                ),
//                BlockStateProvider.simple(
//                        Blocks.MUSHROOM_STEM.defaultBlockState()
//                                .setValue(HugeMushroomBlock.UP, false)
//                                .setValue(HugeMushroomBlock.DOWN, false)
//                ),
//                3, // ToDo: Make dynamic
//                BlockPredicate.matchesTag(blockTag)
//        );
//    }


//}

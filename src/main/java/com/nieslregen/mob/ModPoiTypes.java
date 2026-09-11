package com.nieslregen.mob;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PoiHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;

public class ModPoiTypes {

    public static void init() {}

    public static final ResourceKey<PoiType> SQUIRREL_HOME_POI_KEY = ResourceKey.create(
            Registries.POINT_OF_INTEREST_TYPE,
            Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, "squirrel_home_poi")
    );
    public static PoiType SQUIRREL_HOME_POI = PoiHelper.register(
            Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, "squirrel_home_poi"),
            0,
            1,
            ModBlocks.MUSHROOM_STEM_HOLLOW
    );

//    public static void init() {
//        Registry.register(
//                BuiltInRegistries.POINT_OF_INTEREST_TYPE,
//                SQUIRREL_HOME,
//                new PoiType(
//                        getBlockStates(ModBlocks.MUSHROOM_STEM_HOLLOW),
//                        0,
//                        1
//                )
//        );
//    }
//
//    public static final ResourceKey<PoiType> SQUIRREL_HOME =
//            createKey("squirrel_home");
//
//    public static void bootstrap(BootstrapContext<PoiType> context) {
//        context.register(
//                SQUIRREL_HOME,
//                new PoiType(
//                        getBlockStates(ModBlocks.MUSHROOM_STEM_HOLLOW),
//                        0,
//                        1
//                )
//        );
//    }
//
//    private static ResourceKey<PoiType> createKey(String name) {
//        return ResourceKey.create(
//                Registries.POINT_OF_INTEREST_TYPE,
//                Identifier.fromNamespaceAndPath(
//                        MyceliumLeatherMod.MOD_ID,
//                        name
//                )
//        );
//    }
//
//    private static Set<BlockState> getBlockStates(Block block) {
//        return Set.copyOf(
//                block.getStateDefinition().getPossibleStates()
//        );
//    }
}
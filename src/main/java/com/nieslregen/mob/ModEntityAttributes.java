package com.nieslregen.mob;

import com.nieslregen.mob.cawler.Crawler;
import com.nieslregen.mob.myceliumchicken.MyceliumChicken;
import com.nieslregen.mob.myceliumsquirrel.MyceliumSquirrel;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class ModEntityAttributes {
    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(
                ModEntityTypes.MYCELIUM_CHICKEN,
                MyceliumChicken.createAttributes()
        );
        FabricDefaultAttributeRegistry.register(
                ModEntityTypes.CRAWLER,
                Crawler.createAttributes()
        );
        FabricDefaultAttributeRegistry.register(
                ModEntityTypes.SQUIRREL,
                MyceliumSquirrel.createAttributes()
        );
    }
}

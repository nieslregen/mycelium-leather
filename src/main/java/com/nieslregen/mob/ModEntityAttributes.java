package com.nieslregen.mob;

import com.nieslregen.mob.myceliumchicken.MyceliumChicken;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class ModEntityAttributes {
    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(
                ModEntityTypes.MYCELIUM_CHICKEN,
                MyceliumChicken.createAttributes()
        );
    }
}

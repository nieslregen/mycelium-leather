package com.nieslregen.mob;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.mob.myceliumchicken.MyceliumChicken;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntityTypes {

    public static final EntityType<MyceliumChicken> MYCELIUM_CHICKEN = register(
            "mycelium_chicken",
            EntityType.Builder.<MyceliumChicken>of(MyceliumChicken::new, MobCategory.MISC)
                    .sized(0.75f, 1.75f)
    );

    private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, name));
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }

    public static void registerModEntityTypes() {
        MyceliumLeatherMod.LOGGER.info("Registering EntityTypes for " + MyceliumLeatherMod.MOD_ID);
    }

    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(MYCELIUM_CHICKEN, MyceliumChicken.createCubeAttributes());
    }
}

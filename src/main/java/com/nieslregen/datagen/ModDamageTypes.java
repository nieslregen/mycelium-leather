package com.nieslregen.datagen;

import com.nieslregen.MyceliumLeatherMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;


public class ModDamageTypes {

    public static String BLEEDING_IDENTIFIER = "bleeding";
    public static String BACKSTAB_IDENTIFIER = "backstab";
    public static String STAB_IDENTIFIER = "stab";

    public static ResourceKey<DamageType> BLEEDING = ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, BLEEDING_IDENTIFIER));
    public static ResourceKey<DamageType> BACKSTAB = ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, BACKSTAB_IDENTIFIER));
    public static ResourceKey<DamageType> STAB = ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, STAB_IDENTIFIER));

    public static void bootstrap(BootstrapContext<DamageType> bootstrapContext) {
        bootstrapContext.register(BLEEDING, new DamageType(BLEEDING_IDENTIFIER, 0.1F, DamageEffects.POKING));
        bootstrapContext.register(BACKSTAB, new DamageType(BACKSTAB_IDENTIFIER, 0.1F, DamageEffects.HURT));
        bootstrapContext.register(STAB, new DamageType(STAB_IDENTIFIER, 0.1F, DamageEffects.HURT));
    }

    public static DamageSource create(Level level, ResourceKey<DamageType> key) {
        return new DamageSource(level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(key));
    }
}

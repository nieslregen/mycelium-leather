package com.nieslregen.effect;

import com.nieslregen.MyceliumLeatherMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class ModEffects {

    public static final MobEffect ILLNESS = registerMobEffect(
            "illness",
            new IllnessEffect(
                    MobEffectCategory.HARMFUL,
                    0x5e21ab
            )
    );

    private static MobEffect registerMobEffect(String name, MobEffect effect) {
        return Registry.register(
                BuiltInRegistries.MOB_EFFECT,
                Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, name),
                effect
        );
    }
    public static void registerEffects() {
        MyceliumLeatherMod.LOGGER.info("Registering effects");
    }
}

package com.nieslregen.mob.client;

import com.nieslregen.mob.ModEntityAttributes;
import com.nieslregen.mob.ModEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class ModCustomEntityClient implements ClientModInitializer {

    public void onInitializeClient() {
        ModEntityModelLayers.registerModelLayers();
        EntityRenderers.register(ModEntityTypes.MYCELIUM_CHICKEN, MyceliumChickenRenderer::new);
        ModEntityAttributes.registerAttributes();
    }
}

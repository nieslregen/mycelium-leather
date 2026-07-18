package com.nieslregen.mob.client;

import com.nieslregen.mob.ModEntityAttributes;
import com.nieslregen.mob.ModEntityTypes;
import com.nieslregen.mob.client.crawler.CrawlerRenderer;
import com.nieslregen.mob.client.myceliumchicken.MyceliumChickenRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class ModCustomEntityClient implements ClientModInitializer {

    public void onInitializeClient() {
        ModEntityModelLayers.registerModelLayers();
        registerEntity();
        ModEntityAttributes.registerAttributes();
    }

    private void registerEntity() {
        EntityRenderers.register(ModEntityTypes.MYCELIUM_CHICKEN, MyceliumChickenRenderer::new);
        EntityRenderers.register(ModEntityTypes.CRAWLER, CrawlerRenderer::new);
    }
}

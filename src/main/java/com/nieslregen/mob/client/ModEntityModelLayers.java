package com.nieslregen.mob.client;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.mob.client.crawler.CrawlerModel;
import com.nieslregen.mob.client.myceliumchicken.MyceliumChickenModel;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

public class ModEntityModelLayers {

    public static final ModelLayerLocation MYCELIUM_CHICKEN_LAYER = createMain("mycelium_chicken_layer");
    public static final ModelLayerLocation CRAWLER_LAYER = createMain("crawler_layer");

    private static ModelLayerLocation createMain(String name) {
        return new ModelLayerLocation(Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, name), "main");
    }

    public static void registerModelLayers() {
        ModelLayerRegistry.registerModelLayer(ModEntityModelLayers.MYCELIUM_CHICKEN_LAYER, MyceliumChickenModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ModEntityModelLayers.CRAWLER_LAYER, CrawlerModel::getTexturedModelData);;
    }
}

package com.nieslregen.mob.client.crawler;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

@Environment(EnvType.CLIENT)
public class CrawlerRendererState extends LivingEntityRenderState {
    public static final IntegerProperty SURFACE_VARIANT = IntegerProperty.create("surface_variant", 0, 3);
}

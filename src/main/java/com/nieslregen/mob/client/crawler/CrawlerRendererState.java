package com.nieslregen.mob.client.crawler;

import com.nieslregen.mob.cawler.Crawler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

@Environment(EnvType.CLIENT)
public class CrawlerRendererState extends LivingEntityRenderState {

    public Crawler.OvergrownType variant;
    public final BlockModelRenderState crawlerModel;

    public final AnimationState fallingAsleepAnimationState = new AnimationState();
    public final AnimationState wakingUpAnimationState = new AnimationState();

    public CrawlerRendererState() {
        this.variant = Crawler.OvergrownType.NONE;
        this.crawlerModel = new BlockModelRenderState();
    }
}

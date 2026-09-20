package com.nieslregen.mob.client.crawler;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.mob.cawler.Crawler;
import com.nieslregen.mob.client.ModEntityModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class CrawlerRenderer extends MobRenderer<Crawler, CrawlerRendererState, CrawlerModel> {

    private static Identifier CLEAN = Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, "textures/entity/crawler_clean.png");
    private static Identifier MUSHROOM = Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, "textures/entity/crawler_mushroom.png");
    private static Identifier MOSS = Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, "textures/entity/crawler_moss.png");
    private static Identifier PODZOL = Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, "textures/entity/crawler_podzol.png");
    private static Identifier SALT = Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, "textures/entity/crawler_salt.png");

    public CrawlerRenderer(EntityRendererProvider.Context context) {
        super(
                context,
                new CrawlerModel(context.bakeLayer(ModEntityModelLayers.CRAWLER_LAYER)),
                0.375F
        );
    }

    @Override
    public Identifier getTextureLocation(CrawlerRendererState state) {
        return switch (state.variant) {
            case Crawler.OvergrownType.MUSHROOM ->  MUSHROOM;
            case Crawler.OvergrownType.MOSS ->  MOSS;
            case Crawler.OvergrownType.PODZOL ->  PODZOL;
            case Crawler.OvergrownType.SALT ->  SALT;
            default ->  CLEAN;
        };
    }

    @Override
    public CrawlerRendererState createRenderState() {
        return new CrawlerRendererState();
    }

    @Override
    public void extractRenderState(Crawler entity, CrawlerRendererState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.variant = entity.getOvergrownSurface();
        state.fallingAsleepAnimationState.copyFrom(entity.fallingAsleepAnimationState);
        state.wakingUpAnimationState.copyFrom(entity.wakingUpAnimationState);
    }
}

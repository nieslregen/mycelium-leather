package com.nieslregen.mob.client;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.mob.myceliumchicken.MyceliumChicken;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class MyceliumChickenRenderer extends MobRenderer<MyceliumChicken, MyceliumChickenRenderState, MyceliumChickenModel> {

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, "textures/entity/mycelium_chicken.png");

    public MyceliumChickenRenderer(final EntityRendererProvider.Context context) {
        super(context, new MyceliumChickenModel(context.bakeLayer(ModEntityModelLayers.MYCELIUM_CHICKEN_LAYER)), 0.375F);
    }

    @Override
    public MyceliumChickenRenderState createRenderState() {
        return new MyceliumChickenRenderState();
    }

    @Override
    public Identifier getTextureLocation(MyceliumChickenRenderState state) {
        return TEXTURE;
    }
}

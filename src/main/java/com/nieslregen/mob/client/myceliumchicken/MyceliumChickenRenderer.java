package com.nieslregen.mob.client.myceliumchicken;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.mob.client.ModEntityModelLayers;
import com.nieslregen.mob.myceliumchicken.MyceliumChicken;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

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

    @Override
    public void extractRenderState(MyceliumChicken entity, MyceliumChickenRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.flap = Mth.lerp(partialTicks, entity.oFlap, entity.flap);
        state.flapSpeed = Mth.lerp(partialTicks, entity.oFlapSpeed, entity.flapSpeed);
    }
}

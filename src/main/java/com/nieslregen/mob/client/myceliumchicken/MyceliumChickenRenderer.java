package com.nieslregen.mob.client.myceliumchicken;

import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.mob.client.ModEntityModelLayers;
import com.nieslregen.mob.myceliumchicken.MyceliumChicken;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class MyceliumChickenRenderer extends AgeableMobRenderer<MyceliumChicken, MyceliumChickenRenderState, MyceliumChickenModel> {

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, "textures/entity/mycelium_chicken.png");
    private static final Identifier BABY_TEXTURE = Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID,"textures/entity/mycelium_chicken_baby.png");

    public MyceliumChickenRenderer(final EntityRendererProvider.Context context) {
        super(
                context,
                new MyceliumChickenModel(context.bakeLayer(ModEntityModelLayers.MYCELIUM_CHICKEN_LAYER)),
                new BabyMyceliumChickenModel(context.bakeLayer(ModEntityModelLayers.BABY_MYCELIUM_CHICKEN_LAYER)),
                0.375F
        );
    }

    @Override
    public MyceliumChickenRenderState createRenderState() {
        return new MyceliumChickenRenderState();
    }

    @Override
    public Identifier getTextureLocation(MyceliumChickenRenderState state) {
        return state.isBaby ? BABY_TEXTURE : TEXTURE;
    }

    @Override
    public void extractRenderState(MyceliumChicken entity, MyceliumChickenRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.flap = Mth.lerp(partialTicks, entity.oFlap, entity.flap);
        state.flapSpeed = Mth.lerp(partialTicks, entity.oFlapSpeed, entity.flapSpeed);
        state.sitDownAnimationState.copyFrom(entity.sitDownAnimationState);
        state.standUpAnimationState.copyFrom(entity.standUpUpAnimationState);
    }
}

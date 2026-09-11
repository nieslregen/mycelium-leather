package com.nieslregen.mob.client.myceliumsquirrel;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.mob.client.ModEntityModelLayers;
import com.nieslregen.mob.myceliumsquirrel.MyceliumSquirrel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

@Environment(EnvType.CLIENT)
public class MyceliumSquirrelRenderer extends AgeableMobRenderer<MyceliumSquirrel, MyceliumSquirrelRenderState, MyceliumSquirrelModel> {

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, "textures/entity/mycelium_squirrel.png");
    private static final Identifier BABY_TEXTURE = Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID,"textures/entity/mycelium_squirrel_baby.png");

    public MyceliumSquirrelRenderer(EntityRendererProvider.Context context) {
        super(
                context,
                new MyceliumSquirrelModel(context.bakeLayer(ModEntityModelLayers.MYCELIUM_SQUIRREL_LAYER)),
                new BabyMyceliumSquirrelModel(context.bakeLayer(ModEntityModelLayers.BABY_MYCELIUM_SQUIRREL_LAYER)),
                .175F
        );
    }

    @Override
    public Identifier getTextureLocation(MyceliumSquirrelRenderState state) {
        return state.isBaby ? BABY_TEXTURE : TEXTURE;
    }

    @Override
    public MyceliumSquirrelRenderState createRenderState() {
        return new MyceliumSquirrelRenderState();
    }

    @Override
    public void extractRenderState(MyceliumSquirrel entity, MyceliumSquirrelRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.diggingAnimationState.copyFrom(entity.diggingAnimationState);
        state.climbingAnimationState.copyFrom(entity.climbingAnimationState);
    }

    @Override
    protected void setupRotations(MyceliumSquirrelRenderState state, PoseStack poseStack, float bodyRot, float entityScale) {
        super.setupRotations(state, poseStack, bodyRot, entityScale);
    }
}

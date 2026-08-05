package com.nieslregen.mob.client.mycelium_squirrel;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.nieslregen.MyceliumLeatherMod;
import com.nieslregen.mob.client.ModEntityModelLayers;
import com.nieslregen.mob.myceliumsquirrel.MyceliumSquirrel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.*;

@Environment(EnvType.CLIENT)
public class MyceliumSquirrelRenderer extends MobRenderer<MyceliumSquirrel, MyceliumSquirrelRenderState, MyceliumSquirrelModel> {

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MyceliumLeatherMod.MOD_ID, "textures/entity/mycelium_squirrel.png");
    private Optional<MyceliumSquirrel> squirrel = Optional.empty();
    private Optional<Map.Entry<Direction, BlockPos>> sticksOnto = Optional.empty();
    private boolean isClimbing = false;

    public MyceliumSquirrelRenderer(EntityRendererProvider.Context context) {
        super(context, new MyceliumSquirrelModel(context.bakeLayer(ModEntityModelLayers.MYCELIUM_SQUIRREL_LAYER)), .175F);
    }

    @Override
    public Identifier getTextureLocation(MyceliumSquirrelRenderState state) {
        return TEXTURE;
    }

    @Override
    public MyceliumSquirrelRenderState createRenderState() {
        return new MyceliumSquirrelRenderState();
    }

    @Override
    public void extractRenderState(MyceliumSquirrel entity, MyceliumSquirrelRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        squirrel = Optional.of(entity);
    }

    @Override
    protected void setupRotations(MyceliumSquirrelRenderState state, PoseStack poseStack, float bodyRot, float entityScale) {
        super.setupRotations(state, poseStack, bodyRot, entityScale);

        // ToDo: move sticking to face logic into squirrel, then ask on which face it sticks (when it sticks onto something then you can rotate)
        if (squirrel.isPresent()) {
            MyceliumSquirrel s = squirrel.get();

            Map<Direction, BlockPos> m = Map.of(
                    Direction.NORTH, s.blockPosition().north(),
                    Direction.EAST,  s.blockPosition().east(),
                    Direction.SOUTH, s.blockPosition().south(),
                    Direction.WEST,  s.blockPosition().west()
            );

            Optional<Map.Entry<Direction, BlockPos>> nearest =
                    m.entrySet()
                            .stream()
                            .filter(e -> !s.level().getBlockState(e.getValue()).is(Blocks.AIR))
                            .min(Comparator.comparingDouble(e -> e.getValue()
                                    .distToCenterSqr(
                                            s.position().x,
                                            s.position().y,
                                            s.position().z)));
            if (nearest.isPresent()) {
                poseStack.mulPose(Axis.XP.rotationDegrees(90));

                if (nearest.get().getValue().distToCenterSqr(s.position().x, s.position().y, s.position().z) < .1D) {
                    isClimbing = true;
                    sticksOnto = nearest;
                }

            } else {
                if (isClimbing) {
                    isClimbing = false;
                    poseStack.mulPose(Axis.XP.rotationDegrees(90));
                }
            }
        }
    }
}

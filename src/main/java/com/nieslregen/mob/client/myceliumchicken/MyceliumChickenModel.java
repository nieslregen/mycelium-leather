package com.nieslregen.mob.client.myceliumchicken;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class MyceliumChickenModel extends EntityModel<MyceliumChickenRenderState> {
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart rightWing;
    private final ModelPart leftWing;

    private final KeyframeAnimation walkAnimation;
    private final KeyframeAnimation sitAnimation;
    private final KeyframeAnimation sitPoseAnimation;
    private final KeyframeAnimation standupAnimation;
    private final KeyframeAnimation idleAnimation;

    public MyceliumChickenModel(ModelPart root) {
        super(root);
        this.root = root.getChild("root");
        this.head = root.getChild("root").getChild("torso").getChild("head");
        this.leftWing = root.getChild("root").getChild("torso").getChild("body").getChild("left_wing");
        this.rightWing = root.getChild("root").getChild("torso").getChild("body").getChild("right_wing");

        this.walkAnimation = MyceliumChickenAnimation.walking.bake(this.root);
        this.sitAnimation = MyceliumChickenAnimation.take_a_seat.bake(this.root);
        this.sitPoseAnimation = MyceliumChickenAnimation.take_a_seat.bake(this.root);
        this.standupAnimation = MyceliumChickenAnimation.stand_up.bake(this.root);
        this.idleAnimation = MyceliumChickenAnimation.walking.bake(this.root);
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition root = modelData.getRoot().addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition torso = root
                .addOrReplaceChild("torso",
                        CubeListBuilder.create(),
                        PartPose.offset(0.0F, -4.0F, 1.0F));

        PartDefinition head = torso.addOrReplaceChild(
                "head",
                CubeListBuilder.create()
                        .texOffs(10, 29)
                        .addBox(-2.0F, -4.0F, -5.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(1, 30)
                        .addBox(-1.0F, -2.0F, -4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(9, 34)
                        .addBox(-2.0F, -6.0F, -3.0F, 4.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 35)
                        .addBox(-2.0F, -6.0F, 0.0F, 4.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -4.0F, -3.0F));

        PartDefinition body = torso.addOrReplaceChild(
                "body",
                CubeListBuilder.create().texOffs(1, 0)
                        .addBox(-3.0F, -6.0F, -4.0F, 6.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
                        .texOffs(33, 9)
                        .addBox(0.0F, -7.0F, 3.0F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_wing = body.addOrReplaceChild(
                "left_wing",
                CubeListBuilder.create()
                        .texOffs(1, 14)
                        .addBox(0.0F, 0.0F, -1.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offset(3.0F, -6.0F, -2.0F));

        PartDefinition right_wing = body.addOrReplaceChild(
                "right_wing",
                CubeListBuilder.create()
                        .texOffs(16, 14)
                        .addBox(-1.0F, 0.0F, -1.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-3.0F, -6.0F, -2.0F));

        PartDefinition mushrooms = body.addOrReplaceChild(
                "mushrooms",
                CubeListBuilder.create()
                        .texOffs(43, 3)
                        .addBox(3.0F, -3.0F, -2.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(33, 3)
                        .addBox(-2.0F, -3.0F, 1.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -6.0F, -1.0F));

        PartDefinition cube_r1 = mushrooms.addOrReplaceChild(
                "cube_r1",
                CubeListBuilder.create()
                        .texOffs(33, -1)
                        .addBox(0.0F, -3.0F, -3.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.0F, 0.0F, 3.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r2 = mushrooms.addOrReplaceChild(
                "cube_r2",
                CubeListBuilder.create()
                        .texOffs(43, -1)
                        .addBox(0.0F, -3.0F, -3.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(4.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition feet = root.addOrReplaceChild(
                "feet",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, -4.0F, 1.0F));

        PartDefinition left_leg = feet.addOrReplaceChild(
                "left_leg",
                CubeListBuilder.create()
                        .texOffs(44, 28)
                        .addBox(0.0F, 0.0F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(1.0F, 0.0F, 0.0F));

        PartDefinition left_foot = left_leg.addOrReplaceChild(
                "left_foot",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 4.0F, 0.0F));

        PartDefinition cube_r3 = left_foot.addOrReplaceChild(
                "cube_r3",
                CubeListBuilder.create()
                        .texOffs(42, 32)
                        .addBox(-1.0F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition right_leg = feet.addOrReplaceChild(
                "right_leg",
                CubeListBuilder.create()
                        .texOffs(36, 28)
                        .addBox(-1.0F, 0.0F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-1.0F, 0.0F, 0.0F));

        PartDefinition right_foot = right_leg.addOrReplaceChild(
                "right_foot",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 4.0F, 0.0F));

        PartDefinition cube_r4 = right_foot.addOrReplaceChild(
                "cube_r4",
                CubeListBuilder.create().texOffs(34, 32)
                        .addBox(-1.0F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }

    public void setupAnim(final MyceliumChickenRenderState state) {
        super.setupAnim(state);
        this.applyHeadRotation(state, state.yRot, state.xRot);
        this.walkAnimation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, 2.0F, 2.5F);
        this.sitAnimation.apply(state.sitAnimationState, state.ageInTicks);
        this.sitPoseAnimation.apply(state.sitPoseAnimationState, state.ageInTicks);
        this.standupAnimation.apply(state.sitUpAnimationState, state.ageInTicks);
        this.idleAnimation.apply(state.idleAnimationState, state.ageInTicks);

        float flapAngle = (Mth.sin((double)state.flap) + 1.0F) * state.flapSpeed;
        this.rightWing.zRot = flapAngle;
        this.leftWing.zRot = -flapAngle;
    }

    private void applyHeadRotation(final MyceliumChickenRenderState state, float yRot, float xRot) {
        yRot = Mth.clamp(yRot, -30.0F, 30.0F);
        xRot = Mth.clamp(xRot, -25.0F, 45.0F);
//        if (state.jumpCooldown > 0.0F) {
//            float headRotation = 45.0F * state.jumpCooldown / 55.0F;
//            xRot = Mth.clamp(xRot + headRotation, -25.0F, 70.0F);
//        }

        this.head.yRot = yRot * ((float)Math.PI / 180F);
        this.head.xRot = xRot * ((float)Math.PI / 180F);
    }
}

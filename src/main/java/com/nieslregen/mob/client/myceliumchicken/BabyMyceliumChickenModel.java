package com.nieslregen.mob.client.myceliumchicken;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class BabyMyceliumChickenModel extends MyceliumChickenModel {

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart rightWing;
    private final ModelPart leftWing;

    private final KeyframeAnimation walkAnimation;
    private final KeyframeAnimation sitAnimation;
    private final KeyframeAnimation sitPoseAnimation;
    private final KeyframeAnimation standupAnimation;
    private final KeyframeAnimation idleAnimation;
    
    public BabyMyceliumChickenModel(ModelPart root) {
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

    public static LayerDefinition createBodyLayer() {
//        MeshDefinition mesh = new MeshDefinition();
//        PartDefinition root = mesh.getRoot();
//        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.25F, -0.75F, 4.0F, 4.0F, 4.0F).texOffs(10, 8).addBox(-1.0F, -0.25F, -1.75F, 2.0F, 1.0F, 1.0F), PartPose.offset(0.0F, 20.25F, -1.25F));
//        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(2, 2).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F).texOffs(0, 1).addBox(-0.5F, 2.0F, -1.0F, 1.0F, 0.0F, 1.0F), PartPose.offset(1.0F, 22.0F, 0.5F));
//        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 2).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F).texOffs(0, 0).addBox(-0.5F, 2.0F, -1.0F, 1.0F, 0.0F, 1.0F), PartPose.offset(-1.0F, 22.0F, 0.5F));
//        root.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(6, 8).addBox(0.0F, 0.0F, -1.0F, 1.0F, 0.0F, 2.0F), PartPose.offset(2.0F, 20.0F, 0.0F));
//        root.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(4, 8).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 0.0F, 2.0F), PartPose.offset(-2.0F, 20.0F, 0.0F));
//        return LayerDefinition.create(mesh, 16, 16);


        MeshDefinition modelData = new MeshDefinition();
        PartDefinition root = modelData.getRoot().addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition torso = root.addOrReplaceChild("torso", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 1.0F));

        PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(12, 30).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 33).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body = torso.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(5, 18).addBox(0.0F, 0.0F, -1.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, 0.0F));

        PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(20, 18).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 0.0F, 0.0F));

        PartDefinition feet = root.addOrReplaceChild("feet", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 1.0F));

        PartDefinition left_leg = feet.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(44, 28).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 2.0F, 0.0F));

        PartDefinition left_foot = left_leg.addOrReplaceChild("left_foot", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition cube_r1 = left_foot.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(43, 32).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition right_leg = feet.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(36, 28).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 2.0F, 0.0F));

        PartDefinition right_foot = right_leg.addOrReplaceChild("right_foot", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition cube_r2 = right_foot.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(35, 32).addBox(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));
        return LayerDefinition.create(modelData, 64, 64);

    }

}

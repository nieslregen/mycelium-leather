package com.nieslregen.mob.client.myceliumsquirrel;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class BabyMyceliumSquirrelModel extends MyceliumSquirrelModel {
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart tail;
	private final ModelPart head;
	private final ModelPart nose;
	private final ModelPart ear_left;
	private final ModelPart ear_right;
	private final ModelPart leg_front_left;
	private final ModelPart leg_back_left;
	private final ModelPart leg_back_right;
	private final ModelPart leg_front_right;

	private final KeyframeAnimation walkAnimation;
	private final KeyframeAnimation diggingAnimation;

	public BabyMyceliumSquirrelModel(ModelPart root) {
        super(root);
        this.root = root.getChild("root");
		this.body = this.root.getChild("body");
		this.tail = this.body.getChild("tail");
		this.head = this.body.getChild("head");
		this.nose = this.head.getChild("nose");
		this.ear_left = this.head.getChild("ear_left");
		this.ear_right = this.head.getChild("ear_right");
		this.leg_front_left = this.body.getChild("leg_front_left");
		this.leg_back_left = this.body.getChild("leg_back_left");
		this.leg_back_right = this.body.getChild("leg_back_right");
		this.leg_front_right = this.body.getChild("leg_front_right");

		this.walkAnimation = MyceliumSquirrelAnimation.walking.bake(this.root);
		this.diggingAnimation = MyceliumSquirrelAnimation.diggin.bake(this.root);

	}
	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition root = modelData.getRoot().addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 2.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(6, 5).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -2.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(4, 24).addBox(-2.0F, -2.0F, -1.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 2.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(5, 12).addBox(-1.5F, -2.0F, -3.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -1.0F));

		PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(1, 20).addBox(-0.5F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -4.0F));

		PartDefinition ear_left = head.addOrReplaceChild("ear_left", CubeListBuilder.create().texOffs(24, 1).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.8F, -2.5F, -2.2F));

		PartDefinition ear_right = head.addOrReplaceChild("ear_right", CubeListBuilder.create().texOffs(24, 4).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.8F, -1.5F, -2.2F));

		PartDefinition leg_front_left = body.addOrReplaceChild("leg_front_left", CubeListBuilder.create().texOffs(24, 17).addBox(0.0F, 0.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.2F, 0.0F, -1.2F));

		PartDefinition leg_back_left = body.addOrReplaceChild("leg_back_left", CubeListBuilder.create().texOffs(24, 22).addBox(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.2F, 0.0F, 0.2F));

		PartDefinition leg_back_right = body.addOrReplaceChild("leg_back_right", CubeListBuilder.create().texOffs(25, 12).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.2F, 0.0F, 0.2F));

		PartDefinition leg_front_right = body.addOrReplaceChild("leg_front_right", CubeListBuilder.create().texOffs(25, 27).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.2F, 0.0F, -1.2F));
		return LayerDefinition.create(modelData, 32, 32);
	}
}
package com.nieslregen.mob.client.crawler;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class CrawlerModel extends EntityModel<CrawlerRendererState> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart foliage;
    private final ModelPart mushrooms;
    private final ModelPart feet;
    private final ModelPart right_back_foot;
    private final ModelPart right_front_foot;
    private final ModelPart left_back_foot;
    private final ModelPart left_front_foot;

    private final KeyframeAnimation walkAnimation;
    private final KeyframeAnimation wakingUpAnimation;
    private final KeyframeAnimation fallingAsleepAnimation;

    protected CrawlerModel(ModelPart root) {
        super(root);
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.foliage = this.body.getChild("foliage");
        this.mushrooms = this.body.getChild("mushrooms");
        this.feet = this.root.getChild("feet");
        this.right_back_foot = this.feet.getChild("right_back_foot");
        this.right_front_foot = this.feet.getChild("right_front_foot");
        this.left_back_foot = this.feet.getChild("left_back_foot");
        this.left_front_foot = this.feet.getChild("left_front_foot");

        this.walkAnimation = CrawlerAnimation.walking.bake(this.root);
        this.wakingUpAnimation = CrawlerAnimation.wake_up.bake(this.root);
        this.fallingAsleepAnimation = CrawlerAnimation.fall_asleep.bake(this.root);
    }


    public static LayerDefinition getTexturedModelData() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -7.0F, -4.0F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition foliage = body.addOrReplaceChild("foliage", CubeListBuilder.create().texOffs(0, 14).addBox(4.0F, 0.0F, -4.0F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(16, 14).addBox(-4.0F, 0.0F, -4.0F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, 0.0F));

        PartDefinition cube_r1 = foliage.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(16, 23).addBox(3.0F, -7.0F, -1.0F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 7.0F, 7.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r2 = foliage.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 23).addBox(3.0F, -7.0F, -1.0F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 7.0F, -1.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition mushrooms = body.addOrReplaceChild("mushrooms", CubeListBuilder.create().texOffs(9, 33).addBox(2.0F, -3.0F, -3.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 42).addBox(-1.0F, -4.0F, 0.0F, 0.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r3 = mushrooms.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 38).addBox(0.0F, -4.0F, -3.0F, 0.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.0F, 2.5F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r4 = mushrooms.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(9, 29).addBox(0.0F, -3.0F, -3.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.0F, -1.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition feet = root.addOrReplaceChild("feet", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition right_back_foot = feet.addOrReplaceChild("right_back_foot", CubeListBuilder.create().texOffs(28, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 1.0F, 3.0F));

        PartDefinition right_front_foot = feet.addOrReplaceChild("right_front_foot", CubeListBuilder.create().texOffs(28, 4).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 1.0F, -4.0F));

        PartDefinition left_back_foot = feet.addOrReplaceChild("left_back_foot", CubeListBuilder.create().texOffs(28, 8).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 1.0F, 3.0F));

        PartDefinition left_front_foot = feet.addOrReplaceChild("left_front_foot", CubeListBuilder.create().texOffs(28, 12).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 1.0F, -4.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }
    public void setupAnim(final CrawlerRendererState state) {
        super.setupAnim(state);
        this.applyHeadRotation(state, state.yRot, state.xRot);
        this.walkAnimation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, 7.5F, 8F);

        if (state.fallingAsleepAnimationState.isStarted()) {
            this.fallingAsleepAnimation.apply(state.fallingAsleepAnimationState, state.ageInTicks);
        }

        if (state.wakingUpAnimationState.isStarted()) {
            this.wakingUpAnimation.apply(state.wakingUpAnimationState, state.ageInTicks);
        }
    }

    private void applyHeadRotation(final CrawlerRendererState state, float yRot, float xRot) {
        yRot = Mth.clamp(yRot, -30.0F, 30.0F);
        xRot = Mth.clamp(xRot, -25.0F, 45.0F);

        this.body.yRot = yRot * ((float)Math.PI / 180F);
        this.body.xRot = xRot * ((float)Math.PI / 180F);
    }
}

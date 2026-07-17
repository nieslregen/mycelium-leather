package com.nieslregen.mob.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

@Environment(EnvType.CLIENT)
public class MyceliumChickenRenderState extends LivingEntityRenderState {

    public final AnimationState sitAnimationState;
    public final AnimationState sitPoseAnimationState;
    public final AnimationState sitUpAnimationState;
    public final AnimationState idleAnimationState;

    public float flap;
    public float flapSpeed;

    public MyceliumChickenRenderState() {
        this.sitAnimationState = new AnimationState();
        this.sitPoseAnimationState = new AnimationState();
        this.sitUpAnimationState = new AnimationState();
        this.idleAnimationState = new AnimationState();
    }

}

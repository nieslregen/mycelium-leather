package com.nieslregen.mob.client.myceliumsquirrel;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

@Environment(EnvType.CLIENT)
public class MyceliumSquirrelRenderState extends LivingEntityRenderState {

    public final AnimationState diggingAnimationState = new AnimationState();
    public final AnimationState climbingAnimationState = new AnimationState();

    public MyceliumSquirrelRenderState() { }
}

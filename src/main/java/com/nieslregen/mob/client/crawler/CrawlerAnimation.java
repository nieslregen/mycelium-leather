package com.nieslregen.mob.client.crawler;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class CrawlerAnimation { 
    
    
    public static final AnimationDefinition walking = AnimationDefinition.Builder.withLength(1.0F).looping()
    
    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-0.44F, 2.26F, -2.28F), AnimationChannel.Interpolations.LINEAR),
        new Keyframe(0.125F, KeyframeAnimations.degreeVec(-0.4369F, 4.9809F, -5.019F), AnimationChannel.Interpolations.LINEAR),
        new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
        new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-0.4369F, -4.9809F, 5.019F), AnimationChannel.Interpolations.LINEAR),
        new Keyframe(1.0F, KeyframeAnimations.degreeVec(-0.44F, 2.26F, -2.28F), AnimationChannel.Interpolations.LINEAR)
    ))
    .addAnimation(
    "left_front_foot", new AnimationChannel(AnimationChannel.Targets.POSITION,
        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.67F, -0.67F), AnimationChannel.Interpolations.LINEAR),
        new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, 1.0F, -1.0F), AnimationChannel.Interpolations.LINEAR),
        new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.LINEAR),
        new Keyframe(0.4583F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
        new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
        new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.67F, -0.67F), AnimationChannel.Interpolations.LINEAR)
    ))
    .addAnimation(
    "left_back_foot", new AnimationChannel(AnimationChannel.Targets.POSITION,
        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.0F, -0.99F), AnimationChannel.Interpolations.LINEAR),
        new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.LINEAR),
        new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
        new Keyframe(0.625F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
        new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 1.0F, -0.99F), AnimationChannel.Interpolations.LINEAR)
    ))
    .addAnimation(
    "right_front_foot", new AnimationChannel(AnimationChannel.Targets.POSITION,
        new Keyframe(0.4167F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
        new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, 1.0F, -1.0F), AnimationChannel.Interpolations.LINEAR),
        new Keyframe(0.8333F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.LINEAR),
        new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
    ))
    .addAnimation(
    "right_back_foot", new AnimationChannel(AnimationChannel.Targets.POSITION,
        new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
        new Keyframe(0.5417F, KeyframeAnimations.posVec(0.0F, 1.0F, -0.99F), AnimationChannel.Interpolations.LINEAR),
        new Keyframe(0.7083F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.LINEAR),
        new Keyframe(0.875F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
    ))
    .build();

}

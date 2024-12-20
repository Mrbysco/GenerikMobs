package com.mrbysco.generikmobs.client.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class TexChetAnimation {

	public static final AnimationDefinition IDLE = AnimationDefinition.Builder.withLength(0.75f).looping()
			.addAnimation("bone",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 1.11f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5416766f, KeyframeAnimations.degreeVec(0f, 0f, 2.5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 1.11f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone2",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, -1.11f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5416766f, KeyframeAnimations.degreeVec(0f, 0f, -2.5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, -1.11f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("body",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.375f, KeyframeAnimations.posVec(0f, -0.5f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_arm",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, -6.9f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(0f, 0f, -10f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5416766f, KeyframeAnimations.degreeVec(0f, 0f, -2.5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, -6.9f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_sleve",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_arm",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 6.9f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(0f, 0f, 10f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5416766f, KeyframeAnimations.degreeVec(0f, 0f, 2.5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 6.9f),
									AnimationChannel.Interpolations.LINEAR))).build();
	public static final AnimationDefinition WALK = AnimationDefinition.Builder.withLength(0.6766666f).looping()
			.addAnimation("head",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.08343333f, KeyframeAnimations.degreeVec(1f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.degreeVec(-1f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.4167667f, KeyframeAnimations.degreeVec(1f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5834334f, KeyframeAnimations.degreeVec(-1f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("body",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.3f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.16766666f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.3433333f, KeyframeAnimations.posVec(0f, -0.3f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.6766666f, KeyframeAnimations.posVec(0f, -0.3f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_arm",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(-22.5f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(22.5f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(-22.5f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_arm",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(22.5f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(-22.5f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(22.5f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_leg",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0.5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.16766666f, KeyframeAnimations.posVec(0f, 2f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.3433333f, KeyframeAnimations.posVec(0f, 0f, -0.5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.6766666f, KeyframeAnimations.posVec(0f, 0f, 0.5f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_leg",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(32.5f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(-32.5f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(32.5f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_leg",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, -0.5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.3433333f, KeyframeAnimations.posVec(0f, 0f, 0.5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 2f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.6766666f, KeyframeAnimations.posVec(0f, 0f, -0.5f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_leg",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(-32.5f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(32.5f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(-32.5f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR))).build();
}

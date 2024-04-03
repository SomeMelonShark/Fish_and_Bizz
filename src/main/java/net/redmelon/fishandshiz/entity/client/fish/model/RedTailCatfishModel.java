package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.redmelon.fishandshiz.entity.animation.ModAnimations;
import net.redmelon.fishandshiz.entity.custom.fish.RedTailCatfishEntity;

// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class RedTailCatfishModel<T extends RedTailCatfishEntity> extends SinglePartEntityModel<T> {
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart tailFin;
	private final ModelPart tailFin2;
	private final ModelPart leftWhiskers;
	private final ModelPart rightWhiskers;

	public RedTailCatfishModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body = root.getChild("root").getChild("body");
		this.tailFin = root.getChild("root").getChild("body").getChild("tailFin");
		this.tailFin2 = root.getChild("root").getChild("body").getChild("tailFin").getChild("tailFin2");
		this.leftWhiskers = root.getChild("root").getChild("body").getChild("leftWhiskers");
		this.rightWhiskers = root.getChild("root").getChild("body").getChild("rightWhiskers");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 16.0F, -13.0F));

		ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-7.0F, -6.0F, -8.0F, 14.0F, 14.0F, 19.0F, new Dilation(0.0F))
		.uv(46, 33).cuboid(-6.0F, -3.0F, -17.0F, 12.0F, 7.0F, 9.0F, new Dilation(0.0F))
		.uv(26, 45).cuboid(0.0F, -16.0F, 2.0F, 0.0F, 12.0F, 13.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData body_r1 = body.addChild("body_r1", ModelPartBuilder.create().uv(-7, 6).cuboid(-6.0F, 0.0F, -3.0F, 6.0F, 0.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(-7.0F, 8.0F, 7.0F, 0.0F, 0.0F, -0.3927F));

		ModelPartData body_r2 = body.addChild("body_r2", ModelPartBuilder.create().uv(-7, 33).cuboid(0.0F, 0.0F, -3.0F, 6.0F, 0.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, 8.0F, 7.0F, 0.0F, 0.0F, 0.3927F));

		ModelPartData jaw = body.addChild("jaw", ModelPartBuilder.create().uv(47, 0).cuboid(-5.0F, 5.0F, -5.0F, 10.0F, 3.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, -11.0F));

		ModelPartData tailFin = body.addChild("tailFin", ModelPartBuilder.create().uv(0, 33).cuboid(-4.0F, -5.0F, 0.0F, 8.0F, 10.0F, 15.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 2.0F, 11.0F));

		ModelPartData tailFin_r1 = tailFin.addChild("tailFin_r1", ModelPartBuilder.create().uv(-6, 0).cuboid(-5.0F, 0.0F, -2.0F, 5.0F, 0.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, 5.0F, 4.0F, 0.0F, 0.0F, -0.48F));

		ModelPartData tailFin_r2 = tailFin.addChild("tailFin_r2", ModelPartBuilder.create().uv(-6, 13).cuboid(0.0F, 0.0F, -2.0F, 5.0F, 0.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, 5.0F, 4.0F, 0.0F, 0.0F, 0.48F));

		ModelPartData tailFin2 = tailFin.addChild("tailFin2", ModelPartBuilder.create().uv(57, 51).cuboid(-2.0F, -4.0F, 0.0F, 4.0F, 7.0F, 7.0F, new Dilation(0.0F))
		.uv(0, 45).cuboid(0.0F, -7.0F, 7.0F, 0.0F, 13.0F, 13.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 1.0F, 15.0F));

		ModelPartData leftfin = body.addChild("leftfin", ModelPartBuilder.create().uv(37, 49).cuboid(0.0F, 0.0F, -5.0F, 9.0F, 0.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.4363F));

		ModelPartData rightfin = body.addChild("rightfin", ModelPartBuilder.create().uv(22, 33).cuboid(-9.0F, 0.0F, -5.0F, 9.0F, 0.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(-7.0F, 4.0F, 0.0F, 0.0F, 0.0F, -0.4363F));

		ModelPartData leftWhiskers = body.addChild("leftWhiskers", ModelPartBuilder.create().uv(47, 4).cuboid(0.0F, -1.0F, -10.0F, 0.0F, 1.0F, 10.0F, new Dilation(0.0F))
		.uv(47, 3).cuboid(1.0F, 0.0F, -10.0F, 0.0F, 1.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 3.0F, -17.0F));

		ModelPartData rightWhiskers = body.addChild("rightWhiskers", ModelPartBuilder.create().uv(47, 2).cuboid(0.0F, -1.0F, -10.0F, 0.0F, 1.0F, 10.0F, new Dilation(0.0F))
		.uv(47, 1).cuboid(-1.0F, 0.0F, -10.0F, 0.0F, 1.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, 3.0F, -17.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}
	@Override
	public void setAngles(RedTailCatfishEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		if (entity.isAttacking()) {
			this.animateMovement(ModAnimations.RED_TAIL_CATFISH_BITE, limbAngle, limbDistance, 2f, 2f);
		}
		float f = 1.0f;
		if (!entity.isTouchingWater()) {
			f = 1.5f;
		}
		this.leftWhiskers.yaw = f * 0.8f * MathHelper.sin(0.2f * animationProgress);
		this.rightWhiskers.yaw = -f * 0.8f * MathHelper.sin(0.2f * animationProgress);
		this.body.pitch = headPitch * ((float)Math.PI / 180);
		this.body.yaw = headYaw * ((float)Math.PI / 180);
		if (entity.getVelocity().horizontalLengthSquared() > 1.0E-7) {
			this.body.yaw += -0.05f - 0.05f * MathHelper.cos(animationProgress * 0.3f);
			this.tailFin.yaw = -0.2f * MathHelper.cos(animationProgress * 0.5f);
			this.tailFin2.yaw = -0.25f * MathHelper.cos(animationProgress * 0.5f);
		}
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		root.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return root;
	}
}
package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.renderer.ArcherfishRenderer;
import net.redmelon.fishandshiz.entity.custom.fish.ArcherfishEntity;
import net.redmelon.fishandshiz.entity.custom.fish.RedTailCatfishEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class RedTailCatfishModel extends GeoModel<RedTailCatfishEntity> {
	@Override
	public Identifier getModelResource(RedTailCatfishEntity animatable) {
		return new Identifier(FishAndShiz.MOD_ID, "geo/red_tail_catfish.geo.json");
	}

	@Override
	public Identifier getTextureResource(RedTailCatfishEntity animatable) {
		return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/red_tail_catfish/red_tail_catfish.png");
	}

	@Override
	public Identifier getAnimationResource(RedTailCatfishEntity animatable) {
		return new Identifier(FishAndShiz.MOD_ID, "animations/red_tail_catfish.animation.json");
	}

	@Override
	public void setCustomAnimations(RedTailCatfishEntity animatable, long instanceId, AnimationState<RedTailCatfishEntity> animationState) {
		CoreGeoBone root = getAnimationProcessor().getBone("root");

		if (root != null) {
			EntityModelData entityModelData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

			root.setRotX(entityModelData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
			root.setRotY(entityModelData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
		}
	}
}
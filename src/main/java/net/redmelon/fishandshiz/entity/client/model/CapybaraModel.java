package net.redmelon.fishandshiz.entity.client.model;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.CapybaraEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class CapybaraModel extends GeoModel<CapybaraEntity> {
    @Override
    public Identifier getModelResource(CapybaraEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/capybara.geo.json");
    }
    @Override
    public Identifier getTextureResource(CapybaraEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/capybara.png");
    }

    @Override
    public Identifier getAnimationResource(CapybaraEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/capybara.animation.json");
    }

    @Override
    public void setCustomAnimations(CapybaraEntity animatable, long instanceId, AnimationState<CapybaraEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch()*MathHelper.RADIANS_PER_DEGREE);
            head.setRotY(entityData.netHeadYaw()*MathHelper.RADIANS_PER_DEGREE);
        }
    }
}

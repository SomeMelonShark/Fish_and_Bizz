package net.redmelon.fishandshiz.entity.client;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.CapybaraEntity;
import net.redmelon.fishandshiz.entity.custom.MilkfishEntity;
import net.redmelon.fishandshiz.entity.custom.MudCrabEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class MudCrabModel extends GeoModel<MudCrabEntity> {
    @Override
    public Identifier getModelResource(MudCrabEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/mud_crab.geo.json");
    }

    @Override
    public Identifier getTextureResource(MudCrabEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/mud_crab.png");
    }

    @Override
    public Identifier getAnimationResource(MudCrabEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/mud_crab.animation.json");
    }

    @Override
    public void setCustomAnimations(MudCrabEntity animatable, long instanceId, AnimationState<MudCrabEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch()* MathHelper.RADIANS_PER_DEGREE);
            head.setRotY(entityData.netHeadYaw()*MathHelper.RADIANS_PER_DEGREE);
        }
    }
}

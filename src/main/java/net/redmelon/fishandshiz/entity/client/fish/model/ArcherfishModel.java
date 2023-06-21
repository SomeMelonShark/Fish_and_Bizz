package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.ArcherfishEntity;
import net.redmelon.fishandshiz.entity.custom.CapybaraEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class ArcherfishModel extends GeoModel<ArcherfishEntity> {
    @Override
    public Identifier getModelResource(ArcherfishEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/archerfish.geo.json");
    }

    @Override
    public Identifier getTextureResource(ArcherfishEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/archerfish.png");
    }

    @Override
    public Identifier getAnimationResource(ArcherfishEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/archerfish.animation.json");
    }

    @Override
    public void setCustomAnimations(ArcherfishEntity animatable, long instanceId, AnimationState<ArcherfishEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch()* MathHelper.RADIANS_PER_DEGREE);
            head.setRotY(entityData.netHeadYaw()*MathHelper.RADIANS_PER_DEGREE);
        }
    }
}

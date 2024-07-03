package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.renderer.ArcherfishRenderer;
import net.redmelon.fishandshiz.entity.custom.fish.ArcherfishEntity;
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
        return ArcherfishRenderer.LOCATION_BY_VARIANT.get(animatable.getVariant());
    }

    @Override
    public Identifier getAnimationResource(ArcherfishEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/mediumfish.animation.json");
    }

    @Override
    public void setCustomAnimations(ArcherfishEntity animatable, long instanceId, AnimationState<ArcherfishEntity> animationState) {
        CoreGeoBone root = getAnimationProcessor().getBone("root");

        if (root != null) {
            EntityModelData entityModelData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            root.setRotX(entityModelData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            root.setRotX(entityModelData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}

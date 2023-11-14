package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.renderer.AmurCarpRenderer;
import net.redmelon.fishandshiz.entity.custom.AmurCarpEntity;
import software.bernie.geckolib.model.GeoModel;

public class AmurCarpModel extends GeoModel<AmurCarpEntity> {
    @Override
    public Identifier getModelResource(AmurCarpEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/amur_carp.geo.json");
    }

    @Override
    public Identifier getTextureResource(AmurCarpEntity animatable) {
        return AmurCarpRenderer.LOCATION_BY_VARIANT.get(animatable.getVariant());
    }

    @Override
    public Identifier getAnimationResource(AmurCarpEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/amur_carp.animation.json");
    }
}

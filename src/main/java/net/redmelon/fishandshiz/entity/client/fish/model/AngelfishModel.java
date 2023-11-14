package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.renderer.AngelfishRenderer;
import net.redmelon.fishandshiz.entity.custom.AngelfishEntity;
import software.bernie.geckolib.model.GeoModel;

public class AngelfishModel extends GeoModel<AngelfishEntity> {
    @Override
    public Identifier getModelResource(AngelfishEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/angelfish.geo.json");
    }

    @Override
    public Identifier getTextureResource(AngelfishEntity animatable) {
        return AngelfishRenderer.LOCATION_BY_VARIANT.get(animatable.getVariant());
    }

    @Override
    public Identifier getAnimationResource(AngelfishEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/angelfish.animation.json");
    }
}

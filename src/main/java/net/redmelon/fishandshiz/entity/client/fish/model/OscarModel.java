package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.OscarEntity;
import software.bernie.geckolib.model.GeoModel;

public class OscarModel extends GeoModel<OscarEntity> {
    @Override
    public Identifier getModelResource(OscarEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/oscar.geo.json");
    }

    @Override
    public Identifier getTextureResource(OscarEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/oscar.png");
    }

    @Override
    public Identifier getAnimationResource(OscarEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/oscar.animation.json");
    }
}

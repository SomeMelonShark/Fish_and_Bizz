package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.GraylingEntity;
import software.bernie.geckolib.model.GeoModel;

public class GraylingModel extends GeoModel<GraylingEntity> {
    @Override
    public Identifier getModelResource(GraylingEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/grayling.geo.json");
    }

    @Override
    public Identifier getTextureResource(GraylingEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/grayling.png");
    }

    @Override
    public Identifier getAnimationResource(GraylingEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/grayling.animation.json");
    }
}
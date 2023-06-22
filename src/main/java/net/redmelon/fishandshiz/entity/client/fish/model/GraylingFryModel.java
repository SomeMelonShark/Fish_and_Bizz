package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.GraylingFryEntity;
import net.redmelon.fishandshiz.entity.custom.MilkfishFryEntity;
import software.bernie.geckolib.model.GeoModel;

public class GraylingFryModel extends GeoModel<GraylingFryEntity> {
    @Override
    public Identifier getModelResource(GraylingFryEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/grayling_fry.geo.json");
    }

    @Override
    public Identifier getTextureResource(GraylingFryEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/grayling_fry.png");
    }

    @Override
    public Identifier getAnimationResource(GraylingFryEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/fry.animation.json");
    }
}

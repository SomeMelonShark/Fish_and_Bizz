package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.AngelfishFryEntity;
import software.bernie.geckolib.model.GeoModel;

public class AngelfishFryModel extends GeoModel<AngelfishFryEntity> {
    @Override
    public Identifier getModelResource(AngelfishFryEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/angelfish_fry.geo.json");
    }

    @Override
    public Identifier getTextureResource(AngelfishFryEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/angelfish_fry.png");
    }

    @Override
    public Identifier getAnimationResource(AngelfishFryEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/fry.animation.json");
    }
}

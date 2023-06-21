package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.RainbowfishFryEntity;
import software.bernie.geckolib.model.GeoModel;

public class RainbowfishFryModel extends GeoModel<RainbowfishFryEntity> {
    @Override
    public Identifier getModelResource(RainbowfishFryEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/rainbowfish_fry.geo.json");
    }

    @Override
    public Identifier getTextureResource(RainbowfishFryEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/rainbowfish_fry.png");
    }

    @Override
    public Identifier getAnimationResource(RainbowfishFryEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/fry.animation.json");
    }
}
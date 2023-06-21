package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.RainbowfishEntity;
import software.bernie.geckolib.model.GeoModel;

public class RainbowfishModel extends GeoModel<RainbowfishEntity> {
    @Override
    public Identifier getModelResource(RainbowfishEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/rainbowfish.geo.json");
    }

    @Override
    public Identifier getTextureResource(RainbowfishEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/rainbowfish.png");
    }

    @Override
    public Identifier getAnimationResource(RainbowfishEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/rainbowfish.animation.json");
    }
}

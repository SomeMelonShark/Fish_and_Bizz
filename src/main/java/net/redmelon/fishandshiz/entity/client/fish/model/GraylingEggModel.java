package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.GraylingEggEntity;
import net.redmelon.fishandshiz.entity.custom.MilkfishEggEntity;
import software.bernie.geckolib.model.GeoModel;

public class GraylingEggModel extends GeoModel<GraylingEggEntity> {
    @Override
    public Identifier getModelResource(GraylingEggEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/salmon_egg.geo.json");
    }
    @Override
    public Identifier getTextureResource(GraylingEggEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/grayling_egg.png");
    }

    @Override
    public Identifier getAnimationResource(GraylingEggEntity animatable) {
        return null;
    }
}

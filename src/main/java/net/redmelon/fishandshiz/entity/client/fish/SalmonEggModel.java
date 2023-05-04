package net.redmelon.fishandshiz.entity.client.fish;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.SalmonEggEntity;
import software.bernie.geckolib.model.GeoModel;

public class SalmonEggModel extends GeoModel<SalmonEggEntity> {
    @Override
    public Identifier getModelResource(SalmonEggEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/salmon_egg.geo.json");
    }
    @Override
    public Identifier getTextureResource(SalmonEggEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/salmon_egg.png");
    }

    @Override
    public Identifier getAnimationResource(SalmonEggEntity animatable) {
        return null;
    }
}

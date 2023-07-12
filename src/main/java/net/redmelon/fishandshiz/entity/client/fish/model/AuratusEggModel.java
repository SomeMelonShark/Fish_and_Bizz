package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.AuratusEggEntity;
import software.bernie.geckolib.model.GeoModel;

public class AuratusEggModel extends GeoModel<AuratusEggEntity> {
    @Override
    public Identifier getModelResource(AuratusEggEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/angelfish_egg.geo.json");
    }
    @Override
    public Identifier getTextureResource(AuratusEggEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/angelfish_egg.png");
    }

    @Override
    public Identifier getAnimationResource(AuratusEggEntity animatable) {
        return null;
    }
}
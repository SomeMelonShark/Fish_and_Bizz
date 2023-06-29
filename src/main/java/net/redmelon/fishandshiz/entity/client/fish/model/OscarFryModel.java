package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.OscarFryEntity;
import software.bernie.geckolib.model.GeoModel;

public class OscarFryModel extends GeoModel<OscarFryEntity> {
    @Override
    public Identifier getModelResource(OscarFryEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/oscar_fry.geo.json");
    }

    @Override
    public Identifier getTextureResource(OscarFryEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/oscar_fry.png");
    }

    @Override
    public Identifier getAnimationResource(OscarFryEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/fry.animation.json");
    }
}
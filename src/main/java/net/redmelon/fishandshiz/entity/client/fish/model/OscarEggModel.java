package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.OscarEggEntity;
import software.bernie.geckolib.model.GeoModel;

public class OscarEggModel extends GeoModel<OscarEggEntity> {
    @Override
    public Identifier getModelResource(OscarEggEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/angelfish_egg.geo.json");
    }
    @Override
    public Identifier getTextureResource(OscarEggEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/oscar_egg.png");
    }

    @Override
    public Identifier getAnimationResource(OscarEggEntity animatable) {
        return null;
    }
}

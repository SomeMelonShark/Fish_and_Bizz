package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.CorydorasEntity;
import software.bernie.geckolib.model.GeoModel;

public class CorydorasModel extends GeoModel<CorydorasEntity> {
    @Override
    public Identifier getModelResource(CorydorasEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/corydoras.geo.json");
    }

    @Override
    public Identifier getTextureResource(CorydorasEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/corydoras1.png");
    }

    @Override
    public Identifier getAnimationResource(CorydorasEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/corydoras.animation.json");
    }
}

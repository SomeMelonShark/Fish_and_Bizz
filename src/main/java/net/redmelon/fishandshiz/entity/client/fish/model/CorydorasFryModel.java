package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.CorydorasFryEntity;
import software.bernie.geckolib.model.GeoModel;

public class CorydorasFryModel extends GeoModel<CorydorasFryEntity> {
    @Override
    public Identifier getModelResource(CorydorasFryEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/corydoras_fry.geo.json");
    }

    @Override
    public Identifier getTextureResource(CorydorasFryEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/corydoras_fry.png");
    }

    @Override
    public Identifier getAnimationResource(CorydorasFryEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/fry.animation.json");
    }
}
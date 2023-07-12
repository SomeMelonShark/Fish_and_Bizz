package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.AuratusFryEntity;
import software.bernie.geckolib.model.GeoModel;

public class AuratusFryModel extends GeoModel<AuratusFryEntity> {
    @Override
    public Identifier getModelResource(AuratusFryEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/auratus_fry.geo.json");
    }

    @Override
    public Identifier getTextureResource(AuratusFryEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/auratus_fry.png");
    }

    @Override
    public Identifier getAnimationResource(AuratusFryEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/fry.animation.json");
    }
}
package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.VolcanoSnailEntity;
import software.bernie.geckolib.model.GeoModel;

public class VolcanoSnailModel extends GeoModel<VolcanoSnailEntity> {
    @Override
    public Identifier getModelResource(VolcanoSnailEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/volcano_snail.geo.json");
    }

    @Override
    public Identifier getTextureResource(VolcanoSnailEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/volcano_snail.png");
    }

    @Override
    public Identifier getAnimationResource(VolcanoSnailEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/volcano_snail.animation.json");
    }
}

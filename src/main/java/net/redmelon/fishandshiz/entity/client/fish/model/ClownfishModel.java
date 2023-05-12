package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.ClownfishEntity;
import net.redmelon.fishandshiz.entity.custom.NeonTetraEntity;
import software.bernie.geckolib.model.GeoModel;

public class ClownfishModel extends GeoModel<ClownfishEntity> {
    @Override
    public Identifier getModelResource(ClownfishEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/clownfish.geo.json");
    }

    @Override
    public Identifier getTextureResource(ClownfishEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/clownfish.png");
    }

    @Override
    public Identifier getAnimationResource(ClownfishEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/clownfish.animation.json");
    }
}

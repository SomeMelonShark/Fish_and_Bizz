package net.redmelon.fishandshiz.entity.client;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.ManeJellyfishEntity;
import software.bernie.geckolib.model.GeoModel;

public class ManeJellyfishModel extends GeoModel<ManeJellyfishEntity> {
    @Override
    public Identifier getModelResource(ManeJellyfishEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/lion_mane.geo.json");
    }

    @Override
    public Identifier getTextureResource(ManeJellyfishEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/lion_mane.png");
    }

    @Override
    public Identifier getAnimationResource(ManeJellyfishEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/lion_mane.animation.json");
    }
}

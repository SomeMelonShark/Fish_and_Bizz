package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.ClownfishFryEntity;
import software.bernie.geckolib.model.GeoModel;

public class ClownfishFryModel extends GeoModel<ClownfishFryEntity> {
    @Override
    public Identifier getModelResource(ClownfishFryEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/clownfish_fry.geo.json");
    }

    @Override
    public Identifier getTextureResource(ClownfishFryEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/clownfish_fry.png");
    }

    @Override
    public Identifier getAnimationResource(ClownfishFryEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/clownfish_fry.animation.json");
    }
}

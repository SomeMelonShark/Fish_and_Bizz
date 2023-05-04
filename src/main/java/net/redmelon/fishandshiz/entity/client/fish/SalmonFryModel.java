package net.redmelon.fishandshiz.entity.client.fish;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.SalmonFryEntity;
import software.bernie.geckolib.model.GeoModel;

public class SalmonFryModel extends GeoModel<SalmonFryEntity> {
    @Override
    public Identifier getModelResource(SalmonFryEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/salmon_fry.geo.json");
    }

    @Override
    public Identifier getTextureResource(SalmonFryEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/salmon_fry.png");
    }

    @Override
    public Identifier getAnimationResource(SalmonFryEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/salmon_fry.animation.json");
    }
}

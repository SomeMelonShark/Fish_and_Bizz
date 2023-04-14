package net.redmelon.fishandshiz.entity.client.fish;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.AngelfishEggEntity;
import software.bernie.geckolib.model.GeoModel;

public class AngelfishEggModel extends GeoModel<AngelfishEggEntity> {
    @Override
    public Identifier getModelResource(AngelfishEggEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/angelfish_egg.geo.json");
    }
    @Override
    public Identifier getTextureResource(AngelfishEggEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/angelfish_egg.png");
    }

    @Override
    public Identifier getAnimationResource(AngelfishEggEntity animatable) {
        return null;
    }

}

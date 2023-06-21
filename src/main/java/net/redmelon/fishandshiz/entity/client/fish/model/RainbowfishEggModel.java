package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.NeonTetraEggEntity;
import net.redmelon.fishandshiz.entity.custom.RainbowfishEggEntity;
import software.bernie.geckolib.model.GeoModel;

public class RainbowfishEggModel extends GeoModel<RainbowfishEggEntity> {
    @Override
    public Identifier getModelResource(RainbowfishEggEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/corydoras_egg.geo.json");
    }
    @Override
    public Identifier getTextureResource(RainbowfishEggEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/rainbowfish_egg.png");
    }

    @Override
    public Identifier getAnimationResource(RainbowfishEggEntity animatable) {
        return null;
    }
}

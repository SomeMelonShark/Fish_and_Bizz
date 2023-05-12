package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.CorydorasEggEntity;
import net.redmelon.fishandshiz.entity.custom.MilkfishEggEntity;
import software.bernie.geckolib.model.GeoModel;

public class CorydorasEggModel extends GeoModel<CorydorasEggEntity> {
    @Override
    public Identifier getModelResource(CorydorasEggEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/corydoras_egg.geo.json");
    }
    @Override
    public Identifier getTextureResource(CorydorasEggEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/corydoras_egg.png");
    }

    @Override
    public Identifier getAnimationResource(CorydorasEggEntity animatable) {
        return null;
    }
}

package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.fish.ArcherfishSpitEntity;
import software.bernie.geckolib.model.GeoModel;

public class ArcherfishSpitModel extends GeoModel<ArcherfishSpitEntity> {
    @Override
    public Identifier getModelResource(ArcherfishSpitEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/archerfish_spit.geo.json");
    }

    @Override
    public Identifier getTextureResource(ArcherfishSpitEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/fish/archerfish_spit.png");
    }

    @Override
    public Identifier getAnimationResource(ArcherfishSpitEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/archerfish_spit.animation.json");
    }
}

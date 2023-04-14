package net.redmelon.fishandshiz.entity.client.fish;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.ArcherfishEntity;
import software.bernie.geckolib.model.GeoModel;

public class ArcherfishModel extends GeoModel<ArcherfishEntity> {
    @Override
    public Identifier getModelResource(ArcherfishEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/archerfish.geo.json");
    }

    @Override
    public Identifier getTextureResource(ArcherfishEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/archerfish.png");
    }

    @Override
    public Identifier getAnimationResource(ArcherfishEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/archerfish.animation.json");
    }
}

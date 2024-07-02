package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.renderer.ArcherfishRenderer;
import net.redmelon.fishandshiz.entity.custom.fish.ArcherfishEntity;
import software.bernie.geckolib.model.GeoModel;

public class ArcherfishModel extends GeoModel<ArcherfishEntity> {
    @Override
    public Identifier getModelResource(ArcherfishEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/archerfish.geo.json");
    }

    @Override
    public Identifier getTextureResource(ArcherfishEntity animatable) {
        return ArcherfishRenderer.LOCATION_BY_VARIANT.get(animatable.getVariant());
    }

    @Override
    public Identifier getAnimationResource(ArcherfishEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/mediumfish.animation.json");
    }
}

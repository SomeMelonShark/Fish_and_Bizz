package net.redmelon.fishandshiz.entity.client.model;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.renderer.RamshornSnailRenderer;
import net.redmelon.fishandshiz.entity.custom.RamshornSnailEntity;
import software.bernie.geckolib.model.GeoModel;

public class RamshornSnailModel extends GeoModel<RamshornSnailEntity> {
    @Override
    public Identifier getModelResource(RamshornSnailEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/ramshorn_snail.geo.json");
    }

    @Override
    public Identifier getTextureResource(RamshornSnailEntity animatable) {
        return RamshornSnailRenderer.LOCATION_BY_VARIANT.get(animatable.getVariantId());
    }

    @Override
    public Identifier getAnimationResource(RamshornSnailEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/ramshorn_snail.animation.json");
    }
}

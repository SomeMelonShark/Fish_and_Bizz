package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.renderer.BettaRenderer;
import net.redmelon.fishandshiz.entity.custom.BettaEntity;
import software.bernie.geckolib.model.GeoModel;

public class BettaModel extends GeoModel<BettaEntity> {
    @Override
    public Identifier getModelResource(BettaEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/betta.geo.json");
    }

    @Override
    public Identifier getTextureResource(BettaEntity animatable) {
        return BettaRenderer.LOCATION_BY_VARIANT.get(animatable.getVariant());
    }

    @Override
    public Identifier getAnimationResource(BettaEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/betta.animation.json");
    }
}

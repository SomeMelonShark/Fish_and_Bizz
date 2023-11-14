package net.redmelon.fishandshiz.block.entity.client;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.block.entity.SeaAnemoneBlockEntity;
import net.redmelon.fishandshiz.entity.custom.CapybaraEntity;
import software.bernie.geckolib.model.GeoModel;

public class SeaAnemoneModel extends GeoModel<SeaAnemoneBlockEntity> {

    @Override
    public Identifier getModelResource(SeaAnemoneBlockEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/sea_anemone.geo.json");
    }

    @Override
    public Identifier getTextureResource(SeaAnemoneBlockEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/block/sea_anemone.png");
    }

    @Override
    public Identifier getAnimationResource(SeaAnemoneBlockEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/sea_anemone.animation.json");
    }
}

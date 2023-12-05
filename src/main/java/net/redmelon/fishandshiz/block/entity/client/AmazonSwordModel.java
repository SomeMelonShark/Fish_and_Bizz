package net.redmelon.fishandshiz.block.entity.client;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.block.entity.AmazonSwordBlockEntity;
import software.bernie.geckolib.model.GeoModel;

public class AmazonSwordModel extends GeoModel<AmazonSwordBlockEntity> {

    @Override
    public Identifier getModelResource(AmazonSwordBlockEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/amazon_sword.geo.json");
    }

    @Override
    public Identifier getTextureResource(AmazonSwordBlockEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/block/amazon_sword.png");
    }

    @Override
    public Identifier getAnimationResource(AmazonSwordBlockEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/amazon_sword.animation.json");
    }
}

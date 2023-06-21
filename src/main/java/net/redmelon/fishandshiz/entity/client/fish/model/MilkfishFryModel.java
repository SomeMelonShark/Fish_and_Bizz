package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.MilkfishFryEntity;
import software.bernie.geckolib.model.GeoModel;

public class MilkfishFryModel extends GeoModel<MilkfishFryEntity> {
    @Override
    public Identifier getModelResource(MilkfishFryEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/milkfish_fry.geo.json");
    }

    @Override
    public Identifier getTextureResource(MilkfishFryEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/milkfish_fry.png");
    }

    @Override
    public Identifier getAnimationResource(MilkfishFryEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/fry.animation.json");
    }
}

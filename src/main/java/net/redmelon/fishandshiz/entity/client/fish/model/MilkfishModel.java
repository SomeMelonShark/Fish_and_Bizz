package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.MilkfishEntity;
import software.bernie.geckolib.model.GeoModel;

public class MilkfishModel extends GeoModel<MilkfishEntity> {
    @Override
    public Identifier getModelResource(MilkfishEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/milkfish.geo.json");
    }

    @Override
    public Identifier getTextureResource(MilkfishEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/milkfish.png");
    }

    @Override
    public Identifier getAnimationResource(MilkfishEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/milkfish.animation.json");
    }
}

package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.MilkfishEggEntity;
import software.bernie.geckolib.model.GeoModel;

public class MilkfishEggModel extends GeoModel<MilkfishEggEntity> {
    @Override
    public Identifier getModelResource(MilkfishEggEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/milkfish_egg.geo.json");
    }
    @Override
    public Identifier getTextureResource(MilkfishEggEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/milkfish_egg.png");
    }

    @Override
    public Identifier getAnimationResource(MilkfishEggEntity animatable) {
        return null;
    }
}

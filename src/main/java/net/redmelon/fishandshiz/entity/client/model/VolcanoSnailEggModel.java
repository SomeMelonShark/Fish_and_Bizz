package net.redmelon.fishandshiz.entity.client.model;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.MudCrabEggEntity;
import net.redmelon.fishandshiz.entity.custom.VolcanoSnailEggEntity;
import software.bernie.geckolib.model.GeoModel;

public class VolcanoSnailEggModel extends GeoModel<VolcanoSnailEggEntity> {
    @Override
    public Identifier getModelResource(VolcanoSnailEggEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/volcano_snail_egg.geo.json");
    }
    @Override
    public Identifier getTextureResource(VolcanoSnailEggEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/volcano_snail_egg.png");
    }

    @Override
    public Identifier getAnimationResource(VolcanoSnailEggEntity animatable) {
        return null;
    }
}

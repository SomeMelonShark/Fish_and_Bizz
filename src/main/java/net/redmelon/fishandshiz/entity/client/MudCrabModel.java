package net.redmelon.fishandshiz.entity.client;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.MudCrabEntity;
import software.bernie.geckolib.model.GeoModel;

public class MudCrabModel extends GeoModel<MudCrabEntity> {
    @Override
    public Identifier getModelResource(MudCrabEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/mud_crab.geo.json");
    }

    @Override
    public Identifier getTextureResource(MudCrabEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/mud_crab.png");
    }

    @Override
    public Identifier getAnimationResource(MudCrabEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/mud_crab.animation.json");
    }
}

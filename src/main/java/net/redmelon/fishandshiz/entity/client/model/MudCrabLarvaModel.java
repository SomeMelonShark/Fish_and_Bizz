package net.redmelon.fishandshiz.entity.client.model;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.MudCrabLarvaEntity;
import software.bernie.geckolib.model.GeoModel;

public class MudCrabLarvaModel extends GeoModel<MudCrabLarvaEntity> {
    @Override
    public Identifier getModelResource(MudCrabLarvaEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/mud_crab_larva.geo.json");
    }

    @Override
    public Identifier getTextureResource(MudCrabLarvaEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/mud_crab_larva.png");
    }

    @Override
    public Identifier getAnimationResource(MudCrabLarvaEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/mud_crab_larva.animation.json");
    }
}

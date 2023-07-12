package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.AuratusEntity;
import net.redmelon.fishandshiz.entity.custom.NeonTetraEntity;
import software.bernie.geckolib.model.GeoModel;

public class AuratusModel extends GeoModel<AuratusEntity> {
    @Override
    public Identifier getModelResource(AuratusEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/auratus.geo.json");
    }

    @Override
    public Identifier getTextureResource(AuratusEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/auratus.png");
    }

    @Override
    public Identifier getAnimationResource(AuratusEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/auratus.animation.json");
    }
}

package net.redmelon.fishandshiz.entity.client.fish;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.NeonTetraEntity;
import software.bernie.geckolib.model.GeoModel;

public class NeonTetraModel extends GeoModel<NeonTetraEntity> {
    @Override
    public Identifier getModelResource(NeonTetraEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/neon_tetra.geo.json");
    }

    @Override
    public Identifier getTextureResource(NeonTetraEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/neon_tetra.png");
    }

    @Override
    public Identifier getAnimationResource(NeonTetraEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/neon_tetra.animation.json");
    }
}

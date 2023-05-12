package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.NeonTetraFryEntity;
import software.bernie.geckolib.model.GeoModel;

public class NeonTetraFryModel extends GeoModel<NeonTetraFryEntity> {
    @Override
    public Identifier getModelResource(NeonTetraFryEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/neon_tetra_fry.geo.json");
    }

    @Override
    public Identifier getTextureResource(NeonTetraFryEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/neon_tetra_fry.png");
    }

    @Override
    public Identifier getAnimationResource(NeonTetraFryEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/neon_tetra_fry.animation.json");
    }
}

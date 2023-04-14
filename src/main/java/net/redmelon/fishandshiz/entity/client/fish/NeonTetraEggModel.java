package net.redmelon.fishandshiz.entity.client.fish;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.NeonTetraEggEntity;
import software.bernie.geckolib.model.GeoModel;

public class NeonTetraEggModel extends GeoModel<NeonTetraEggEntity> {
    @Override
    public Identifier getModelResource(NeonTetraEggEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/neon_tetra_egg.geo.json");
    }
    @Override
    public Identifier getTextureResource(NeonTetraEggEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/neon_tetra_egg.png");
    }

    @Override
    public Identifier getAnimationResource(NeonTetraEggEntity animatable) {
        return null;
    }
}

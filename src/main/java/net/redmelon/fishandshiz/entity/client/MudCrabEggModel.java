package net.redmelon.fishandshiz.entity.client;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.MudCrabEggEntity;
import net.redmelon.fishandshiz.entity.custom.NeonTetraEggEntity;
import software.bernie.geckolib.model.GeoModel;

public class MudCrabEggModel extends GeoModel<MudCrabEggEntity> {
    @Override
    public Identifier getModelResource(MudCrabEggEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/mud_crab_egg.geo.json");
    }
    @Override
    public Identifier getTextureResource(MudCrabEggEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/mud_crab_egg.png");
    }

    @Override
    public Identifier getAnimationResource(MudCrabEggEntity animatable) {
        return null;
    }
}

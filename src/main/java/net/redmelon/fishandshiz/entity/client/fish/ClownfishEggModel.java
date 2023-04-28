package net.redmelon.fishandshiz.entity.client.fish;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.ClownfishEggEntity;
import net.redmelon.fishandshiz.entity.custom.NeonTetraEggEntity;
import software.bernie.geckolib.model.GeoModel;

public class ClownfishEggModel extends GeoModel<ClownfishEggEntity> {
    @Override
    public Identifier getModelResource(ClownfishEggEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/clownfish_egg.geo.json");
    }
    @Override
    public Identifier getTextureResource(ClownfishEggEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/clownfish_egg.png");
    }

    @Override
    public Identifier getAnimationResource(ClownfishEggEntity animatable) {
        return null;
    }
}

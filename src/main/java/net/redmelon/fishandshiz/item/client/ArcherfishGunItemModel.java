package net.redmelon.fishandshiz.item.client;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.item.custom.ArcherfishGunItem;
import software.bernie.geckolib.model.GeoModel;

public class ArcherfishGunItemModel extends GeoModel<ArcherfishGunItem> {
    @Override
    public Identifier getModelResource(ArcherfishGunItem animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "geo/archerfish_gun.geo.json");
    }

    @Override
    public Identifier getTextureResource(ArcherfishGunItem animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/item/archerfish_gun.png");
    }

    @Override
    public Identifier getAnimationResource(ArcherfishGunItem animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "animations/archerfish_gun.animation.json");
    }
}

package net.redmelon.fishandshiz.entity.client.model;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.ReplacedSquidEntity;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class ReplacedSquidModel extends DefaultedEntityGeoModel<ReplacedSquidEntity> {
    public ReplacedSquidModel() {
        super(new Identifier(FishAndShiz.MOD_ID, "replaced_squid"));
    }
}

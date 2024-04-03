package net.redmelon.fishandshiz.entity.client.renderer;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.passive.SquidEntity;
import net.redmelon.fishandshiz.entity.client.model.ReplacedSquidModel;
import net.redmelon.fishandshiz.entity.custom.ReplacedSquidEntity;
import software.bernie.geckolib.renderer.GeoReplacedEntityRenderer;

public class ReplacedSquidRenderer extends GeoReplacedEntityRenderer<SquidEntity, ReplacedSquidEntity> {

    public ReplacedSquidRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ReplacedSquidModel(), new ReplacedSquidEntity());
    }
}

package net.redmelon.fishandshiz.block.entity.client;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.redmelon.fishandshiz.block.entity.SeaAnemoneBlockEntity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class SeaAnemoneRenderer extends GeoBlockRenderer<SeaAnemoneBlockEntity> {
    public SeaAnemoneRenderer(BlockEntityRendererFactory.Context context) {
        super(new SeaAnemoneModel());
    }
}

package net.redmelon.fishandshiz.block.entity.client;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.redmelon.fishandshiz.block.entity.AmazonSwordBlockEntity;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class AmazonSwordRenderer extends GeoBlockRenderer<AmazonSwordBlockEntity> {
    public AmazonSwordRenderer(BlockEntityRendererFactory.Context context) {
        super(new AmazonSwordModel());
    }
}

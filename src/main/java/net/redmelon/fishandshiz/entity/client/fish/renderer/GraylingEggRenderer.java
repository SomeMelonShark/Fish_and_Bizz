package net.redmelon.fishandshiz.entity.client.fish.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.model.GraylingEggModel;
import net.redmelon.fishandshiz.entity.custom.GraylingEggEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GraylingEggRenderer extends GeoEntityRenderer<GraylingEggEntity> {
    public GraylingEggRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new GraylingEggModel());
        this.shadowRadius = 1.0f;
    }

    @Override
    public Identifier getTextureLocation(GraylingEggEntity instance) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/grayling_egg.png");
    }

    @Override
    public RenderLayer getRenderType(GraylingEggEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}
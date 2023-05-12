package net.redmelon.fishandshiz.entity.client.fish.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.model.SalmonEggModel;
import net.redmelon.fishandshiz.entity.custom.SalmonEggEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SalmonEggRenderer extends GeoEntityRenderer<SalmonEggEntity> {
    public SalmonEggRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new SalmonEggModel());
        this.shadowRadius= 0.1f;
    }

    @Override
    public Identifier getTextureLocation(SalmonEggEntity instance) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/salmon_egg.png");
    }

    @Override
    public RenderLayer getRenderType(SalmonEggEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}

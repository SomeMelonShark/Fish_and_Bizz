package net.redmelon.fishandshiz.entity.client.fish.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.model.NeonTetraModel;
import net.redmelon.fishandshiz.entity.custom.NeonTetraEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class NeonTetraRenderer extends GeoEntityRenderer<NeonTetraEntity> {
    public NeonTetraRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new NeonTetraModel());
        this.shadowRadius= 0.2f;
    }

    @Override
    public Identifier getTextureLocation(NeonTetraEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/neon_tetra.png");
    }

    @Override
    public RenderLayer getRenderType(NeonTetraEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}

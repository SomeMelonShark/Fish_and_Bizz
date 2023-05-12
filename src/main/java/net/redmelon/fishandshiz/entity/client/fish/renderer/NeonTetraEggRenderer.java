package net.redmelon.fishandshiz.entity.client.fish.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.model.NeonTetraEggModel;
import net.redmelon.fishandshiz.entity.custom.NeonTetraEggEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class NeonTetraEggRenderer extends GeoEntityRenderer<NeonTetraEggEntity> {
    public NeonTetraEggRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new NeonTetraEggModel());
        this.shadowRadius= 0.1f;
    }

    @Override
    public Identifier getTextureLocation(NeonTetraEggEntity instance) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/neon_tetra_egg.png");
    }

    @Override
    public RenderLayer getRenderType(NeonTetraEggEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}

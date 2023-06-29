package net.redmelon.fishandshiz.entity.client.fish.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.model.VolcanoSnailModel;
import net.redmelon.fishandshiz.entity.custom.CapybaraEntity;
import net.redmelon.fishandshiz.entity.custom.VolcanoSnailEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class VolcanoSnailRenderer extends GeoEntityRenderer<VolcanoSnailEntity> {
    public VolcanoSnailRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new VolcanoSnailModel());
        this.shadowRadius = 0.4f;
    }

    @Override
    public Identifier getTextureLocation(VolcanoSnailEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/volcano_snail.png");
    }

    @Override
    public RenderLayer getRenderType(VolcanoSnailEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(VolcanoSnailEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.6f, 0.6f, 0.6f);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

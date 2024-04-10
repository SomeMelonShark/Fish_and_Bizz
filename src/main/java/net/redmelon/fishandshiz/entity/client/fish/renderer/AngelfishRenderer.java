package net.redmelon.fishandshiz.entity.client.fish.renderer;

import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.entity.client.fish.model.BasicFishModel;
import net.redmelon.fishandshiz.entity.custom.fish.AngelfishEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class AngelfishRenderer extends GeoEntityRenderer<AngelfishEntity> {
    public AngelfishRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new BasicFishModel<>("angelfish"));
        this.addRenderLayer(new AngelfishLayerRenderer(this));
        this.shadowRadius = 0.2f;
    }

    static class AngelfishLayerRenderer extends GeoRenderLayer<AngelfishEntity> {

        public AngelfishLayerRenderer(GeoRenderer<AngelfishEntity> entityRendererIn) {
            super(entityRendererIn);
        }

        private void render(MatrixStack poseStack, AngelfishEntity animatable, BakedGeoModel bakedModel, int color, Identifier texture, VertexConsumerProvider bufferSource, int packedLight, int packedOverlay, float partialTick) {
            if(texture != null) {
                float r = ((color >> 16) & 0xff) / 255f;
                float g = ((color >> 8) & 0xff) / 255f;
                float b = (color & 0xff) / 255f;
                int overlay = OverlayTexture.getUv(0,
                        animatable.hurtTime > 0 || animatable.deathTime > 0);

                this.getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, this.getRenderType(texture), bufferSource.getBuffer(this.getRenderType(texture)), partialTick,
                         packedLight, overlay, r, g, b, 1f);
            }
        }

        @Override
        public void render(MatrixStack poseStack, AngelfishEntity animatable, BakedGeoModel bakedModel, RenderLayer renderType,
                           VertexConsumerProvider bufferSource, VertexConsumer buffer, float partialTick,
                           int packedLight, int packedOverlay) {

            this.render(poseStack, animatable, bakedModel, animatable.getBaseColor().color(), this.getTextureResource(animatable), bufferSource, packedLight, packedOverlay, partialTick);
            this.render(poseStack, animatable, bakedModel, animatable.getPatternColor().color(), animatable.getPattern().texture(), bufferSource, packedLight, packedOverlay, partialTick);
            this.render(poseStack, animatable, bakedModel, animatable.getDetailColor().color(), animatable.getDetail().texture(), bufferSource, packedLight, packedOverlay, partialTick);

            if(animatable.isBaby()) {
                poseStack.scale(0.5f, 0.5f, 0.5f);
            } else {
                poseStack.scale(0.8f, 0.8f, 0.8f);
            }
        }

        public RenderLayer getRenderType(Identifier texture) {
            return RenderLayer.getEntityCutoutNoCull(texture);
        }
    }
}

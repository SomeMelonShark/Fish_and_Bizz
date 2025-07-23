package net.redmelon.fishandshiz.entity.client.fish.renderer;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.cclass.AnimalFishEntity;
import net.redmelon.fishandshiz.entity.client.fish.model.BasicFishModel;
import net.redmelon.fishandshiz.entity.custom.fish.VariableFishEntity;
import net.redmelon.fishandshiz.entity.variant.GenericTextureProvider;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class BasicVariableFishRenderer<E extends AnimalFishEntity & GeoAnimatable & VariableFishEntity<P, D>, P extends GenericTextureProvider, D extends GenericTextureProvider> extends GeoEntityRenderer<E> {
    public BasicVariableFishRenderer(EntityRendererFactory.Context renderManager, GeoModel<E> modelProvider) {
        super(renderManager, modelProvider);
        this.addRenderLayer(new VariableFishLayerRenderer<>(this));
        this.shadowRadius = 0.4f;
    }

    public static <E extends AnimalFishEntity & GeoAnimatable & VariableFishEntity<P, D>, P extends GenericTextureProvider, D extends GenericTextureProvider> EntityRendererFactory<E> create(GeoModel<E> model) {
        return ctx -> new BasicVariableFishRenderer<>(ctx, model);
    }

    public static <E extends AnimalFishEntity & GeoAnimatable & VariableFishEntity<P, D>, P extends GenericTextureProvider, D extends GenericTextureProvider> EntityRendererFactory<E> create(String name, String aname) {
        return ctx -> new BasicVariableFishRenderer<>(ctx, new BasicFishModel<>(name, aname));
    }

    @Override
    public RenderLayer getRenderType(E animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(E entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        if (entity.isBaby()) {
            poseStack.scale(0.4f, 0.4f, 0.4f);
        } else if (entity.isMicro()) {
            poseStack.scale(0.3f, 0.3f, 0.3f);
        } else if (entity.isMacro()) {
            poseStack.scale(1.0f, 1.0f, 1.0f);
        } else {
            poseStack.scale(0.7f, 0.7f, 0.7f);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    static class VariableFishLayerRenderer<E extends AnimalFishEntity & GeoAnimatable & VariableFishEntity<P, D>, P extends GenericTextureProvider, D extends GenericTextureProvider> extends GeoRenderLayer<E> {

        public VariableFishLayerRenderer(GeoRenderer<E> entityRendererIn) {
            super(entityRendererIn);
        }

        private void renderLayer(MatrixStack poseStack, E animatable, BakedGeoModel bakedModel, int color, Identifier texture, VertexConsumerProvider bufferSource, int packedLight, int packedOverlay, float partialTick) {
            if (texture != null) {
                float r = ((color >> 16) & 0xff) / 255f;
                float g = ((color >> 8) & 0xff) / 255f;
                float b = (color & 0xff) / 255f;
                int overlay = OverlayTexture.getUv(0, animatable.hurtTime > 0 || animatable.deathTime > 0);

                this.getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, this.getRenderType(texture), bufferSource.getBuffer(this.getRenderType(texture)), partialTick, packedLight, overlay, r, g, b, 1f);
            }
        }

        @Override
        public void render(MatrixStack poseStack, E animatable, BakedGeoModel bakedModel, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
            this.renderLayer(poseStack, animatable, bakedModel, animatable.getBaseColor().color(), this.getTextureResource(animatable), bufferSource, packedLight, packedOverlay, partialTick);
            this.renderLayer(poseStack, animatable, bakedModel, animatable.getPatternColor().color(), animatable.getPattern().texture(), bufferSource, packedLight, packedOverlay, partialTick);
            this.renderLayer(poseStack, animatable, bakedModel, animatable.getDetailColor().color(), animatable.getDetail().texture(), bufferSource, packedLight, packedOverlay, partialTick);

            if (animatable.isBaby()) {
                poseStack.scale(0.4f, 0.4f, 0.4f);
            } else if (animatable.isMicro()) {
                poseStack.scale(0.3f, 0.3f, 0.3f);
            } else {
                poseStack.scale(0.7f, 0.7f, 0.7f);
            }
        }

        public RenderLayer getRenderType(Identifier texture) {
            return RenderLayer.getEntityCutoutNoCull(texture);
        }
    }
}

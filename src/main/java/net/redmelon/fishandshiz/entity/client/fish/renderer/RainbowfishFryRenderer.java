package net.redmelon.fishandshiz.entity.client.fish.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.model.NeonTetraFryModel;
import net.redmelon.fishandshiz.entity.client.fish.model.RainbowfishFryModel;
import net.redmelon.fishandshiz.entity.custom.NeonTetraFryEntity;
import net.redmelon.fishandshiz.entity.custom.RainbowfishFryEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RainbowfishFryRenderer extends GeoEntityRenderer<RainbowfishFryEntity> {
    public RainbowfishFryRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new RainbowfishFryModel());
        this.shadowRadius= 0.2f;
    }

    @Override
    public Identifier getTextureLocation(RainbowfishFryEntity instance) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/rainbowfish_fry.png");
    }
    @Override
    public RenderLayer getRenderType(RainbowfishFryEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(RainbowfishFryEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        poseStack.scale(0.7f, 0.7f, 0.7f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

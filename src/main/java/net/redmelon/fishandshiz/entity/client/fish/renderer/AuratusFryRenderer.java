package net.redmelon.fishandshiz.entity.client.fish.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.model.AuratusFryModel;
import net.redmelon.fishandshiz.entity.client.fish.model.NeonTetraFryModel;
import net.redmelon.fishandshiz.entity.custom.AuratusFryEntity;
import net.redmelon.fishandshiz.entity.custom.NeonTetraFryEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AuratusFryRenderer extends GeoEntityRenderer<AuratusFryEntity> {
    public AuratusFryRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new AuratusFryModel());
        this.shadowRadius= 0.15f;
    }

    @Override
    public Identifier getTextureLocation(AuratusFryEntity instance) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/auratus_fry.png");
    }
    @Override
    public RenderLayer getRenderType(AuratusFryEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(AuratusFryEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        poseStack.scale(0.6f, 0.6f, 0.6f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
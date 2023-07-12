package net.redmelon.fishandshiz.entity.client.fish.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.model.AuratusModel;
import net.redmelon.fishandshiz.entity.client.fish.model.NeonTetraModel;
import net.redmelon.fishandshiz.entity.custom.AuratusEntity;
import net.redmelon.fishandshiz.entity.custom.NeonTetraEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AuratusRenderer extends GeoEntityRenderer<AuratusEntity> {
    public AuratusRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new AuratusModel());
        this.shadowRadius= 0.3f;
    }

    @Override
    public Identifier getTextureLocation(AuratusEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/auratus.png");
    }

    @Override
    public RenderLayer getRenderType(AuratusEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(AuratusEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        poseStack.scale(1.0f, 1.0f, 1.0f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

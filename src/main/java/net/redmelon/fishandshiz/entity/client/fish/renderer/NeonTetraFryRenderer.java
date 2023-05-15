package net.redmelon.fishandshiz.entity.client.fish.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.model.NeonTetraFryModel;
import net.redmelon.fishandshiz.entity.custom.NeonTetraFryEntity;
import net.redmelon.fishandshiz.entity.custom.OscarEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class NeonTetraFryRenderer extends GeoEntityRenderer<NeonTetraFryEntity> {
    public NeonTetraFryRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new NeonTetraFryModel());
        this.shadowRadius= 0.15f;
    }

    @Override
    public Identifier getTextureLocation(NeonTetraFryEntity instance) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/neon_tetra_fry.png");
    }
    @Override
    public RenderLayer getRenderType(NeonTetraFryEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(NeonTetraFryEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        poseStack.scale(0.6f, 0.6f, 0.6f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

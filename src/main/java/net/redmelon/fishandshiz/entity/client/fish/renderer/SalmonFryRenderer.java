package net.redmelon.fishandshiz.entity.client.fish.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.model.SalmonFryModel;
import net.redmelon.fishandshiz.entity.custom.OscarEntity;
import net.redmelon.fishandshiz.entity.custom.SalmonFryEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SalmonFryRenderer extends GeoEntityRenderer<SalmonFryEntity> {
    public SalmonFryRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new SalmonFryModel());
        this.shadowRadius= 0.15f;
    }

    @Override
    public Identifier getTextureLocation(SalmonFryEntity instance) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/salmon_fry.png");
    }
    @Override
    public RenderLayer getRenderType(SalmonFryEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(SalmonFryEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        poseStack.scale(0.6f, 0.6f, 0.6f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

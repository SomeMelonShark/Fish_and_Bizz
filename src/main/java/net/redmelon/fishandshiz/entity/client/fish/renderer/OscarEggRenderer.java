package net.redmelon.fishandshiz.entity.client.fish.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.model.OscarEggModel;
import net.redmelon.fishandshiz.entity.custom.OscarEggEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class OscarEggRenderer extends GeoEntityRenderer<OscarEggEntity> {
    public OscarEggRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new OscarEggModel());
        this.shadowRadius= 0.4f;
    }

    @Override
    public Identifier getTextureLocation(OscarEggEntity instance) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/oscar_egg.png");
    }

    @Override
    public RenderLayer getRenderType(OscarEggEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(OscarEggEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        poseStack.scale(1.2f, 1.2f, 1.2f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
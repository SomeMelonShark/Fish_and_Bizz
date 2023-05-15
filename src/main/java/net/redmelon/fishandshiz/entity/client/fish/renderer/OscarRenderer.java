package net.redmelon.fishandshiz.entity.client.fish.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.model.MilkfishModel;
import net.redmelon.fishandshiz.entity.client.fish.model.OscarModel;
import net.redmelon.fishandshiz.entity.custom.CapybaraEntity;
import net.redmelon.fishandshiz.entity.custom.MilkfishEntity;
import net.redmelon.fishandshiz.entity.custom.OscarEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class OscarRenderer extends GeoEntityRenderer<OscarEntity> {
    public OscarRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new OscarModel());
        this.shadowRadius= 0.6f;
    }

    @Override
    public Identifier getTextureLocation(OscarEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/oscar.png");
    }

    @Override
    public RenderLayer getRenderType(OscarEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(OscarEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        poseStack.scale(0.5f, 0.5f, 0.5f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

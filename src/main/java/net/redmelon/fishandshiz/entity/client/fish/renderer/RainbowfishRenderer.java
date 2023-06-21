package net.redmelon.fishandshiz.entity.client.fish.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.model.OscarModel;
import net.redmelon.fishandshiz.entity.client.fish.model.RainbowfishModel;
import net.redmelon.fishandshiz.entity.custom.OscarEntity;
import net.redmelon.fishandshiz.entity.custom.RainbowfishEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RainbowfishRenderer extends GeoEntityRenderer<RainbowfishEntity> {
    public RainbowfishRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new RainbowfishModel());
        this.shadowRadius= 0.6f;
    }

    @Override
    public Identifier getTextureLocation(RainbowfishEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/rainbowfish.png");
    }

    @Override
    public RenderLayer getRenderType(RainbowfishEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(RainbowfishEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        poseStack.scale(0.7f, 0.7f, 0.7f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

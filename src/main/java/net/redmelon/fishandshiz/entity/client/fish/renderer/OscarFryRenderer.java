package net.redmelon.fishandshiz.entity.client.fish.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.model.OscarFryModel;
import net.redmelon.fishandshiz.entity.custom.OscarFryEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class OscarFryRenderer extends GeoEntityRenderer<OscarFryEntity> {
    public OscarFryRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new OscarFryModel());
        this.shadowRadius= 0.2f;
    }

    @Override
    public Identifier getTextureLocation(OscarFryEntity instance) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/oscar_fry.png");
    }
    @Override
    public RenderLayer getRenderType(OscarFryEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(OscarFryEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        poseStack.scale(0.7f, 0.7f, 0.7f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

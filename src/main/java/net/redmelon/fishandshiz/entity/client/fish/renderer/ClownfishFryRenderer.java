package net.redmelon.fishandshiz.entity.client.fish.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.model.ClownfishFryModel;
import net.redmelon.fishandshiz.entity.custom.ClownfishFryEntity;
import net.redmelon.fishandshiz.entity.custom.OscarEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ClownfishFryRenderer extends GeoEntityRenderer<ClownfishFryEntity> {
    public ClownfishFryRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ClownfishFryModel());
        this.shadowRadius= 0.3f;
    }

    @Override
    public Identifier getTextureLocation(ClownfishFryEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/clownfish_fry.png");
    }

    @Override
    public RenderLayer getRenderType(ClownfishFryEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(ClownfishFryEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        poseStack.scale(0.5f, 0.5f, 0.5f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

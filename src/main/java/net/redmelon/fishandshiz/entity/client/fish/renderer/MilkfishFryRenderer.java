package net.redmelon.fishandshiz.entity.client.fish.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.model.MilkfishFryModel;
import net.redmelon.fishandshiz.entity.custom.MilkfishFryEntity;
import net.redmelon.fishandshiz.entity.custom.OscarEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MilkfishFryRenderer extends GeoEntityRenderer<MilkfishFryEntity> {
    public MilkfishFryRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new MilkfishFryModel());
        this.shadowRadius= 0.15f;
    }

    @Override
    public Identifier getTextureLocation(MilkfishFryEntity instance) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/milkfish_fry.png");
    }
    @Override
    public RenderLayer getRenderType(MilkfishFryEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(MilkfishFryEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        poseStack.scale(0.3f, 0.3f, 0.3f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

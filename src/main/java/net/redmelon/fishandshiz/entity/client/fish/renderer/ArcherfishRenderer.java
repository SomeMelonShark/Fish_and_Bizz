package net.redmelon.fishandshiz.entity.client.fish.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.model.ArcherfishModel;
import net.redmelon.fishandshiz.entity.custom.ArcherfishEntity;
import net.redmelon.fishandshiz.entity.custom.OscarEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ArcherfishRenderer extends GeoEntityRenderer<ArcherfishEntity> {
    public ArcherfishRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ArcherfishModel());
        this.shadowRadius= 0.4f;
    }

    @Override
    public Identifier getTextureLocation(ArcherfishEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/archerfish.png");
    }

    @Override
    public RenderLayer getRenderType(ArcherfishEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(ArcherfishEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        poseStack.scale(0.7f, 0.7f, 0.7f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

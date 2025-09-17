package net.redmelon.fishandshiz.entity.client.fish.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.TropicalFishEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.ModModelLayers;
import net.redmelon.fishandshiz.entity.client.fish.model.ArcherfishModel;
import net.redmelon.fishandshiz.entity.client.fish.model.RedTailCatfishModel;
import net.redmelon.fishandshiz.entity.custom.fish.ArcherfishEntity;
import net.redmelon.fishandshiz.entity.custom.fish.RedTailCatfishEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RedTailCatfishRenderer extends GeoEntityRenderer<RedTailCatfishEntity> {
    public RedTailCatfishRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new RedTailCatfishModel());
        this.shadowRadius= 2.0f;
    }

    @Override
    public Identifier getTextureLocation(RedTailCatfishEntity instance) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/red_tail_catfish/red_tail_catfish.png");
    }

    @Override
    public RenderLayer getRenderType(RedTailCatfishEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(RedTailCatfishEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.3f, 0.3f, 0.3f);
        } else {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

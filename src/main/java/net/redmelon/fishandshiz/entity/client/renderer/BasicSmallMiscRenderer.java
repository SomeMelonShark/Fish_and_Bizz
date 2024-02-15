package net.redmelon.fishandshiz.entity.client.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.entity.client.model.BasicMiscModel;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BasicSmallMiscRenderer<E extends LivingEntity & GeoAnimatable> extends GeoEntityRenderer<E> {

    public BasicSmallMiscRenderer(EntityRendererFactory.Context ctx, GeoModel<E> modelProvider) {
        super(ctx, modelProvider);
    }

    public static <E extends LivingEntity & GeoAnimatable> EntityRendererFactory<E> create(GeoModel<E> model) {
        return ctx -> new BasicSmallMiscRenderer<>(ctx, model);
    }

    public static <E extends LivingEntity & GeoAnimatable> EntityRendererFactory<E> create(String name) {
        return ctx -> new BasicSmallMiscRenderer<>(ctx, new BasicMiscModel<>(name));
    }

    @Override
    public RenderLayer getRenderType(E animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(E entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
        poseStack.scale(0.65f, 0.65f, 0.65f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

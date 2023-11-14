package net.redmelon.fishandshiz.entity.client.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.entity.client.model.BasicMiscModel;
import net.redmelon.fishandshiz.entity.custom.CapybaraEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BasicMiscRenderer <A extends LivingEntity & GeoAnimatable> extends GeoEntityRenderer<A> {

    public BasicMiscRenderer(EntityRendererFactory.Context ctx, GeoModel<A> modelProvider) {
        super(ctx, modelProvider);
    }

    public static <A extends LivingEntity & GeoAnimatable> EntityRendererFactory<A> create(GeoModel<A> model) {
        return ctx -> new BasicMiscRenderer<>(ctx, model);
    }

    public static <A extends LivingEntity & GeoAnimatable> EntityRendererFactory<A> create(String name) {
        return ctx -> new BasicMiscRenderer<>(ctx, new BasicMiscModel<>(name));
    }

    @Override
    public RenderLayer getRenderType(A animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(A entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.4f, 0.4f, 0.4f);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

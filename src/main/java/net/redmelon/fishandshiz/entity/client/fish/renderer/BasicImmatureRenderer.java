package net.redmelon.fishandshiz.entity.client.fish.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.cclass.AnimalFishEntity;
import net.redmelon.fishandshiz.entity.client.fish.model.BasicEggModel;
import net.redmelon.fishandshiz.entity.client.fish.model.BasicFryModel;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BasicImmatureRenderer<A extends AnimalFishEntity & GeoAnimatable> extends GeoEntityRenderer<A> {

    public BasicImmatureRenderer(EntityRendererFactory.Context ctx, GeoModel<A> modelProvider) {
        super(ctx, modelProvider);
    }

    public static <A extends AnimalFishEntity & GeoAnimatable> EntityRendererFactory<A> create(GeoModel<A> model) {
        return ctx -> new BasicImmatureRenderer<>(ctx, model);
    }

    public static <A extends AnimalFishEntity & GeoAnimatable> EntityRendererFactory<A> eggCreate(String gname, String tname) {
        return ctx -> new BasicImmatureRenderer<>(ctx, new BasicEggModel<>(gname, tname));
    }

    public static <A extends AnimalFishEntity & GeoAnimatable> EntityRendererFactory<A> fryCreate(String gname, String tname) {
        return ctx -> new BasicImmatureRenderer<>(ctx, new BasicFryModel<>(gname, tname));
    }

    @Override
    public RenderLayer getRenderType(A animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(A entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        if (entity.isFry()) {
            poseStack.scale(0.55f, 0.55f, 0.55f);
        } else {
        poseStack.scale(1.0f, 1.0f, 1.0f);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

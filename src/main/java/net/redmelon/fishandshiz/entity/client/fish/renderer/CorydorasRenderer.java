package net.redmelon.fishandshiz.entity.client.fish.renderer;

import com.google.common.collect.Maps;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.model.CorydorasModel;
import net.redmelon.fishandshiz.entity.custom.fish.CorydorasEntity;
import net.redmelon.fishandshiz.entity.variant.CorydorasVariant;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

public class CorydorasRenderer extends GeoEntityRenderer<CorydorasEntity> {
    public static final Map<CorydorasVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(CorydorasVariant.class), (corydorasVariantIdentifierEnumMap) -> {
                corydorasVariantIdentifierEnumMap.put(CorydorasVariant.BRONZE,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/corydoras1.png"));
                corydorasVariantIdentifierEnumMap.put(CorydorasVariant.PANDA,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/corydoras2.png"));
                corydorasVariantIdentifierEnumMap.put(CorydorasVariant.ALBINO,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/corydoras3.png"));
            });

    public CorydorasRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new CorydorasModel());
        this.shadowRadius= 0.2f;
    }

    @Override
    public Identifier getTextureLocation(CorydorasEntity instance) {
        return LOCATION_BY_VARIANT.get(instance.getVariant());
    }

    @Override
    public RenderLayer getRenderType(CorydorasEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(CorydorasEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        poseStack.scale(0.4f, 0.4f, 0.4f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

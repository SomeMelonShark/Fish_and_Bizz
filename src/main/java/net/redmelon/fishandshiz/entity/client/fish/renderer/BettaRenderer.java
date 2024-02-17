package net.redmelon.fishandshiz.entity.client.fish.renderer;

import com.google.common.collect.Maps;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.model.AmurCarpModel;
import net.redmelon.fishandshiz.entity.client.fish.model.BettaModel;
import net.redmelon.fishandshiz.entity.custom.AmurCarpEntity;
import net.redmelon.fishandshiz.entity.custom.BettaEntity;
import net.redmelon.fishandshiz.entity.variant.BettaVariant;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

public class BettaRenderer extends GeoEntityRenderer<BettaEntity> {
    public static final Map<BettaVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(BettaVariant.class), (variantIdentifierEnumMap) -> {
                variantIdentifierEnumMap.put(BettaVariant.WILD1,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/bettaw1.png"));
                variantIdentifierEnumMap.put(BettaVariant.WILD2,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/bettaw2.png"));
                variantIdentifierEnumMap.put(BettaVariant.VEIL1,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/bettav1.png"));
                variantIdentifierEnumMap.put(BettaVariant.VEIL2,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/bettav2.png"));
                variantIdentifierEnumMap.put(BettaVariant.FAN1,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/bettaf1.png"));
                variantIdentifierEnumMap.put(BettaVariant.FAN2,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/bettaf2.png"));
            });
    public BettaRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new BettaModel());
        this.shadowRadius= 0.2f;
    }

    @Override
    public Identifier getTextureLocation(BettaEntity instance) {
        return LOCATION_BY_VARIANT.get(instance.getVariant());
    }
    @Override
    public RenderLayer getRenderType(BettaEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(BettaEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        poseStack.scale(1.0f, 1.0f, 1.0f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

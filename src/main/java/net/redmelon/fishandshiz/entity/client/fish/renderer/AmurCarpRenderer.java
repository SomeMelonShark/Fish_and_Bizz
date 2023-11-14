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
import net.redmelon.fishandshiz.entity.client.fish.model.AngelfishModel;
import net.redmelon.fishandshiz.entity.custom.AmurCarpEntity;
import net.redmelon.fishandshiz.entity.custom.AngelfishEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

public class AmurCarpRenderer extends GeoEntityRenderer<AmurCarpEntity> {
    public static final Map<AmurCarpEntity.AmurCarpVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(AmurCarpEntity.AmurCarpVariant.class), (angelfishVariantIdentifierEnumMap) -> {
                angelfishVariantIdentifierEnumMap.put(AmurCarpEntity.AmurCarpVariant.WILD,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/amur_carp1.png"));
                angelfishVariantIdentifierEnumMap.put(AmurCarpEntity.AmurCarpVariant.CREAM1,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/amur_carp2.png"));
                angelfishVariantIdentifierEnumMap.put(AmurCarpEntity.AmurCarpVariant.CREAM2,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/amur_carp3.png"));
                angelfishVariantIdentifierEnumMap.put(AmurCarpEntity.AmurCarpVariant.CREAM3,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/amur_carp4.png"));
                angelfishVariantIdentifierEnumMap.put(AmurCarpEntity.AmurCarpVariant.CREAM4,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/amur_carp5.png"));
            });
    public AmurCarpRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new AmurCarpModel());
        this.shadowRadius= 0.2f;
    }

    @Override
    public Identifier getTextureLocation(AmurCarpEntity instance) {
        return LOCATION_BY_VARIANT.get(instance.getVariant());
    }
    @Override
    public RenderLayer getRenderType(AmurCarpEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(AmurCarpEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        poseStack.scale(1.0f, 1.0f, 1.0f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

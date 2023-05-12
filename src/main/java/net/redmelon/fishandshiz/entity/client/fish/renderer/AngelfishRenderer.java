package net.redmelon.fishandshiz.entity.client.fish.renderer;

import com.google.common.collect.Maps;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.model.AngelfishModel;
import net.redmelon.fishandshiz.entity.custom.AngelfishEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

public class AngelfishRenderer extends GeoEntityRenderer<AngelfishEntity> {
    public static final Map<AngelfishEntity.AngelfishVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(AngelfishEntity.AngelfishVariant.class), (angelfishVariantIdentifierEnumMap) -> {
                angelfishVariantIdentifierEnumMap.put(AngelfishEntity.AngelfishVariant.WILD1,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/wild1.png"));
                angelfishVariantIdentifierEnumMap.put(AngelfishEntity.AngelfishVariant.WILD2,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/wild2.png"));
                angelfishVariantIdentifierEnumMap.put(AngelfishEntity.AngelfishVariant.WILD3,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/wild3.png"));
                angelfishVariantIdentifierEnumMap.put(AngelfishEntity.AngelfishVariant.MARBLE1,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/marble1.png"));
                angelfishVariantIdentifierEnumMap.put(AngelfishEntity.AngelfishVariant.MARBLE2,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/marble2.png"));
                angelfishVariantIdentifierEnumMap.put(AngelfishEntity.AngelfishVariant.PANTS1,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/pants1.png"));
                angelfishVariantIdentifierEnumMap.put(AngelfishEntity.AngelfishVariant.PANTS2,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/pants2.png"));
                angelfishVariantIdentifierEnumMap.put(AngelfishEntity.AngelfishVariant.PANTS3,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/pants3.png"));
                angelfishVariantIdentifierEnumMap.put(AngelfishEntity.AngelfishVariant.PANTS4,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/pants4.png"));
                angelfishVariantIdentifierEnumMap.put(AngelfishEntity.AngelfishVariant.STRIPES1,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/stripes1.png"));
                angelfishVariantIdentifierEnumMap.put(AngelfishEntity.AngelfishVariant.STRIPES2,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/stripes2.png"));
                angelfishVariantIdentifierEnumMap.put(AngelfishEntity.AngelfishVariant.STRIPES3,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/stripes3.png"));
            });
    public AngelfishRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new AngelfishModel());
        this.shadowRadius= 0.2f;
    }

    @Override
    public Identifier getTextureLocation(AngelfishEntity instance) {
        return LOCATION_BY_VARIANT.get(instance.getVariant());
    }
    @Override
    public RenderLayer getRenderType(AngelfishEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
    float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}

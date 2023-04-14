package net.redmelon.fishandshiz.entity.client.fish;

import com.google.common.collect.Maps;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.AngelfishEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

public class AngelfishRenderer extends GeoEntityRenderer<AngelfishEntity> {
    public static final Map<AngelfishEntity.AngelfishVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(AngelfishEntity.AngelfishVariant.class), (angelfishVariantIdentifierEnumMap) -> {
                angelfishVariantIdentifierEnumMap.put(AngelfishEntity.AngelfishVariant.WILD,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/angelfish_wild.png"));
                angelfishVariantIdentifierEnumMap.put(AngelfishEntity.AngelfishVariant.MARBLE,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/angelfish_marble.png"));
                angelfishVariantIdentifierEnumMap.put(AngelfishEntity.AngelfishVariant.PANTS,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/angelfish_pants.png"));
                angelfishVariantIdentifierEnumMap.put(AngelfishEntity.AngelfishVariant.STRIPES,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/angelfish_stripes.png"));
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

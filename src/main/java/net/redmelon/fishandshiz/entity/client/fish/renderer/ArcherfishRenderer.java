package net.redmelon.fishandshiz.entity.client.fish.renderer;

import com.google.common.collect.Maps;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.model.ArcherfishModel;
import net.redmelon.fishandshiz.entity.custom.fish.ArcherfishEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

public class ArcherfishRenderer extends GeoEntityRenderer<ArcherfishEntity> {
    public static final Map<Integer, Identifier> LOCATION_BY_VARIANT = Util.make(Maps.newHashMap(), (map) -> {
        map.put(0, new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/archerfish/archerfish1.png"));
        map.put(1, new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/archerfish/archerfish2.png"));
    });
    public ArcherfishRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ArcherfishModel());
        this.shadowRadius= 0.3f;
    }

    @Override
    public Identifier getTextureLocation(ArcherfishEntity instance) {
        return LOCATION_BY_VARIANT.get(instance.getVariantId());
    }

    @Override
    public RenderLayer getRenderType(ArcherfishEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(ArcherfishEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            poseStack.scale(0.8f, 0.8f, 0.8f);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

package net.redmelon.fishandshiz.entity.client.renderer;

import com.google.common.collect.Maps;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.model.RamshornSnailModel;
import net.redmelon.fishandshiz.entity.custom.RamshornSnailEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

public class RamshornSnailRenderer extends GeoEntityRenderer<RamshornSnailEntity> {
    public static final Map<Integer, Identifier> LOCATION_BY_VARIANT = Util.make(Maps.newHashMap(), (map) -> {
        map.put(0, new Identifier(FishAndShiz.MOD_ID, "textures/entity/ramshorn_snail1.png"));
        map.put(1, new Identifier(FishAndShiz.MOD_ID, "textures/entity/ramshorn_snail2.png"));
    });
    public RamshornSnailRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new RamshornSnailModel());
        this.shadowRadius= 0.2f;
    }

    @Override
    public void render(RamshornSnailEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
        poseStack.scale(0.65f, 0.65f, 0.65f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

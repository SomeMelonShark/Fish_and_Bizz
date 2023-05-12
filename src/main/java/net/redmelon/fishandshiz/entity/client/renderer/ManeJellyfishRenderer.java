package net.redmelon.fishandshiz.entity.client.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.model.ManeJellyfishModel;
import net.redmelon.fishandshiz.entity.custom.ManeJellyfishEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ManeJellyfishRenderer extends GeoEntityRenderer<ManeJellyfishEntity> {
    public ManeJellyfishRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ManeJellyfishModel());
        this.shadowRadius= 1.0f;
    }

    @Override
    public Identifier getTextureLocation(ManeJellyfishEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/lion_mane.png");
    }

    @Override
    public RenderLayer getRenderType(ManeJellyfishEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}

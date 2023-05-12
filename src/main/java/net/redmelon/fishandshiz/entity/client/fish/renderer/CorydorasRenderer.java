package net.redmelon.fishandshiz.entity.client.fish.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.model.CorydorasModel;
import net.redmelon.fishandshiz.entity.custom.CorydorasEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CorydorasRenderer extends GeoEntityRenderer<CorydorasEntity> {
    public CorydorasRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new CorydorasModel());
        this.shadowRadius= 0.2f;
    }

    @Override
    public Identifier getTextureLocation(CorydorasEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/corydoras1.png");
    }

    @Override
    public RenderLayer getRenderType(CorydorasEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}

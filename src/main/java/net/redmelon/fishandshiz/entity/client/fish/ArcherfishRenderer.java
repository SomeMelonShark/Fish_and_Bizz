package net.redmelon.fishandshiz.entity.client.fish;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.ArcherfishEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ArcherfishRenderer extends GeoEntityRenderer<ArcherfishEntity> {
    public ArcherfishRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ArcherfishModel());
        this.shadowRadius= 0.4f;
    }

    @Override
    public Identifier getTextureLocation(ArcherfishEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/archerfish.png");
    }

    @Override
    public RenderLayer getRenderType(ArcherfishEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}

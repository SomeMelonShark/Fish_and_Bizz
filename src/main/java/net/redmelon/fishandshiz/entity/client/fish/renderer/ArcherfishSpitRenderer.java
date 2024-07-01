package net.redmelon.fishandshiz.entity.client.fish.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.model.ArcherfishSpitModel;
import net.redmelon.fishandshiz.entity.custom.fish.ArcherfishSpitEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ArcherfishSpitRenderer extends GeoEntityRenderer<ArcherfishSpitEntity> {
    public ArcherfishSpitRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ArcherfishSpitModel());
    }

    @Override
    public Identifier getTextureLocation(ArcherfishSpitEntity instance) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/fish/archerfish/archerfish_spit.png");
    }

    @Override
    public RenderLayer getRenderType(ArcherfishSpitEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}

package net.redmelon.fishandshiz.entity.client.fish.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.model.AngelfishEggModel;
import net.redmelon.fishandshiz.entity.custom.AngelfishEggEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AngelfishEggRenderer extends GeoEntityRenderer<AngelfishEggEntity> {

    public AngelfishEggRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new AngelfishEggModel());
        this.shadowRadius= 0.1f;
    }

    @Override
    public Identifier getTextureLocation(AngelfishEggEntity instance) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/angelfish_egg.png");
    }

    @Override
    public RenderLayer getRenderType(AngelfishEggEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}

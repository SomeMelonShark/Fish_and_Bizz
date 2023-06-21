package net.redmelon.fishandshiz.entity.client.fish.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.model.RainbowfishEggModel;
import net.redmelon.fishandshiz.entity.custom.RainbowfishEggEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RainbowfishEggRenderer extends GeoEntityRenderer<RainbowfishEggEntity> {
    public RainbowfishEggRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new RainbowfishEggModel());
        this.shadowRadius= 0.4f;
    }

    @Override
    public Identifier getTextureLocation(RainbowfishEggEntity instance) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/rainbowfish_egg.png");
    }

    @Override
    public RenderLayer getRenderType(RainbowfishEggEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}

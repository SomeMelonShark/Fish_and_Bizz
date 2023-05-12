package net.redmelon.fishandshiz.entity.client.fish.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.model.CorydorasEggModel;
import net.redmelon.fishandshiz.entity.client.fish.model.MilkfishEggModel;
import net.redmelon.fishandshiz.entity.custom.CorydorasEggEntity;
import net.redmelon.fishandshiz.entity.custom.MilkfishEggEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CorydorasEggRenderer extends GeoEntityRenderer<CorydorasEggEntity> {
    public CorydorasEggRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new CorydorasEggModel());
        this.shadowRadius= 0.1f;
    }

    @Override
    public Identifier getTextureLocation(CorydorasEggEntity instance) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/corydoras_egg.png");
    }

    @Override
    public RenderLayer getRenderType(CorydorasEggEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}

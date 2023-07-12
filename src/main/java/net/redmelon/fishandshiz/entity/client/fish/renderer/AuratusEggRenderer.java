package net.redmelon.fishandshiz.entity.client.fish.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.model.AuratusEggModel;
import net.redmelon.fishandshiz.entity.custom.AuratusEggEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AuratusEggRenderer extends GeoEntityRenderer<AuratusEggEntity> {
    public AuratusEggRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new AuratusEggModel());
        this.shadowRadius= 0.2f;
    }

    @Override
    public Identifier getTextureLocation(AuratusEggEntity instance) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/angelfish_egg.png");
    }

    @Override
    public RenderLayer getRenderType(AuratusEggEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}

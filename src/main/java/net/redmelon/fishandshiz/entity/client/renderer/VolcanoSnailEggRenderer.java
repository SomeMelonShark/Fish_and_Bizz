package net.redmelon.fishandshiz.entity.client.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.model.MudCrabEggModel;
import net.redmelon.fishandshiz.entity.client.model.VolcanoSnailEggModel;
import net.redmelon.fishandshiz.entity.custom.MudCrabEggEntity;
import net.redmelon.fishandshiz.entity.custom.VolcanoSnailEggEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class VolcanoSnailEggRenderer extends GeoEntityRenderer<VolcanoSnailEggEntity> {
    public VolcanoSnailEggRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new VolcanoSnailEggModel());
        this.shadowRadius= 0.1f;
    }

    @Override
    public Identifier getTextureLocation(VolcanoSnailEggEntity instance) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/volcano_snail_egg.png");
    }

    @Override
    public RenderLayer getRenderType(VolcanoSnailEggEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}


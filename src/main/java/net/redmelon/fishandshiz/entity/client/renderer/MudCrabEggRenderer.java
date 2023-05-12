package net.redmelon.fishandshiz.entity.client.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.model.MudCrabEggModel;
import net.redmelon.fishandshiz.entity.custom.MudCrabEggEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MudCrabEggRenderer extends GeoEntityRenderer<MudCrabEggEntity> {
    public MudCrabEggRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new MudCrabEggModel());
        this.shadowRadius= 0.1f;
    }

    @Override
    public Identifier getTextureLocation(MudCrabEggEntity instance) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/mud_crab_egg.png");
    }

    @Override
    public RenderLayer getRenderType(MudCrabEggEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}

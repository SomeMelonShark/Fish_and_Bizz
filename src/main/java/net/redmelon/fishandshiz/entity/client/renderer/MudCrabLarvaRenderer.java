package net.redmelon.fishandshiz.entity.client.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.model.MudCrabLarvaModel;
import net.redmelon.fishandshiz.entity.custom.MudCrabLarvaEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MudCrabLarvaRenderer extends GeoEntityRenderer<MudCrabLarvaEntity> {
    public MudCrabLarvaRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new MudCrabLarvaModel());
        this.shadowRadius= 0.1f;
    }

    @Override
    public Identifier getTextureLocation(MudCrabLarvaEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/mud_crab_larva.png");
    }

    @Override
    public RenderLayer getRenderType(MudCrabLarvaEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}

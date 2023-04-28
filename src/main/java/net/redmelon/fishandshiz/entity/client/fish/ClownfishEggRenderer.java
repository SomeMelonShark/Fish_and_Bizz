package net.redmelon.fishandshiz.entity.client.fish;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.ClownfishEggEntity;
import net.redmelon.fishandshiz.entity.custom.NeonTetraEggEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ClownfishEggRenderer extends GeoEntityRenderer<ClownfishEggEntity> {
    public ClownfishEggRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ClownfishEggModel());
        this.shadowRadius= 0.1f;
    }

    @Override
    public Identifier getTextureLocation(ClownfishEggEntity instance) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/clownfish_egg.png");
    }

    @Override
    public RenderLayer getRenderType(ClownfishEggEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}

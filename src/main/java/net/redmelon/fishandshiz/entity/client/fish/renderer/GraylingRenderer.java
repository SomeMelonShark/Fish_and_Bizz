package net.redmelon.fishandshiz.entity.client.fish.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.model.GraylingModel;
import net.redmelon.fishandshiz.entity.client.fish.model.MilkfishModel;
import net.redmelon.fishandshiz.entity.custom.GraylingEntity;
import net.redmelon.fishandshiz.entity.custom.MilkfishEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GraylingRenderer extends GeoEntityRenderer<GraylingEntity> {
    public GraylingRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new GraylingModel());
        this.shadowRadius= 0.5f;
    }

    @Override
    public Identifier getTextureLocation(GraylingEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/grayling.png");
    }

    @Override
    public RenderLayer getRenderType(GraylingEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}

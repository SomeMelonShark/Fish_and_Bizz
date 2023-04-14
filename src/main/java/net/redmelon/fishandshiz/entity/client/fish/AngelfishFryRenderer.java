package net.redmelon.fishandshiz.entity.client.fish;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.AngelfishFryEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AngelfishFryRenderer extends GeoEntityRenderer<AngelfishFryEntity> {
    public AngelfishFryRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new AngelfishFryModel());
        this.shadowRadius= 0.1f;
    }

    @Override
    public Identifier getTextureLocation(AngelfishFryEntity instance) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/angelfish_fry.png");
    }
    @Override
    public RenderLayer getRenderType(AngelfishFryEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}

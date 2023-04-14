package net.redmelon.fishandshiz.entity.client.fish;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.MilkfishFryEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MilkfishFryRenderer extends GeoEntityRenderer<MilkfishFryEntity> {
    public MilkfishFryRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new MilkfishFryModel());
        this.shadowRadius= 0.15f;
    }

    @Override
    public Identifier getTextureLocation(MilkfishFryEntity instance) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/milkfish_fry.png");
    }
    @Override
    public RenderLayer getRenderType(MilkfishFryEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}

package net.redmelon.fishandshiz.entity.client.fish.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.model.MilkfishEggModel;
import net.redmelon.fishandshiz.entity.custom.MilkfishEggEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MilkfishEggRenderer extends GeoEntityRenderer<MilkfishEggEntity> {
    public MilkfishEggRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new MilkfishEggModel());
        this.shadowRadius= 0.1f;
    }

    @Override
    public Identifier getTextureLocation(MilkfishEggEntity instance) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/milkfish_egg.png");
    }

    @Override
    public RenderLayer getRenderType(MilkfishEggEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}

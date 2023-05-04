package net.redmelon.fishandshiz.entity.client;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.MilkfishModel;
import net.redmelon.fishandshiz.entity.custom.CapybaraEntity;
import net.redmelon.fishandshiz.entity.custom.MilkfishEntity;
import net.redmelon.fishandshiz.entity.custom.MudCrabEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MudCrabRenderer extends GeoEntityRenderer<MudCrabEntity> {
    public MudCrabRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new MudCrabModel());
        this.shadowRadius= 0.5f;
    }

    @Override
    public Identifier getTextureLocation(MudCrabEntity animatable) {
        return new Identifier(FishAndShiz.MOD_ID, "textures/entity/mud_crab.png");
    }

    @Override
    public RenderLayer getRenderType(MudCrabEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}

package net.redmelon.fishandshiz.entity.client.fish.renderer;

import com.google.common.collect.Maps;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.fish.model.AngelfishModel;
import net.redmelon.fishandshiz.entity.custom.AngelfishEntity;
import net.redmelon.fishandshiz.entity.variant.AngelfishVariant;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

public class AngelfishRenderer extends GeoEntityRenderer<AngelfishEntity> {
    public static final Map<AngelfishVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(AngelfishVariant.class), (variantIdentifierEnumMap) -> {
                variantIdentifierEnumMap.put(AngelfishVariant.WILD1,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/angelfishw1.png"));
                variantIdentifierEnumMap.put(AngelfishVariant.WILD2,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/angelfishw2.png"));
                variantIdentifierEnumMap.put(AngelfishVariant.WILD3,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/angelfishw3.png"));
                variantIdentifierEnumMap.put(AngelfishVariant.MARBLE1,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/angelfishm1.png"));
                variantIdentifierEnumMap.put(AngelfishVariant.MARBLE2,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/angelfishm2.png"));
                variantIdentifierEnumMap.put(AngelfishVariant.PANTS1,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/angelfishp1.png"));
                variantIdentifierEnumMap.put(AngelfishVariant.PANTS2,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/angelfishp2.png"));
                variantIdentifierEnumMap.put(AngelfishVariant.PANTS3,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/angelfishp3.png"));
                variantIdentifierEnumMap.put(AngelfishVariant.PANTS4,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/angelfishp4.png"));
                variantIdentifierEnumMap.put(AngelfishVariant.STRIPES1,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/angelfishs1.png"));
                variantIdentifierEnumMap.put(AngelfishVariant.STRIPES2,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/angelfishs2.png"));
                variantIdentifierEnumMap.put(AngelfishVariant.STRIPES3,
                        new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/angelfishs3.png"));
            });
    public AngelfishRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new AngelfishModel());
        this.shadowRadius= 0.2f;
    }

    @Override
    public Identifier getTextureLocation(AngelfishEntity instance) {
        return LOCATION_BY_VARIANT.get(instance.getVariant());
    }
    @Override
    public RenderLayer getRenderType(AngelfishEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource,
    float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(AngelfishEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        poseStack.scale(0.7f, 0.7f, 0.7f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

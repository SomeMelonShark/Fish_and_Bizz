package net.redmelon.fishandshiz.entity.client.fish.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.TropicalFishEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.client.ModModelLayers;
import net.redmelon.fishandshiz.entity.client.fish.model.RedTailCatfishModel;
import net.redmelon.fishandshiz.entity.custom.fish.RedTailCatfishEntity;

public class RedTailCatfishRenderer extends MobEntityRenderer<RedTailCatfishEntity, RedTailCatfishModel<RedTailCatfishEntity>> {
    private static final Identifier TEXTURE = new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/red_tail_catfish/red_tail_catfish.png");
    public RedTailCatfishRenderer(EntityRendererFactory.Context context) {
        super(context, new RedTailCatfishModel<>(context.getPart(ModModelLayers.RED_TAIL_CATFISH)), 1.5f);
    }

    @Override
    public Identifier getTexture(RedTailCatfishEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(RedTailCatfishEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.4f, 0.4f, 0.4f);
        } else {
            poseStack.scale(0.6f, 0.6f, 0.6f);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

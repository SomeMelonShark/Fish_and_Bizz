package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import static net.redmelon.fishandshiz.FishAndShiz.MOD_ID;

public class BasicFishModel<E extends LivingEntity & GeoAnimatable> extends GeoModel<E> {

    private final Identifier model;
    private final Identifier texture;
    private final Identifier animation;
    private final @Nullable String root;

    public BasicFishModel(Identifier model, Identifier texture, Identifier animation, @Nullable String root) {
        this.model = model;
        this.texture = texture;
        this.animation = animation;
        this.root = root;
    }

    public BasicFishModel(String name, @Nullable String root) {
        this(
                new Identifier(MOD_ID,"geo/" + name + ".geo.json"),
                new Identifier(MOD_ID, "textures/entity/fish/" + name + ".png"),
                new Identifier(MOD_ID, "animations/" + name + ".animation.json"), root);

    }

    public BasicFishModel(String name) {
        this(name, null);
    }

    @Override
    public Identifier getModelResource(E entity) {
        return model;
    }

    @Override
    public Identifier getTextureResource(E entity) {
        return texture;
    }
    @Override
    public Identifier getAnimationResource(E entity) {
        return animation;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setCustomAnimations(E animatable, long instanceId, AnimationState<E> animationState) {
        if (root != null) {
            CoreGeoBone rootBone = this.getAnimationProcessor().getBone("root");
            EntityModelData entityModelData = (EntityModelData) animationState.getExtraData().get(0);
            if (rootBone != null) {;
                rootBone.setRotX(entityModelData.headPitch() * ((float) Math.PI / 180F));
                rootBone.setRotY(entityModelData.netHeadYaw() * ((float) Math.PI / 180F));
            }
        }
    }
}

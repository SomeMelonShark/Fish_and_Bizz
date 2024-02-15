package net.redmelon.fishandshiz.entity.client.model;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import static net.redmelon.fishandshiz.FishAndShiz.MOD_ID;

public class BasicMiscModel <E extends LivingEntity & GeoAnimatable> extends GeoModel<E> {

    private final Identifier model;
    private final Identifier texture;
    private final Identifier animation;
    private final @Nullable String head;

    public BasicMiscModel(Identifier model, Identifier texture, Identifier animation, @Nullable String head) {
        this.model = model;
        this.texture = texture;
        this.animation = animation;
        this.head = head;
    }

    public BasicMiscModel(String name, @Nullable String head) {
        this(
                new Identifier(MOD_ID,"geo/" + name + ".geo.json"),
                new Identifier(MOD_ID, "textures/entity/" + name + ".png"),
                new Identifier(MOD_ID, "animations/" + name + ".animation.json"), head);

    }

    public BasicMiscModel(String name) {
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
        if (head != null) {
            CoreGeoBone headBone = this.getAnimationProcessor().getBone("head");
            EntityModelData entityModelData = (EntityModelData) animationState.getExtraData().get(0);
            if (headBone != null) {;
                headBone.setRotX(entityModelData.headPitch() * ((float) Math.PI / 180F));
                headBone.setRotY(entityModelData.netHeadYaw() * ((float) Math.PI / 180F));
            }
        }
    }

}

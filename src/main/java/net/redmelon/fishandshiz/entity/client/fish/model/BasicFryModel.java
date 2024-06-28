package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

import static net.redmelon.fishandshiz.FishAndShiz.MOD_ID;

public class BasicFryModel<A extends LivingEntity & GeoAnimatable> extends GeoModel<A> {

    private final Identifier model;
    private final Identifier texture;
    private final Identifier animation;
    private final @Nullable String head;

    public BasicFryModel(Identifier model, Identifier texture, Identifier animation, @Nullable String head) {
        this.model = model;
        this.texture = texture;
        this.animation = animation;
        this.head = head;
    }

    public BasicFryModel(String name, @Nullable String head) {
        this(
                new Identifier(MOD_ID,"geo/" + name + "_fry.geo.json"),
                new Identifier(MOD_ID, "textures/entity/fish/" + name + "/" + name + "_fry.png"),
                new Identifier(MOD_ID, "animations/fry.animation.json"), head);

    }

    public BasicFryModel(String name) {
        this(name, null);
    }

    @Override
    public Identifier getModelResource(A entity) {
        return model;
    }

    @Override
    public Identifier getTextureResource(A entity) {
        return texture;
    }
    @Override
    public Identifier getAnimationResource(A entity) {
        return animation;
    }
}

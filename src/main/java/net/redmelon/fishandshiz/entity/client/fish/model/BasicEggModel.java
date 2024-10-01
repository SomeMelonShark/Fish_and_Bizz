package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

import static net.redmelon.fishandshiz.FishAndShiz.MOD_ID;

public class BasicEggModel<A extends LivingEntity & GeoAnimatable> extends GeoModel<A> {

    private final Identifier model;
    private final Identifier texture;
    private final Identifier animation;
    private final @Nullable String head;

    public BasicEggModel(Identifier model, Identifier texture, Identifier animation, @Nullable String head) {
        this.model = model;
        this.texture = texture;
        this.animation = animation;
        this.head = head;
    }

    public BasicEggModel(String gname, String tname, @Nullable String head) {
        this(
                new Identifier(MOD_ID,"geo/" + gname + "_egg.geo.json"),
                new Identifier(MOD_ID, "textures/entity/fish/" + tname + "/" + tname + "_egg.png"),
                new Identifier(MOD_ID, "animations/egg.animation.json"), head);

    }

    public BasicEggModel(String gname, String tname) {
        this(gname, tname, null);
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

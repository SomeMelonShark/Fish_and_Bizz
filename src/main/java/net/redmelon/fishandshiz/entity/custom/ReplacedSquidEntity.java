package net.redmelon.fishandshiz.entity.custom;

import net.minecraft.entity.EntityType;
import software.bernie.geckolib.animatable.GeoReplacedEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ReplacedSquidEntity implements GeoReplacedEntity {
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    @Override
    public EntityType<?> getReplacingEntityType() {
        return EntityType.SQUID;
    }

    private PlayState genericFlopController(AnimationState animationState) {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.replaced_squid.swim", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController(this, "controller", 5, this::genericFlopController));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }
}

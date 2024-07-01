package net.redmelon.fishandshiz.item.custom;

import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.redmelon.fishandshiz.entity.custom.fish.ArcherfishSpitEntity;
import net.redmelon.fishandshiz.item.client.ArcherfishGunItemRenderer;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ArcherfishGunItem extends StoredRangedWeaponItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
    public ArcherfishGunItem(Settings settings) {
        super(settings);
    }

    private PlayState controller(AnimationState animationState) {
        if (this.isLoaded() && this.loadCount > 3) {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.archerfish_gun.idle_full", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else if (this.isLoaded() && this.loadCount < 3){
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.archerfish_gun.idle_half", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.archerfish_gun.idle_empty", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
    }

    @Override
    public int getRange() {
        return 15;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (this.isLoaded() && world.isClient) {
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.AMBIENT_UNDERWATER_ENTER, SoundCategory.NEUTRAL, 1f, 1.5f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
            ArcherfishSpitEntity archerfishSpitEntity = new ArcherfishSpitEntity(world, user);
            archerfishSpitEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 2f, 1.0f);
            this.setLoadCount(loadCount + 1);
            user.setCurrentHand(hand);
            world.spawnEntity(archerfishSpitEntity);
            return TypedActionResult.consume(itemStack);
        }
        return TypedActionResult.fail(itemStack);
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private ArcherfishGunItemRenderer renderer;
            @Override
            public BuiltinModelItemRenderer getCustomRenderer() {
                if (this.renderer != null) {
                    this.renderer = new ArcherfishGunItemRenderer();
                }
                return this.renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController(this, "controller", 5, this::controller));

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}

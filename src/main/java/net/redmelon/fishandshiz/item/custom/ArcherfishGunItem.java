package net.redmelon.fishandshiz.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.redmelon.fishandshiz.entity.custom.fish.ArcherfishSpitEntity;
import net.redmelon.fishandshiz.item.client.ArcherfishGunItemRenderer;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ArcherfishGunItem extends StoredRangedWeaponItem implements GeoItem{
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
    public ArcherfishGunItem(Settings settings) {
        super(settings);
    }

    private PlayState controller(AnimationState animationState) {
        if (this.loadCount >= 11) {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.archerfish_gun.idle_full", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else if (this.loadCount < 11 && this.loadCount > 0) {
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
        return 30;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        int cooldownPeriod = 10;

        NbtCompound tag = itemStack.getOrCreateNbt();
        long lastUseTime = tag.getLong("LastUseTime");
        long currentTime = world.getTime();

        if (currentTime - lastUseTime < cooldownPeriod) {
            return TypedActionResult.fail(itemStack);
        }

        tag.putLong("LastUseTime", currentTime);
        itemStack.setNbt(tag);

        BlockPos pos = user.getBlockPos();
        FluidState fluidState = world.getFluidState(pos);

        if (this.isLoaded(itemStack)) {
            if (!world.isClient) {
                ArcherfishSpitEntity archerfishSpitEntity = new ArcherfishSpitEntity(world, user);
                archerfishSpitEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 3f, 0f);
                world.spawnEntity(archerfishSpitEntity);
                this.setLoadCount(itemStack, getLoadCount(itemStack) - 1);
            }

            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.AMBIENT_UNDERWATER_ENTER, SoundCategory.PLAYERS, 2f, 1.5f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
            this.setLoaded(itemStack, getLoadCount(itemStack) > 0);
            user.setCurrentHand(hand);

            return TypedActionResult.pass(itemStack);
        } else if (getLoadCount(itemStack) == 0) {
            if (fluidState.isIn(FluidTags.WATER) && fluidState.getLevel() == 8){
                this.setLoadCount(itemStack, LOAD_COUNT);
                this.setLoaded(itemStack, getLoadCount(itemStack) > 0);
                world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BUCKET_FILL, SoundCategory.PLAYERS, 0.5f, 1.5f);
                return TypedActionResult.success(itemStack);
            }
            for (int i = 0; i < user.getInventory().size(); i++) {
                ItemStack stack = user.getInventory().getStack(i);

                if (stack.getItem() == Items.POTION &&
                        PotionUtil.getPotion(stack) == Potions.WATER) {

                    stack.decrement(1);

                    ItemStack empty = new ItemStack(Items.GLASS_BOTTLE);
                    if (!user.getInventory().insertStack(empty)) {
                        user.dropItem(empty, false);
                    }

                    this.setLoadCount(itemStack, LOAD_COUNT);
                    this.setLoaded(itemStack, true);

                    world.playSound(null, user.getX(), user.getY(), user.getZ(),
                            SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.PLAYERS,
                            0.5f, 1.5f);

                    return TypedActionResult.success(itemStack);
                }
            }
        }

        return TypedActionResult.fail(itemStack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        Formatting[] formattings = new Formatting[]{Formatting.BOLD, Formatting.AQUA};
        String loadCount = "Shots: " + getLoadCount(stack);
        MutableText mutableText = Text.translatable(loadCount);
        mutableText.formatted(formattings);
        tooltip.add(mutableText);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 10;
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final ArcherfishGunItemRenderer renderer = new ArcherfishGunItemRenderer();
            @Override
            public BuiltinModelItemRenderer getCustomRenderer() {
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

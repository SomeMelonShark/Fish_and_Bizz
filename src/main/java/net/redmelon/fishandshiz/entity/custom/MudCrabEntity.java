package net.redmelon.fishandshiz.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.redmelon.fishandshiz.cclass.AnimalFishEntity;
import net.redmelon.fishandshiz.cclass.AnimalWaterEntity;
import net.redmelon.fishandshiz.cclass.PassiveWaterEntity;
import net.redmelon.fishandshiz.cclass.cmethods.goals.BreedWaterAnimalMateGoal;
import net.redmelon.fishandshiz.entity.ModEntities;
import net.redmelon.fishandshiz.item.ModItems;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MudCrabEntity extends AnimalWaterEntity implements GeoEntity {
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public MudCrabEntity(EntityType<? extends AnimalWaterEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalFishEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 8)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f);
    }


    protected void initGoals() {
        this.goalSelector.add(1, new EscapeDangerGoal(this, 2.0));
        this.goalSelector.add(2, new BreedWaterAnimalMateGoal(this, 1.0));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(4, new SwimAroundGoal(this, 1.0, 10));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(6, new LookAtEntityGoal(this, MudCrabEntity.class, 6.0f));
    }

    @Override
    protected void tickWaterBreathingAir(int air) {
        if (this.isAlive() && !this.isInsideWaterOrBubbleColumn()) {
            this.setAir(air - 1);
            if (this.getAir() == -20) {
                this.setAir(0);
            }
        } else {
            this.setAir(300);
        }

    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_GUARDIAN_FLOP;
    }

    private PlayState genericFlopController(AnimationState animationState) {
        if (animationState.isMoving() && this.isOnGround()) {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.mud_crab.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else if (!animationState.isMoving() && this.isOnGround()) {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.mud_crab.idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else if (this.isTouchingWater()){
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.mud_crab.swim", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.mud_crab.idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.COD;
    }

    @Override
    public @Nullable PassiveWaterEntity createChild(ServerWorld var1, PassiveWaterEntity var2) {
        return ModEntities.MUD_CRAB_EGG.create(world);
    }
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_TURTLE_EGG_BREAK;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_CHICKEN_STEP;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_TURTLE_EGG_CRACK;
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
       controllers.add(new AnimationController<>(this, "controller", 2, this::genericFlopController));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(ModItems.MUD_CRAB_BUCKET);
    }
}

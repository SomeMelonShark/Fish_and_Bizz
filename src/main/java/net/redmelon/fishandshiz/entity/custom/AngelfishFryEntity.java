package net.redmelon.fishandshiz.entity.custom;

import com.google.common.annotations.VisibleForTesting;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.SwimAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.redmelon.fishandshiz.cclass.AnimalFishEntity;
import net.redmelon.fishandshiz.cclass.PassiveWaterEntity;
import net.redmelon.fishandshiz.cclass.cmethods.BreedFollowGroupLeaderGoal;
import net.redmelon.fishandshiz.entity.ModEntities;
import net.redmelon.fishandshiz.item.ModItems;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class AngelfishFryEntity extends AngelfishEntity implements GeoEntity {
    @VisibleForTesting
    public static int MAX_ANGELFISH_FRY_AGE = Math.abs(-18000);
    public static float WIDTH = 0.3f;
    public static float HEIGHT = 0.2f;
    private int angelfishFryAge;
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public AngelfishFryEntity(EntityType<? extends AngelfishEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalFishEntity.createFishAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1);
    }
    private PlayState genericFlopController(AnimationState animationState) {
        if (this.isTouchingWater()) {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.angelfish_fry.swim", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.angelfish_fry.flop", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(2, new FleeEntityGoal<PlayerEntity>(this, PlayerEntity.class, 8.0f, 1.6, 1.4, EntityPredicates.EXCEPT_SPECTATOR::test));
        this.goalSelector.add(4, new SwimAroundGoal(this, 1.0, 10));
        this.goalSelector.add(4, new BreedFollowGroupLeaderGoal(this));
    }
    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.world.isClient) {
            this.setAngelfishFryAge(this.angelfishFryAge + 1);
        }
    }
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Age", this.angelfishFryAge);
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setAngelfishFryAge(nbt.getInt("Age"));
    }
    @Override
    public void copyDataToStack(ItemStack stack) {
        Bucketable.copyDataToStack(this, stack);
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        nbtCompound.putInt("Age", this.getAngelfishFryAge());
    }
    @Override
    public void copyDataFromNbt(NbtCompound nbt) {
        Bucketable.copyDataFromNbt(this, nbt);
        if (nbt.contains("Age")) {
            this.setAngelfishFryAge(nbt.getInt("Age"));
        }
    }
    private boolean isFishFood(ItemStack stack) {
        return AngelfishEntity.FISH_FOOD.test(stack);
    }
    private void eatFishFood (PlayerEntity player, ItemStack stack) {
        this.decrementItem(player, stack);
        this.increaseAge(PassiveWaterEntity.toGrowUpAge(this.getTicksUntilGrowth()));
        this.world.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), 0.0, 0.0, 0.0);
    }

    private void decrementItem(PlayerEntity player, ItemStack stack) {
        if (!player.getAbilities().creativeMode) {
            stack.decrement(1);
        }
    }

    private int getAngelfishFryAge() {
        return this.angelfishFryAge;
    }
    private void increaseAge(int seconds) {
        this.setAngelfishFryAge(this.angelfishFryAge + seconds * 20);
    }
    private void setAngelfishFryAge(int angelfishFryAge) {
        this.angelfishFryAge = angelfishFryAge;
        if (this.angelfishFryAge >= MAX_ANGELFISH_FRY_AGE) {
            this.growUp();
        }
    }
    private void growUp() {
        World world = this.world;
        if (world instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld)world;
            AngelfishEntity angelfishEntity = ModEntities.ANGELFISH.create(this.world);
            if (angelfishEntity != null) {
                angelfishEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                angelfishEntity.initialize(serverWorld, this.world.getLocalDifficulty(angelfishEntity.getBlockPos()), SpawnReason.CONVERSION, null, null);
                angelfishEntity.setAiDisabled(this.isAiDisabled());
                if (this.hasCustomName()) {
                    angelfishEntity.setCustomName(this.getCustomName());
                    angelfishEntity.setCustomNameVisible(this.isCustomNameVisible());
                }
                angelfishEntity.setPersistent();
                this.playSound(SoundEvents.ENTITY_TROPICAL_FISH_FLOP, 0.15f, 1.0f);
                serverWorld.spawnEntityAndPassengers(angelfishEntity);
                this.discard();
            }
        }
    }

    private int getTicksUntilGrowth() {
        return Math.max(0, MAX_ANGELFISH_FRY_AGE - this.angelfishFryAge);
    }

    @Override
    public boolean shouldDropXp() {
        return false;
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_TROPICAL_FISH_FLOP;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_TROPICAL_FISH_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_TROPICAL_FISH_DEATH;
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(ModItems.ANGELFISH_FRY_BUCKET);
    }

    @Override
    public void writeCustomDatatoNbt(NbtCompound nbt) {

    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_TROPICAL_FISH_HURT;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController(this, "controller", 5, this::genericFlopController));
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }
}

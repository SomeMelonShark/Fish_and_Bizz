package net.redmelon.fishandshiz.entity.custom;

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
import net.redmelon.fishandshiz.cclass.cmethods.goals.BreedFollowGroupLeaderGoal;
import net.redmelon.fishandshiz.entity.ModEntities;
import net.redmelon.fishandshiz.item.ModItems;
import org.jetbrains.annotations.VisibleForTesting;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MilkfishFryEntity extends MilkfishEntity implements GeoEntity {
    @VisibleForTesting
    public static int MAX_MILKFISH_FRY_AGE = Math.abs(-24000);
    public static float WIDTH = 0.5f;
    public static float HEIGHT = 0.3f;
    private int milkfishFryAge;
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public MilkfishFryEntity(EntityType<? extends MilkfishEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalFishEntity.createFishAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1);
    }

    private PlayState genericFlopController(AnimationState animationState) {
        if (this.isTouchingWater()) {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.milkfish_fry.swim", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.milkfish_fry.flop", Animation.LoopType.LOOP));
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
            this.setMilkfishFryAge(this.milkfishFryAge + 1);
        }
    }
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Age", this.milkfishFryAge);
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setMilkfishFryAge(nbt.getInt("Age"));
    }
    @Override
    public void copyDataToStack(ItemStack stack) {
        Bucketable.copyDataToStack(this, stack);
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        nbtCompound.putInt("Age", this.getMilkfishFryAge());
    }
    @Override
    public void copyDataFromNbt(NbtCompound nbt) {
        Bucketable.copyDataFromNbt(this, nbt);
        if (nbt.contains("Age")) {
            this.setMilkfishFryAge(nbt.getInt("Age"));
        }
    }
    private boolean isFishFood(ItemStack stack) {
        return MilkfishEntity.FISH_FOOD.test(stack);
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

    private int getMilkfishFryAge() {
        return this.milkfishFryAge;
    }
    private void increaseAge(int seconds) {
        this.setMilkfishFryAge(this.milkfishFryAge + seconds * 20);
    }
    private void setMilkfishFryAge(int milkfishFryAge) {
        this.milkfishFryAge = milkfishFryAge;
        if (this.milkfishFryAge >= MAX_MILKFISH_FRY_AGE) {
            this.growUp();
        }
    }
    private void growUp() {
        World world = this.world;
        if (world instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld)world;
            MilkfishEntity milkfishEntity = ModEntities.MILKFISH.create(this.world);
            if (milkfishEntity != null) {
                milkfishEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                milkfishEntity.initialize(serverWorld, this.world.getLocalDifficulty(milkfishEntity.getBlockPos()), SpawnReason.CONVERSION, null, null);
                milkfishEntity.setAiDisabled(this.isAiDisabled());
                if (this.hasCustomName()) {
                    milkfishEntity.setCustomName(this.getCustomName());
                    milkfishEntity.setCustomNameVisible(this.isCustomNameVisible());
                }
                milkfishEntity.setPersistent();
                this.playSound(SoundEvents.ENTITY_COD_FLOP, 0.15f, 1.0f);
                serverWorld.spawnEntityAndPassengers(milkfishEntity);
                this.discard();
            }
        }
    }

    private int getTicksUntilGrowth() {
        return Math.max(0, MAX_MILKFISH_FRY_AGE - this.milkfishFryAge);
    }

    @Override
    public boolean shouldDropXp() {
        return false;
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_COD_FLOP;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SALMON_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_COD_DEATH;
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(ModItems.MILKFISH_FRY_BUCKET);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_COD_HURT;
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

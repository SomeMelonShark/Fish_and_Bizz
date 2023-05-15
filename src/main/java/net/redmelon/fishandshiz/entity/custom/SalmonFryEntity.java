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
import net.minecraft.entity.passive.SalmonEntity;
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
import net.redmelon.fishandshiz.cclass.SchoolingBreedEntity;
import net.redmelon.fishandshiz.cclass.cmethods.goals.BreedFollowGroupLeaderGoal;
import net.redmelon.fishandshiz.item.ModItems;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SalmonFryEntity extends SchoolingBreedEntity implements GeoEntity {
    @VisibleForTesting
    public static int MAX_SALMON_FRY_AGE = Math.abs(-24000);
    public static float WIDTH = 0.5f;
    public static float HEIGHT = 0.3f;
    private int salmonFryAge;
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public SalmonFryEntity(EntityType<? extends SchoolingBreedEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalFishEntity.createFishAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1);
    }

    private PlayState genericFlopController(AnimationState animationState) {
        if (this.isTouchingWater()) {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.salmon_fry.swim", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.salmon_fry.flop", Animation.LoopType.LOOP));
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
            this.setSalmonFryAge(this.salmonFryAge + 1);
        }
    }

    @Override
    public @Nullable PassiveWaterEntity createChild(ServerWorld var1, PassiveWaterEntity var2) {
        return null;
    }

    @Override
    public void writeCustomDatatoNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Age", this.salmonFryAge);
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setSalmonFryAge(nbt.getInt("Age"));
    }
    @Override
    public void copyDataToStack(ItemStack stack) {
        Bucketable.copyDataToStack(this, stack);
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        nbtCompound.putInt("Age", this.getSalmonFryAge());
    }
    @Override
    public void copyDataFromNbt(NbtCompound nbt) {
        Bucketable.copyDataFromNbt(this, nbt);
        if (nbt.contains("Age")) {
            this.setSalmonFryAge(nbt.getInt("Age"));
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

    private int getSalmonFryAge() {
        return this.salmonFryAge;
    }
    private void increaseAge(int seconds) {
        this.setSalmonFryAge(this.salmonFryAge + seconds * 20);
    }
    private void setSalmonFryAge(int salmonFryAge) {
        this.salmonFryAge = salmonFryAge;
        if (this.salmonFryAge >= MAX_SALMON_FRY_AGE) {
            this.growUp();
        }
    }

    private void growUp() {
        World world = this.world;
        if (world instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld)world;
            SalmonEntity salmonEntity = EntityType.SALMON.create(this.world);
            if (salmonEntity != null) {
                salmonEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                salmonEntity.initialize(serverWorld, this.world.getLocalDifficulty(salmonEntity.getBlockPos()), SpawnReason.CONVERSION, null, null);
                salmonEntity.setAiDisabled(this.isAiDisabled());
                if (this.hasCustomName()) {
                    salmonEntity.setCustomName(this.getCustomName());
                    salmonEntity.setCustomNameVisible(this.isCustomNameVisible());
                }
                salmonEntity.setPersistent();
                this.playSound(SoundEvents.ENTITY_SALMON_FLOP, 0.15f, 1.0f);
                serverWorld.spawnEntityAndPassengers(salmonEntity);
                this.discard();
            }
        }
    }

    private int getTicksUntilGrowth() {
        return Math.max(0, MAX_SALMON_FRY_AGE - this.salmonFryAge);
    }

    @Override
    public boolean shouldDropXp() {
        return false;
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_SALMON_FLOP;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SALMON_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SALMON_DEATH;
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(ModItems.SALMON_FRY_BUCKET);
    }


    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SALMON_HURT;
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

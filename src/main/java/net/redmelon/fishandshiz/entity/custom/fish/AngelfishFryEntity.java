package net.redmelon.fishandshiz.entity.custom.fish;

import com.google.common.annotations.VisibleForTesting;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.EntityData;
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
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.redmelon.fishandshiz.cclass.AnimalFishEntity;
import net.redmelon.fishandshiz.cclass.PassiveWaterEntity;
import net.redmelon.fishandshiz.cclass.cmethods.goals.BreedFollowGroupLeaderGoal;
import net.redmelon.fishandshiz.entity.ModEntities;
import net.redmelon.fishandshiz.entity.tags.TropicalSpawn;
import net.redmelon.fishandshiz.entity.variant.AngelfishVariant;
import net.redmelon.fishandshiz.item.ModItems;
import net.redmelon.fishandshiz.world.biome.ModBiomes;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class AngelfishFryEntity extends AngelfishEntity implements GeoEntity {
    @VisibleForTesting
    public static int MAX_AGE = Math.abs(-18000);
    public static float WIDTH = 0.4f;
    public static float HEIGHT = 0.3f;
    protected int stageAge;
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
                    .then("animation.fry.swim", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.fry.flop", Animation.LoopType.LOOP));
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
        if (!this.getWorld().isClient) {
            this.setStageAge(this.stageAge + 1);
        }
    }
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Age", this.stageAge);
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setStageAge(nbt.getInt("Age"));
    }
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
    }
    @Override
    public void copyDataToStack(ItemStack stack) {
        super.copyDataToStack(stack);
        Bucketable.copyDataToStack(this, stack);
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        nbtCompound.putInt("Age", this.getStageAge());
    }
    @Override
    public void copyDataFromNbt(NbtCompound nbt) {
        Bucketable.copyDataFromNbt(this, nbt);
        if (nbt.contains("Age")) {
            this.setStageAge(nbt.getInt("Age"));
        }
    }
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (this.isFishFood(itemStack)) {
            this.eatFishFood(player, itemStack);
            return ActionResult.success(this.getWorld().isClient);
        } else {
            return (ActionResult)Bucketable.tryBucket(player, hand, this).orElse(super.interactMob(player, hand));
        }
    }
    private boolean isFishFood(ItemStack stack) {
        return AngelfishEntity.FISH_FOOD.test(stack);
    }
    private void eatFishFood (PlayerEntity player, ItemStack stack) {
        this.decrementItem(player, stack);
        this.increaseAge(PassiveWaterEntity.toGrowUpAge(this.getTicksUntilGrowth()));
        this.getWorld().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), 0.0, 0.0, 0.0);
    }

    private void decrementItem(PlayerEntity player, ItemStack stack) {
        if (!player.getAbilities().creativeMode) {
            stack.decrement(1);
        }
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                 @Nullable EntityData entityData, @Nullable NbtCompound entityNbt){
        RegistryEntry<Biome> registryEntry = world.getBiome(this.getBlockPos());
        AngelfishVariant variant;

        if (spawnReason == SpawnReason.BUCKET && entityNbt != null && entityNbt.contains(BUCKET_VARIANT_TAG_KEY, NbtElement.INT_TYPE)) {
            this.setAngelfishVariant(entityNbt.getInt(BUCKET_VARIANT_TAG_KEY));
            return entityData;
        }

        if (spawnReason == SpawnReason.NATURAL) {
            if (registryEntry.matchesKey(BiomeKeys.RIVER)) {
                variant = (AngelfishVariant.WILD1);
            } else if (registryEntry.isIn(TropicalSpawn.SPAWNS_TROPICAL)) {
                variant = (AngelfishVariant.WILD1);
            } else if (registryEntry.matchesKey(BiomeKeys.SPARSE_JUNGLE)) {
                variant = (AngelfishVariant.WILD1);
            } else if (registryEntry.matchesKey(BiomeKeys.JUNGLE)) {
                variant = (AngelfishVariant.WILD1);
            } else if (registryEntry.matchesKey(ModBiomes.JUNGLE_BASIN)) {
                variant = (AngelfishVariant.WILD1);
            } else {
                variant = Util.getRandom(AngelfishVariant.values(), this.random);
            }
        } else {
            variant = Util.getRandom(AngelfishVariant.values(), this.random);
        }

        setVariant(variant);
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        this.setAngelfishVariant(variant.getId());
        return entityData;
    }

    @Override
    public @Nullable AngelfishEggEntity createChild(ServerWorld var1, PassiveWaterEntity var2) {
        return null;
    }

    private int getStageAge() {
        return this.stageAge;
    }
    private void increaseAge(int seconds) {
        this.setStageAge(this.stageAge + seconds * 20);
    }
    private void setStageAge(int stageAge) {
        this.stageAge = stageAge;
        if (this.stageAge >= MAX_AGE) {
            this.growUp();
        }
    }
    private void growUp() {
        AngelfishVariant variant;
        World world = this.getWorld();
        if (world instanceof ServerWorld) {
            variant = this.getVariant();
            ServerWorld serverWorld = (ServerWorld)world;
            AngelfishEntity nextEntity = ModEntities.ANGELFISH.create(this.getWorld());
            if (nextEntity != null) {
                nextEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                nextEntity.initialize(serverWorld, this.getWorld().getLocalDifficulty(nextEntity.getBlockPos()), SpawnReason.CONVERSION, null, null);
                nextEntity.setAiDisabled(this.isAiDisabled());
                if (this.hasCustomName()) {
                    nextEntity.setCustomName(this.getCustomName());
                    nextEntity.setCustomNameVisible(this.isCustomNameVisible());
                }
                nextEntity.setPersistent();
                nextEntity.setVariant(variant);
                this.playSound(SoundEvents.ENTITY_TROPICAL_FISH_FLOP, 0.15f, 1.0f);
                serverWorld.spawnEntityAndPassengers(nextEntity);
                this.discard();
            }
        }
    }

    private int getTicksUntilGrowth() {
        return Math.max(0, MAX_AGE - this.stageAge);
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

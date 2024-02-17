package net.redmelon.fishandshiz.entity.custom.fish;

import com.google.common.annotations.VisibleForTesting;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.redmelon.fishandshiz.cclass.AnimalFishEntity;
import net.redmelon.fishandshiz.cclass.PassiveWaterEntity;
import net.redmelon.fishandshiz.cclass.cmethods.goals.FloatGoal;
import net.redmelon.fishandshiz.entity.ModEntities;
import net.redmelon.fishandshiz.entity.variant.BettaVariant;
import net.redmelon.fishandshiz.item.ModItems;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BettaEggEntity extends BettaEntity implements GeoEntity {
    @VisibleForTesting
    public static int MAX_EGG_AGE = Math.abs(-6000);
    private int stageAge;

    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    public BettaEggEntity(EntityType<? extends BettaEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalFishEntity.createFishAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.05f);
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.canMoveVoluntarily() && this.isTouchingWater()) {
            this.hasNoGravity();
            this.updateVelocity(0.01f, movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.00001));
        } else {
            this.move(MovementType.SELF, this.getVelocity());
            this.updateVelocity(0.01f, movementInput);
            this.setVelocity(this.getVelocity().add(0.0, -0.01, 0.0));
        }
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new FloatGoal(this));
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
    private int getStageAge() {
        return this.stageAge;
    }
    private void increaseAge(int seconds) {
        this.setStageAge(this.stageAge + seconds * 20);
    }
    private void setStageAge(int stageAge) {
        this.stageAge = stageAge;
        if (this.stageAge >= MAX_EGG_AGE) {
            this.growUp();
        }
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                 @Nullable EntityData entityData, @Nullable NbtCompound entityNbt){
        RegistryEntry<Biome> registryEntry = world.getBiome(this.getBlockPos());
        BettaVariant variant;

        if (spawnReason == SpawnReason.BUCKET && entityNbt != null && entityNbt.contains(BUCKET_VARIANT_TAG_KEY, NbtElement.INT_TYPE)) {
            this.setBettaVariant(entityNbt.getInt(BUCKET_VARIANT_TAG_KEY));
            return entityData;
        }

        if (spawnReason == SpawnReason.NATURAL) {
            if (registryEntry.matchesKey(BiomeKeys.SWAMP)) {
                variant = (BettaVariant.WILD1);
            } else if (registryEntry.matchesKey(BiomeKeys.PLAINS)) {
                variant = (BettaVariant.WILD2);
            } else {
                variant = Util.getRandom(BettaVariant.values(), this.random);
            }
        } else {
            variant = Util.getRandom(BettaVariant.values(), this.random);
        }

        setVariant(variant);
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        this.setBettaVariant(variant.getId());
        return entityData;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    private void growUp() {
        BettaVariant variant;
        World world = this.getWorld();
        int i = random.nextBetweenExclusive(1, 5);
        for (int j = 1; j <= i; ++j)
            if (world instanceof ServerWorld) {
                variant = this.getVariant();
                ServerWorld serverWorld = (ServerWorld)world;
                BettaFryEntity nextEntity = ModEntities.BETTA_FRY.create(this.getWorld());
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
                    this.playSound(SoundEvents.BLOCK_FROGSPAWN_HATCH, 0.15f, 1.0f);
                    serverWorld.spawnEntityAndPassengers(nextEntity);
                    this.discard();
                }
            }
    }

    private int getTicksUntilGrowth() {
        return Math.max(0, MAX_EGG_AGE - this.stageAge);
    }

    @Override
    public boolean shouldDropXp() {
        return false;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.BLOCK_FROGSPAWN_HIT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_FROGSPAWN_BREAK;
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.BLOCK_FROGSPAWN_HIT;
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(ModItems.BETTA_EGG_BUCKET);
    }

    @Override
    public @Nullable BettaEggEntity createChild(ServerWorld var1, PassiveWaterEntity var2) {
        return null;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }
}

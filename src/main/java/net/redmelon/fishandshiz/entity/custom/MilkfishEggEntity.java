package net.redmelon.fishandshiz.entity.custom;

import com.google.common.annotations.VisibleForTesting;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.redmelon.fishandshiz.cclass.AnimalFishEntity;
import net.redmelon.fishandshiz.cclass.PassiveWaterEntity;
import net.redmelon.fishandshiz.entity.ModEntities;
import net.redmelon.fishandshiz.item.ModItems;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MilkfishEggEntity extends MilkfishEntity implements GeoEntity {
    @VisibleForTesting
    public static int MAX_MILKFISH_EGG_AGE = Math.abs(-18000);
    private int milkfishEggAge;

    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    public MilkfishEggEntity(EntityType<? extends MilkfishEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new MilkfishEggEntity.FishMoveControl(this);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalFishEntity.createFishAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1);
    }
    static class FishMoveControl
            extends MoveControl {
        private final AnimalFishEntity fish;

        FishMoveControl(AnimalFishEntity owner) {
            super(owner);
            this.fish = owner;
        }

        @Override
        public void tick() {//does not move
        }
    }
    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.world.isClient) {
            this.setMilkfishEggAge(this.milkfishEggAge + 1);
        }
    }
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Age", this.milkfishEggAge);
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setMilkfishEggAge(nbt.getInt("Age"));
    }
    @Override
    public void copyDataToStack(ItemStack stack) {
        Bucketable.copyDataToStack(this, stack);
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        nbtCompound.putInt("Age", this.getMilkfishEggAge());
    }
    @Override
    public void copyDataFromNbt(NbtCompound nbt) {
        Bucketable.copyDataFromNbt(this, nbt);
        if (nbt.contains("Age")) {
            this.setMilkfishEggAge(nbt.getInt("Age"));
        }
    }
    private int getMilkfishEggAge() {
        return this.milkfishEggAge;
    }
    private void increaseAge(int seconds) {
        this.setMilkfishEggAge(this.milkfishEggAge + seconds * 20);
    }
    private void setMilkfishEggAge(int milkfishEggAge) {
        this.milkfishEggAge = milkfishEggAge;
        if (this.milkfishEggAge >= MAX_MILKFISH_EGG_AGE) {
            this.growUp();
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }


    private void growUp() {
        World world = this.world;
        int i = random.nextBetweenExclusive(5, 10);
        for (int j = 1; j <= i; ++j)
            if (world instanceof ServerWorld) {
                ServerWorld serverWorld = (ServerWorld)world;
                MilkfishFryEntity milkfishFryEntity = ModEntities.MILKFISH_FRY.create(this.world);
                if (milkfishFryEntity != null) {
                    milkfishFryEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                    milkfishFryEntity.initialize(serverWorld, this.world.getLocalDifficulty(milkfishFryEntity.getBlockPos()), SpawnReason.CONVERSION, null, null);
                    milkfishFryEntity.setAiDisabled(this.isAiDisabled());
                    if (this.hasCustomName()) {
                        milkfishFryEntity.setCustomName(this.getCustomName());
                        milkfishFryEntity.setCustomNameVisible(this.isCustomNameVisible());
                    }
                    milkfishFryEntity.setPersistent();
                    this.playSound(SoundEvents.BLOCK_FROGSPAWN_HATCH, 0.15f, 1.0f);
                    serverWorld.spawnEntityAndPassengers(milkfishFryEntity);
                    this.discard();
                }
            }
    }

    private int getTicksUntilGrowth() {
        return Math.max(0, MAX_MILKFISH_EGG_AGE - this.milkfishEggAge);
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
        return null;
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(ModItems.MILKFISH_EGG_BUCKET);
    }

    @Override
    public @Nullable MilkfishEggEntity createChild(ServerWorld var1, PassiveWaterEntity var2) {
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

package net.redmelon.fishandshiz.entity.custom;

import com.google.common.annotations.VisibleForTesting;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
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

public class AngelfishEggEntity extends AngelfishEntity implements GeoEntity {
    @VisibleForTesting
    public static int MAX_ANGELFISH_EGG_AGE = Math.abs(-6000);
    private int angelfishEggAge;

    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    public AngelfishEggEntity(EntityType<? extends AngelfishEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new AngelfishEggEntity.FishMoveControl(this);
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
            this.setAngelfishEggAge(this.angelfishEggAge + 1);
        }
    }
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Age", this.angelfishEggAge);
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setAngelfishEggAge(nbt.getInt("Age"));
    }
    @Override
    public void copyDataToStack(ItemStack stack) {
        Bucketable.copyDataToStack(this, stack);
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        nbtCompound.putInt("Age", this.getAngelfishEggAge());
    }
    @Override
    public void copyDataFromNbt(NbtCompound nbt) {
        Bucketable.copyDataFromNbt(this, nbt);
        if (nbt.contains("Age")) {
            this.setAngelfishEggAge(nbt.getInt("Age"));
        }
    }
    private int getAngelfishEggAge() {
        return this.angelfishEggAge;
    }
    private void increaseAge(int seconds) {
        this.setAngelfishEggAge(this.angelfishEggAge + seconds * 20);
    }
    private void setAngelfishEggAge(int angelfishEggAge) {
        this.angelfishEggAge = angelfishEggAge;
        if (this.angelfishEggAge >= MAX_ANGELFISH_EGG_AGE) {
            this.growUp();
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }


    private void growUp() {
        World world = this.world;
        int i = random.nextBetweenExclusive(3, 8);
        for (int j = 1; j <= i; ++j)
        if (world instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld)world;
            AngelfishFryEntity angelfishfryEntity = ModEntities.ANGELFISH_FRY.create(this.world);
            if (angelfishfryEntity != null) {
                angelfishfryEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                angelfishfryEntity.initialize(serverWorld, this.world.getLocalDifficulty(angelfishfryEntity.getBlockPos()), SpawnReason.CONVERSION, null, null);
                angelfishfryEntity.setAiDisabled(this.isAiDisabled());
                if (this.hasCustomName()) {
                    angelfishfryEntity.setCustomName(this.getCustomName());
                    angelfishfryEntity.setCustomNameVisible(this.isCustomNameVisible());
                }
                angelfishfryEntity.setPersistent();
                this.playSound(SoundEvents.BLOCK_FROGSPAWN_HATCH, 0.15f, 1.0f);
                serverWorld.spawnEntityAndPassengers(angelfishfryEntity);
                this.discard();
            }
        }
    }

    private int getTicksUntilGrowth() {
        return Math.max(0, MAX_ANGELFISH_EGG_AGE - this.angelfishEggAge);
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
        return new ItemStack(ModItems.ANGELFISH_EGG_BUCKET);
    }

    @Override
    public @Nullable AngelfishEggEntity createChild(ServerWorld var1, PassiveWaterEntity var2) {
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

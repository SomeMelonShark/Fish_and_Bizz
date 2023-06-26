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

public class CorydorasEggEntity extends CorydorasEntity implements GeoEntity {
    @VisibleForTesting
    public static int MAX_CORYDORAS_EGG_AGE = Math.abs(-12000);
    private int corydorasEggAge;

    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    public CorydorasEggEntity(EntityType<? extends CorydorasEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new CorydorasEggEntity.FishMoveControl(this);
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
        if (!this.getWorld().isClient) {
            this.setCorydorasEggAge(this.corydorasEggAge + 1);
        }
    }
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Age", this.corydorasEggAge);
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setCorydorasEggAge(nbt.getInt("Age"));
    }
    @Override
    public void copyDataToStack(ItemStack stack) {
        Bucketable.copyDataToStack(this, stack);
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        nbtCompound.putInt("Age", this.getCorydorasEggAge());
    }
    @Override
    public void copyDataFromNbt(NbtCompound nbt) {
        Bucketable.copyDataFromNbt(this, nbt);
        if (nbt.contains("Age")) {
            this.setCorydorasEggAge(nbt.getInt("Age"));
        }
    }
    private int getCorydorasEggAge() {
        return this.corydorasEggAge;
    }
    private void increaseAge(int seconds) {
        this.setCorydorasEggAge(this.corydorasEggAge + seconds * 20);
    }
    private void setCorydorasEggAge(int corydorasEggAge) {
        this.corydorasEggAge = corydorasEggAge;
        if (this.corydorasEggAge >= MAX_CORYDORAS_EGG_AGE) {
            this.growUp();
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }


    private void growUp() {
        World world = this.getWorld();
        int i = random.nextBetweenExclusive(5, 10);
        for (int j = 1; j <= i; ++j)
            if (world instanceof ServerWorld) {
                ServerWorld serverWorld = (ServerWorld)world;
                CorydorasFryEntity corydorasFryEntity = ModEntities.CORYDORAS_FRY.create(this.getWorld());
                if (corydorasFryEntity != null) {
                    corydorasFryEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                    corydorasFryEntity.initialize(serverWorld, this.getWorld().getLocalDifficulty(corydorasFryEntity.getBlockPos()), SpawnReason.CONVERSION, null, null);
                    corydorasFryEntity.setAiDisabled(this.isAiDisabled());
                    if (this.hasCustomName()) {
                        corydorasFryEntity.setCustomName(this.getCustomName());
                        corydorasFryEntity.setCustomNameVisible(this.isCustomNameVisible());
                    }
                    corydorasFryEntity.setPersistent();
                    this.playSound(SoundEvents.BLOCK_FROGSPAWN_HATCH, 0.15f, 1.0f);
                    serverWorld.spawnEntityAndPassengers(corydorasFryEntity);
                    this.discard();
                }
            }
    }

    private int getTicksUntilGrowth() {
        return Math.max(0, MAX_CORYDORAS_EGG_AGE - this.corydorasEggAge);
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

package net.redmelon.fishandshiz.entity.custom;

import com.google.common.annotations.VisibleForTesting;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.redmelon.fishandshiz.cclass.AnimalWaterEntity;
import net.redmelon.fishandshiz.cclass.PassiveWaterEntity;
import net.redmelon.fishandshiz.entity.ModEntities;
import net.redmelon.fishandshiz.item.ModItems;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MudCrabEggEntity extends MudCrabEntity implements GeoEntity {
    @VisibleForTesting
    public static int MAX_MUD_CRAB_EGG_AGE = Math.abs(-12000);
    private int mudCrabEggAge;

    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    public MudCrabEggEntity(EntityType<? extends MudCrabEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalWaterEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1);
    }

    protected void initGoals() {

    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.getWorld().isClient) {
            this.setMudCrabEggAge(this.mudCrabEggAge + 1);
        }
    }
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Age", this.mudCrabEggAge);
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setMudCrabEggAge(nbt.getInt("Age"));
    }
    @Override
    public void copyDataToStack(ItemStack stack) {
        Bucketable.copyDataToStack(this, stack);
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        nbtCompound.putInt("Age", this.getMudCrabEggAge());
    }
    @Override
    public void copyDataFromNbt(NbtCompound nbt) {
        Bucketable.copyDataFromNbt(this, nbt);
        if (nbt.contains("Age")) {
            this.setMudCrabEggAge(nbt.getInt("Age"));
        }
    }
    private int getMudCrabEggAge() {
        return this.mudCrabEggAge;
    }
    private void increaseAge(int seconds) {
        this.setMudCrabEggAge(this.mudCrabEggAge + seconds * 20);
    }
    private void setMudCrabEggAge(int mudCrabEggAge) {
        this.mudCrabEggAge = mudCrabEggAge;
        if (this.mudCrabEggAge >= MAX_MUD_CRAB_EGG_AGE) {
            this.growUp();
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }


    private void growUp() {
        World world = this.getWorld();
        int i = random.nextBetweenExclusive(4, 7);
        for (int j = 1; j <= i; ++j)
            if (world instanceof ServerWorld) {
                ServerWorld serverWorld = (ServerWorld)world;
                MudCrabLarvaEntity mudCrabLarvaEntity = ModEntities.MUD_CRAB_LARVA.create(this.getWorld());
                if (mudCrabLarvaEntity != null) {
                    mudCrabLarvaEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                    mudCrabLarvaEntity.initialize(serverWorld, this.getWorld().getLocalDifficulty(mudCrabLarvaEntity.getBlockPos()), SpawnReason.CONVERSION, null, null);
                    mudCrabLarvaEntity.setAiDisabled(this.isAiDisabled());
                    if (this.hasCustomName()) {
                        mudCrabLarvaEntity.setCustomName(this.getCustomName());
                        mudCrabLarvaEntity.setCustomNameVisible(this.isCustomNameVisible());
                    }
                    mudCrabLarvaEntity.setPersistent();
                    this.playSound(SoundEvents.BLOCK_FROGSPAWN_HATCH, 0.15f, 1.0f);
                    serverWorld.spawnEntityAndPassengers(mudCrabLarvaEntity);
                    this.discard();
                }
            }
    }

    private int getTicksUntilGrowth() {
        return Math.max(0, MAX_MUD_CRAB_EGG_AGE - this.mudCrabEggAge);
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
        return new ItemStack(ModItems.MUD_CRAB_EGG_BUCKET);
    }

    @Override
    public @Nullable MudCrabEggEntity createChild(ServerWorld var1, PassiveWaterEntity var2) {
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

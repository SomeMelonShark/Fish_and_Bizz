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
import net.redmelon.fishandshiz.cclass.SchoolingBreedEntity;
import net.redmelon.fishandshiz.entity.ModEntities;
import net.redmelon.fishandshiz.item.ModItems;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ClownfishEggEntity extends ClownfishEntity implements GeoEntity {
    @VisibleForTesting
    public static int MAX_CLOWNFISH_EGG_AGE = Math.abs(-12000);
    private int clowfishEggAge;

    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public ClownfishEggEntity(EntityType<? extends ClownfishEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new ClownfishEggEntity.FishMoveControl(this);
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
        public void tick() {/**does not move**/
        }
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.world.isClient) {
            this.setClowfishEggAge(this.clowfishEggAge + 1);
        }
    }
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Age", this.clowfishEggAge);
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setClowfishEggAge(nbt.getInt("Age"));
    }
    @Override
    public void copyDataToStack(ItemStack stack) {
        Bucketable.copyDataToStack(this, stack);
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        nbtCompound.putInt("Age", this.getClowfishEggAge());
    }
    @Override
    public void copyDataFromNbt(NbtCompound nbt) {
        Bucketable.copyDataFromNbt(this, nbt);
        if (nbt.contains("Age")) {
            this.setClowfishEggAge(nbt.getInt("Age"));
        }
    }

    private int getClowfishEggAge() {
        return this.clowfishEggAge;
    }
    private void increaseAge(int seconds) {
        this.setClowfishEggAge(this.clowfishEggAge + seconds * 20);
    }
    private void setClowfishEggAge(int clowfishEggAge) {
        this.clowfishEggAge = clowfishEggAge;
        if (this.clowfishEggAge >= MAX_CLOWNFISH_EGG_AGE) {
            this.growUp();
        }
    }

    private void growUp() {
        World world = this.world;
        int i = random.nextBetweenExclusive(4, 9);
        for (int j = 1; j <= i; ++j)
            if (world instanceof ServerWorld) {
                ServerWorld serverWorld = (ServerWorld)world;
                ClownfishFryEntity clownfishFryEntity = ModEntities.CLOWNFISH_FRY.create(this.world);
                if (clownfishFryEntity != null) {
                    clownfishFryEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                    clownfishFryEntity.initialize(serverWorld, this.world.getLocalDifficulty(clownfishFryEntity.getBlockPos()), SpawnReason.CONVERSION, null, null);
                    clownfishFryEntity.setAiDisabled(this.isAiDisabled());
                    if (this.hasCustomName()) {
                        clownfishFryEntity.setCustomName(this.getCustomName());
                        clownfishFryEntity.setCustomNameVisible(this.isCustomNameVisible());
                    }
                    clownfishFryEntity.setPersistent();
                    this.playSound(SoundEvents.BLOCK_FROGSPAWN_HATCH, 0.15f, 1.0f);
                    serverWorld.spawnEntityAndPassengers(clownfishFryEntity);
                    this.discard();
                }
            }
    }

    private int getTicksUntilGrowth() {
        return Math.max(0, MAX_CLOWNFISH_EGG_AGE - this.clowfishEggAge);
    }

    @Override
    public boolean shouldDropXp() {
        return false;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
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
        return new ItemStack(ModItems.CLOWNFISH_EGG_BUCKET);
    }

    @Override
    public @Nullable NeonTetraEggEntity createChild(ServerWorld var1, PassiveWaterEntity var2) {
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

package net.redmelon.fishandshiz.entity.custom.fish;

import com.google.common.annotations.VisibleForTesting;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.EntityData;
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
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.redmelon.fishandshiz.cclass.AnimalFishEntity;
import net.redmelon.fishandshiz.cclass.PassiveWaterEntity;
import net.redmelon.fishandshiz.entity.ModEntities;
import net.redmelon.fishandshiz.entity.variant.ModEntityColor;
import net.redmelon.fishandshiz.entity.variant.AngelfishDetail;
import net.redmelon.fishandshiz.entity.variant.AngelfishPattern;
import net.redmelon.fishandshiz.item.ModItems;
import net.redmelon.fishandshiz.util.ModUtil;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class AngelfishEggEntity extends AngelfishEntity implements GeoEntity {
    @VisibleForTesting
    public static int MAX_EGG_AGE = Math.abs(-12000);
    protected int stageAge;
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    public AngelfishEggEntity(EntityType<? extends AngelfishEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FishMoveControl(this);
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
    protected void initGoals() {

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
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        return entityData;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }


    private void growUp() {
        ModEntityColor color;
        ModEntityColor color2;
        ModEntityColor color3;
        AngelfishPattern pattern;
        AngelfishDetail detail;
        World world = this.getWorld();
        int i = random.nextBetweenExclusive(2, 6);
        for (int j = 1; j <= i; ++j)
            if (world instanceof ServerWorld) {
                int m = random.nextInt(4);
                if (m != 0) {
                    color = this.getBaseColor();
                    color2 = this.getPatternColor();
                    color3 = this.getDetailColor();
                    pattern = this.getPattern();
                    detail = this.getDetail();
                } else {
                    pattern = random.nextBoolean() ? this.getPattern() : (ModUtil.getRandomTagValue(getWorld(), AngelfishPattern.Tag.PATTERNS, random));
                    detail = random.nextBoolean() ? this.getDetail() : (ModUtil.getRandomTagValue(getWorld(), AngelfishDetail.Tag.DETAILS, random));
                    color = random.nextBoolean() ? this.getBaseColor() : (ModUtil.getRandomTagValue(getWorld(), ModEntityColor.Tag.BASE_COLORS, random));
                    color2 = random.nextBoolean() ? this.getPatternColor() : (ModUtil.getRandomTagValue(getWorld(), ModEntityColor.Tag.PATTERN_COLORS, random));
                    color3 = random.nextBoolean() ? this.getDetailColor() : (ModUtil.getRandomTagValue(getWorld(), ModEntityColor.Tag.DETAIL_COLORS, random));
                }
                ServerWorld serverWorld = (ServerWorld)world;
                AngelfishFryEntity nextEntity = ModEntities.ANGELFISH_FRY.create(this.getWorld());
                if (nextEntity != null) {
                    nextEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                    nextEntity.initialize(serverWorld, this.getWorld().getLocalDifficulty(nextEntity.getBlockPos()), SpawnReason.CONVERSION, null, null);
                    nextEntity.setAiDisabled(this.isAiDisabled());
                    if (this.hasCustomName()) {
                        nextEntity.setCustomName(this.getCustomName());
                        nextEntity.setCustomNameVisible(this.isCustomNameVisible());
                    }
                    nextEntity.setPersistent();
                    nextEntity.setBaseColor(color);
                    nextEntity.setPatternColor(color2);
                    nextEntity.setDetailColor(color3);
                    nextEntity.setPattern(pattern);
                    nextEntity.setDetail(detail);
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

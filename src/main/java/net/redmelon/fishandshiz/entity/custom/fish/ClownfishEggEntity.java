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
import net.redmelon.fishandshiz.cclass.cmethods.EntitySize;
import net.redmelon.fishandshiz.cclass.cmethods.SizeCategory;
import net.redmelon.fishandshiz.entity.ModEntities;
import net.redmelon.fishandshiz.entity.variant.*;
import net.redmelon.fishandshiz.item.ModItems;
import net.redmelon.fishandshiz.util.ModUtil;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ClownfishEggEntity extends ClownfishEntity implements GeoEntity, EntitySize {
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    public ClownfishEggEntity(EntityType<? extends ClownfishEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public SizeCategory getSizeCategory() {
        return SizeCategory.EGG;
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

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                 @Nullable EntityData entityData, @Nullable NbtCompound entityNbt){
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        this.setMature(false);
        this.setEgg(true);
        return entityData;
    }

    @Override
    protected int getMaxStageAge() {
        return 12000;
    }

    @Override
    protected void growUp() {
        ModEntityColor color;
        ModEntityColor color2;
        ModEntityColor color3;
        ClownfishPattern pattern;
        ClownfishDetail detail;
        World world = this.getWorld();
        int i = random.nextBetweenExclusive(3, 7);
        for (int j = 1; j <= i; ++j)
            if (world instanceof ServerWorld) {
                int m = random.nextInt(MUTATION_CHANCE);
                if (m != 0) {
                    color = this.getBaseColor();
                    color2 = this.getPatternColor();
                    color3 = this.getDetailColor();
                    pattern = this.getPattern();
                    detail = this.getDetail();
                } else {
                    pattern = random.nextBoolean() ? this.getPattern() : (ModUtil.getRandomTagValue(getWorld(), ClownfishPattern.Tag.PATTERNS, random));
                    detail = random.nextBoolean() ? this.getDetail() : (ModUtil.getRandomTagValue(getWorld(), ClownfishDetail.Tag.DETAILS, random));
                    color = random.nextBoolean() ? this.getBaseColor() : (ModUtil.getRandomTagValue(getWorld(), ModEntityColor.Tag.BASE_COLORS, random));
                    color2 = random.nextBoolean() ? this.getPatternColor() : (ModUtil.getRandomTagValue(getWorld(), ModEntityColor.Tag.PATTERN_COLORS, random));
                    color3 = random.nextBoolean() ? this.getDetailColor() : (ModUtil.getRandomTagValue(getWorld(), ModEntityColor.Tag.DETAIL_COLORS, random));
                }
                ServerWorld serverWorld = (ServerWorld)world;
                ClownfishFryEntity nextEntity = ModEntities.CLOWNFISH_FRY.create(this.getWorld());
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
        return new ItemStack(ModItems.CLOWNFISH_EGG_BUCKET);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }
}

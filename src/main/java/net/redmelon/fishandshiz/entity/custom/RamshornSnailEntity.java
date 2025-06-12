package net.redmelon.fishandshiz.entity.custom;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.redmelon.fishandshiz.block.ModBlocks;
import net.redmelon.fishandshiz.cclass.AnimalFishEntity;
import net.redmelon.fishandshiz.cclass.AnimalWaterEntity;
import net.redmelon.fishandshiz.cclass.PassiveWaterEntity;
import net.redmelon.fishandshiz.cclass.cmethods.EntitySize;
import net.redmelon.fishandshiz.cclass.cmethods.SizeCategory;
import net.redmelon.fishandshiz.cclass.cmethods.goals.BreedAnimalMateGoal;
import net.redmelon.fishandshiz.cclass.cmethods.goals.WaterWanderGoal;
import net.redmelon.fishandshiz.entity.ModEntities;
import net.redmelon.fishandshiz.entity.custom.fish.ArcherfishEntity;
import net.redmelon.fishandshiz.entity.variant.BiVariant;
import net.redmelon.fishandshiz.item.ModItems;
import net.redmelon.fishandshiz.sound.ModSounds;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class RamshornSnailEntity extends AnimalWaterEntity implements GeoEntity, EntitySize {
    protected static final TrackedData<Integer> VARIANT = DataTracker.registerData(RamshornSnailEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final String BUCKET_VARIANT_TAG_KEY = "BucketVariantTag";
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    public RamshornSnailEntity(EntityType<? extends AnimalWaterEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public SizeCategory getSizeCategory() {
        return SizeCategory.LARGE;
    }

    @Override
    protected int getNitrogenIncreaseAmount() {
        return 1;
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalFishEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1)
                .add(EntityAttributes.GENERIC_ARMOR, 1)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.22f);
    }

    protected void initGoals() {
        this.goalSelector.add(2, new BreedAnimalMateGoal(this, 1.0));
        this.goalSelector.add(3, new WaterWanderGoal(this, 1.0));
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                 @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        BiVariant variant;
        int i = random.nextInt(3);
        int j = random.nextInt(10);
        if (spawnReason == SpawnReason.BUCKET && entityNbt != null && entityNbt.contains(BUCKET_VARIANT_TAG_KEY, NbtElement.INT_TYPE)) {
            this.setVariant(entityNbt.getInt(BUCKET_VARIANT_TAG_KEY));
            return entityData;
        }
        if (spawnReason == SpawnReason.NATURAL && i == 0) {
            this.setBaby(true);
        }
        if (spawnReason == SpawnReason.NATURAL) {
            if (j == 0){
                variant = BiVariant.SPECIAL;
                this.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.5f, 1.5f);
            } else {
                variant = BiVariant.NORMAL;
            }
        } else {
            variant = BiVariant.byId(random.nextInt(1));
        }
        this.setAirResistant(true);
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        this.setVariant(variant.getId());
        return entityData;
    }

    private PlayState genericFlopController(AnimationState animationState) {
        if (this.isInsideWaterOrBubbleColumn() && animationState.isMoving()) {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.ramshorn_snail.crawl", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else if (this.isInsideWaterOrBubbleColumn() && !animationState.isMoving()){
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.ramshorn_snail.idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.ramshorn_snail.flop", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == ModBlocks.MULM.asItem();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.SNAIL_DEATH;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.SNAIL_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {return ModSounds.SNAIL_HURT;}

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", this.getTypeVariant());
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setVariant(nbt.getInt("Variant"));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(VARIANT, 0);
    }

    public static BiVariant getVariety(int variant) {
        return BiVariant.byId(variant);
    }

    public BiVariant getVariant() {
        return BiVariant.byId(this.getTypeVariant() & 255);
    }

    public int getTypeVariant() {
        return this.dataTracker.get(VARIANT);
    }

    protected void setVariant(BiVariant variant) {
        this.dataTracker.set(VARIANT, variant.getId() & 255);
    }

    protected void setVariant(int variant) {
        this.dataTracker.set(VARIANT, variant);
    }
    @Override
    public void copyDataToStack(ItemStack stack) {
        super.copyDataToStack(stack);
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        nbtCompound.putInt(BUCKET_VARIANT_TAG_KEY, this.getTypeVariant());
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(ModItems.RAMSHORN_SNAIL_BUCKET);
    }

    @Override
    public @Nullable PassiveWaterEntity createChild(ServerWorld var1, PassiveWaterEntity var2) {
        return ModEntities.RAMSHORN_SNAIL_EGG.create(getWorld());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 2, this::genericFlopController));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }
}

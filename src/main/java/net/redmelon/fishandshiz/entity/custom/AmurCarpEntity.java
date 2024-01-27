package net.redmelon.fishandshiz.entity.custom;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.SwimAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.redmelon.fishandshiz.cclass.AnimalFishEntity;
import net.redmelon.fishandshiz.cclass.PassiveWaterEntity;
import net.redmelon.fishandshiz.cclass.SchoolingBreedEntity;
import net.redmelon.fishandshiz.cclass.cmethods.goals.BreedAnimalMateGoal;
import net.redmelon.fishandshiz.cclass.cmethods.goals.BreedFollowGroupLeaderGoal;
import net.redmelon.fishandshiz.entity.ModEntities;
import net.redmelon.fishandshiz.item.ModItems;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Arrays;
import java.util.Comparator;

public class AmurCarpEntity extends SchoolingBreedEntity implements GeoEntity {
    protected static final TrackedData<Integer> VARIANT =
            DataTracker.registerData(AmurCarpEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<NbtCompound> MATE_DATA = DataTracker.registerData(AmurCarpEntity.class, TrackedDataHandlerRegistry.NBT_COMPOUND);
    public static final String BUCKET_VARIANT_TAG_KEY = "BucketVariantTag";
    public static final Ingredient FISH_FOOD = Ingredient.ofItems(ModItems.FISH_FOOD);
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    public AmurCarpEntity(EntityType<? extends SchoolingBreedEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalFishEntity.createFishAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 3);
    }
    private PlayState genericFlopController(AnimationState animationState) {
        if (this.isTouchingWater()) {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.amur_carp.swim", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.amur_carp.flop", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(2, new FleeEntityGoal<PlayerEntity>(this, PlayerEntity.class, 8.0f, 1.6, 1.4, EntityPredicates.EXCEPT_SPECTATOR::test));
        this.goalSelector.add(3, new BreedAnimalMateGoal(this, 1));
        this.goalSelector.add(4, new SwimAroundGoal(this, 1.0, 10));
        this.goalSelector.add(4, new BreedFollowGroupLeaderGoal(this));
    }

    @Override
    public @Nullable PassiveWaterEntity createChild(ServerWorld var1, PassiveWaterEntity var2) {
        return ModEntities.AMUR_CARP_EGG.create(getWorld());
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == ModItems.FISH_FOOD;
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
        return new ItemStack(ModItems.AMUR_CARP_BUCKET);
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
        return factory;
    }

    public enum AmurCarpVariant {
        WILD(0),
        CREAM1(1),
        CREAM2(2),
        CREAM3(3),
        CREAM4(4);

        private static final AmurCarpVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(AmurCarpVariant::getId))
                .toArray(AmurCarpVariant[]::new);
        private final int id;

        AmurCarpVariant(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static AmurCarpVariant byId(int id) {
            return BY_ID[id % BY_ID.length];
        }
    }

    public static AmurCarpVariant getVariety(int variant) {
        return AmurCarpVariant.byId(variant);
    }

    @Override
    public void writeCustomDatatoNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        writeMateData(nbt);
        nbt.put("MateData", getMateData());
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(VARIANT, nbt.getInt("Variant"));
        if(nbt.contains("MateData", NbtElement.INT_TYPE))setMateData(nbt.getCompound("MateData"));
    }
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(VARIANT, 0);
        this.dataTracker.startTracking(MATE_DATA, new NbtCompound());
    }

    public NbtCompound writeMateData(NbtCompound nbt) {
        nbt.putInt("Variant", this.getTypeVariant());
        return nbt;
    }

    public void setMateData(NbtCompound mateData) {
        dataTracker.set(MATE_DATA, mateData);
    }
    public NbtCompound getMateData() {
        return dataTracker.get(MATE_DATA);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                 @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        RegistryEntry<Biome> registryEntry = world.getBiome(this.getBlockPos());
        AmurCarpVariant variant;

        if (spawnReason == SpawnReason.BUCKET && entityNbt != null && entityNbt.contains(BUCKET_VARIANT_TAG_KEY, NbtElement.INT_TYPE)) {
            this.setAmurCarpVariant(entityNbt.getInt(BUCKET_VARIANT_TAG_KEY));
            return entityData;
        }

        if (spawnReason == SpawnReason.NATURAL){
            if (registryEntry.matchesKey(BiomeKeys.TAIGA)) {
                variant = (AmurCarpVariant.WILD);
            } else if (registryEntry.matchesKey(BiomeKeys.FROZEN_RIVER)) {
                variant = (AmurCarpVariant.WILD);
            } else {
                variant = Util.getRandom(AmurCarpVariant.values(), this.random);
            }

        }
        else if (spawnReason == SpawnReason.CONVERSION) {
            int i = random.nextInt(7);
            if (i == 1) {
                variant = Util.getRandom(AmurCarpVariant.values(), this.random);
            } else {
                variant = (AmurCarpVariant.WILD);
            }
        } else {
            variant = Util.getRandom(AmurCarpVariant.values(), this.random);
        }
        setVariant(variant);
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        this.setAmurCarpVariant(variant.getId());
        return entityData;
    }

    @Override
    public void copyDataToStack(ItemStack stack) {
        super.copyDataToStack(stack);
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        nbtCompound.putInt(BUCKET_VARIANT_TAG_KEY, this.getTypeVariant());
    }

    public AmurCarpVariant getVariant() {
        return AmurCarpVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.dataTracker.get(VARIANT);
    }

    protected void setVariant(AmurCarpVariant variant) {
        this.dataTracker.set(VARIANT, variant.getId() & 255);
    }

    private void setAmurCarpVariant(int variant) {
        this.dataTracker.set(VARIANT, variant);
    }
}

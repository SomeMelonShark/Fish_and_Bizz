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
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.cclass.AnimalFishEntity;
import net.redmelon.fishandshiz.cclass.PassiveWaterEntity;
import net.redmelon.fishandshiz.cclass.SchoolingBreedEntity;
import net.redmelon.fishandshiz.cclass.cmethods.BreedAnimalMateGoal;
import net.redmelon.fishandshiz.cclass.cmethods.BreedFollowGroupLeaderGoal;
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

import static net.redmelon.fishandshiz.FishAndShiz.MOD_ID;

public class AngelfishEntity extends SchoolingBreedEntity implements GeoEntity {
    protected static final TrackedData<Integer> VARIANT =
            DataTracker.registerData(AngelfishEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<NbtCompound> MATE_DATA = DataTracker.registerData(AngelfishEntity.class, TrackedDataHandlerRegistry.NBT_COMPOUND);
    public static final String BUCKET_VARIANT_TAG_KEY = "BucketVariantTag";
    public static final Ingredient FISH_FOOD = Ingredient.ofItems(ModItems.FISH_FOOD);
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public AngelfishEntity(EntityType<? extends SchoolingBreedEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalFishEntity.createFishAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 2);
    }
    private PlayState genericFlopController(AnimationState animationState) {
        if (this.isTouchingWater()) {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.angelfish.swim", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.angelfish.flop", Animation.LoopType.LOOP));
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
    public @Nullable AngelfishEggEntity createChild(ServerWorld world, PassiveWaterEntity passiveWaterEntity) {
        return ModEntities.ANGELFISH_EGG.create(world);
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
        return new ItemStack(ModItems.ANGELFISH_BUCKET);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_TROPICAL_FISH_HURT;
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController(this, "controller", 5, this::genericFlopController));
    }

    public enum AngelfishVariant {
        WILD1(0),
        WILD2(1),
        WILD3(2),
        MARBLE1(3),
        MARBLE2(4),
        PANTS1(5),
        PANTS2(6),
        PANTS3(7),
        PANTS4(8),
        STRIPES1(9),
        STRIPES2(10),
        STRIPES3(11);

        private static final AngelfishVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(AngelfishVariant::getId))
                .toArray(AngelfishVariant[]::new);
        private final int id;

        AngelfishVariant(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static AngelfishVariant byId(int id) {
            return BY_ID[id % BY_ID.length];
        }
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
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
        AngelfishVariant variant = Util.getRandom(AngelfishVariant.values(), this.random);
        setVariant(variant);
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        if (spawnReason == SpawnReason.BUCKET && entityNbt != null && entityNbt.contains(BUCKET_VARIANT_TAG_KEY, NbtElement.INT_TYPE)) {
            this.setAngelfishVariant(entityNbt.getInt(BUCKET_VARIANT_TAG_KEY));
            return entityData;
        }
        this.setAngelfishVariant(variant.getId());
        return entityData;
    }

    @Override
    public void copyDataToStack(ItemStack stack) {
        super.copyDataToStack(stack);
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        nbtCompound.putInt(BUCKET_VARIANT_TAG_KEY, this.getTypeVariant());
    }

    public AngelfishVariant getVariant() {
        return AngelfishVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.dataTracker.get(VARIANT);
    }

    protected void setVariant(AngelfishVariant variant) {
        this.dataTracker.set(VARIANT, variant.getId() & 255);
    }

    private void setAngelfishVariant(int variant) {
        this.dataTracker.set(VARIANT, variant);
    }
}


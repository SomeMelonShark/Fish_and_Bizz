package net.redmelon.fishandshiz.entity.custom.fish;

import net.minecraft.entity.Bucketable;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
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
import net.redmelon.fishandshiz.cclass.cmethods.goals.WaterStopGoal;
import net.redmelon.fishandshiz.entity.ModEntities;
import net.redmelon.fishandshiz.entity.custom.CrayfishEggEntity;
import net.redmelon.fishandshiz.entity.custom.CrayfishLarvaEntity;
import net.redmelon.fishandshiz.entity.custom.MudCrabEggEntity;
import net.redmelon.fishandshiz.entity.custom.MudCrabLarvaEntity;
import net.redmelon.fishandshiz.entity.variant.AmurCarpVariant;
import net.redmelon.fishandshiz.entity.variant.AngelfishColor;
import net.redmelon.fishandshiz.entity.variant.AngelfishPattern;
import net.redmelon.fishandshiz.item.ModItems;
import net.redmelon.fishandshiz.util.ModUtil;
import net.redmelon.fishandshiz.world.biome.ModBiomes;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class AngelfishEntity extends SchoolingBreedEntity implements GeoEntity {
    private static final TrackedData<AngelfishPattern> PATTERN = DataTracker.registerData(AngelfishEntity.class, AngelfishPattern.TRACKED_DATA_HANDLER);
    private static final TrackedData<AngelfishColor> BASE_COLOR = DataTracker.registerData(AngelfishEntity.class, AngelfishColor.TRACKED_DATA_HANDLER);
    private static final TrackedData<AngelfishColor> PATTERN_COLOR = DataTracker.registerData(AngelfishEntity.class, AngelfishColor.TRACKED_DATA_HANDLER);
    private static final TrackedData<NbtCompound> MATE_DATA = DataTracker.registerData(AngelfishEntity.class, TrackedDataHandlerRegistry.NBT_COMPOUND);
    public static final Ingredient FISH_FOOD = Ingredient.ofItems(ModItems.FISH_FOOD);
    public static final String BUCKET_VARIANT_TAG_KEY = "BucketVariantTag";
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public AngelfishEntity(EntityType<? extends SchoolingBreedEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalFishEntity.createFishAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 2)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2);
    }
    private PlayState genericFlopController(AnimationState animationState) {
        if (this.isTouchingWater() && animationState.isMoving()) {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.angelfish.swim", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else if (this.isTouchingWater() && !animationState.isMoving()){
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.angelfish.idle", Animation.LoopType.LOOP));
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
        this.goalSelector.add(1, new FleeEntityGoal<PlayerEntity>(this, PlayerEntity.class, 8.0f, 1.6, 1.4, EntityPredicates.EXCEPT_SPECTATOR::test));
        this.goalSelector.add(2, new BreedAnimalMateGoal(this, 1));
        this.goalSelector.add(3, new WaterStopGoal(this, 1d, 10));
        this.goalSelector.add(3, new BreedFollowGroupLeaderGoal(this));
        this.goalSelector.add(4, new SwimAroundGoal(this, 1.0, 10));
        this.goalSelector.add(6, new MeleeAttackGoal(this, 1.0f, true));

        this.targetSelector.add(1, new ActiveTargetGoal<>((MobEntity)this, NeonTetraEntity.class, true));

        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, NeonTetraFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, AuratusFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, CrayfishLarvaEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, MudCrabLarvaEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, CorydorasFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, AmurCarpFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, BettaFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, ClownfishFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, GraylingFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, MilkfishFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, OscarFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, RainbowfishFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, SalmonFryEntity.class, true));

        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, AmurCarpEggEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, AuratusEggEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, BettaEggEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, ClownfishEggEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, CorydorasEggEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, GraylingEggEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, MilkfishEggEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, NeonTetraEggEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, OscarEggEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, RainbowfishEggEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, SalmonEggEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, CrayfishEggEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, MudCrabEggEntity.class, true));
    }

    @Override
    public @Nullable AngelfishEggEntity createChild(ServerWorld var1, PassiveWaterEntity var2) {
        AngelfishEntity angelfishEntity = (AngelfishEntity) var2;
        AngelfishEggEntity angelfishEggEntity = (AngelfishEggEntity) ModEntities.ANGELFISH_EGG.create(var1);
        if (angelfishEggEntity != null) {
            AngelfishColor color;
            AngelfishColor color2;
            AngelfishPattern pattern;
                color = random.nextBoolean() ? this.getBaseColor() : angelfishEntity.getBaseColor();
                color2 = random.nextBoolean() ? this.getPatternColor() : angelfishEntity.getPatternColor();
                pattern = random.nextBoolean() ? this.getPattern() : angelfishEntity.getPattern();

            angelfishEggEntity.setBaseColor(color);
            angelfishEggEntity.setPatternColor(color2);
            angelfishEggEntity.setPattern(pattern);
        }
        return angelfishEggEntity;
    }


    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (this.isCultureFeed(itemStack) && getBreedingAge() > 0) {
            this.eatCultureFeed(player, itemStack);
            return ActionResult.success(this.getWorld().isClient);
        } else {
            return (ActionResult) Bucketable.tryBucket(player, hand, this).orElse(super.interactMob(player, hand));
        }
    }

    private boolean isCultureFeed(ItemStack stack) {
        return stack.isOf(ModItems.DRIED_CULTURE_FEED);
    }
    private void eatCultureFeed (PlayerEntity player, ItemStack stack) {
        this.decrementItem(player, stack);
        this.cultureAge();
        this.getWorld().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), 0.0, 0.0, 0.0);
        this.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.5f);
    }
    private void decrementItem(PlayerEntity player, ItemStack stack) {
        if (!player.getAbilities().creativeMode) {
            stack.decrement(1);
        }
    }

    private void cultureAge() {
        this.setBreedingAge((int) (getBreedingAge() * 0.9));
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

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }

    public NbtCompound writeMateData(NbtCompound nbt) {
        nbt.putString("Pattern", getPattern().getId().toString());
        nbt.putString("BaseColor", getBaseColor().getId().toString());
        nbt.putString("PatternColor", getPatternColor().getId().toString());
        return nbt;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        writeMateData(nbt);
        nbt.put("MateData", getMateData());
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        setPattern(AngelfishPattern.fromId(nbt.getString("Pattern")));
        setBaseColor(AngelfishColor.fromId(nbt.getString("BaseColor")));
        setPatternColor(AngelfishColor.fromId(nbt.getString("PatternColor")));
        if(nbt.contains("MateData", NbtElement.COMPOUND_TYPE))
            setMateData(nbt.getCompound("MateData"));
    }
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(PATTERN, AngelfishPattern.WILD);
        dataTracker.startTracking(BASE_COLOR, AngelfishColor.SILVER);
        dataTracker.startTracking(PATTERN_COLOR, AngelfishColor.SILVER);
        dataTracker.startTracking(MATE_DATA, new NbtCompound());
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                 @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        RegistryEntry<Biome> registryEntry = world.getBiome(this.getBlockPos());
        AngelfishColor color;
        AngelfishColor color2;
        AngelfishPattern pattern;
        if (spawnReason == SpawnReason.NATURAL) {
            if (registryEntry.matchesKey(ModBiomes.JUNGLE_BASIN)) {
                pattern = (AngelfishPattern.WILD);
                color = (AngelfishColor.SILVER);
                color2 = (AngelfishColor.MATTE_BLACK);
            } else if (registryEntry.matchesKey(BiomeKeys.SPARSE_JUNGLE)) {
                pattern = (AngelfishPattern.WILD);
                color = (AngelfishColor.SILVER);
                color2 = (AngelfishColor.MATTE_BLACK);
            } else if (registryEntry.matchesKey(BiomeKeys.JUNGLE)) {
                pattern = (AngelfishPattern.WILD);
                color = (AngelfishColor.SILVER);
                color2 = (AngelfishColor.MATTE_BLACK);
            } else {
                pattern = (ModUtil.getRandomTagValue(getWorld(), AngelfishPattern.Tag.PATTERNS, random));
                color = (ModUtil.getRandomTagValue(getWorld(), AngelfishColor.Tag.BASE_COLORS, random));
                color2 = (ModUtil.getRandomTagValue(getWorld(), AngelfishColor.Tag.PATTERN_COLORS, random));
            }
        } else {
            pattern = (ModUtil.getRandomTagValue(getWorld(), AngelfishPattern.Tag.PATTERNS, random));
            color = (ModUtil.getRandomTagValue(getWorld(), AngelfishColor.Tag.BASE_COLORS, random));
            color2 = (ModUtil.getRandomTagValue(getWorld(), AngelfishColor.Tag.PATTERN_COLORS, random));
        }
        setPattern(pattern);
        setBaseColor(color);
        setPatternColor(color2);
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        return entityData;
    }

    public void setBaseColor(AngelfishColor color) {
        dataTracker.set(BASE_COLOR, color);
    }

    public AngelfishColor getBaseColor() {
        return dataTracker.get(BASE_COLOR);
    }

    public void setPatternColor(AngelfishColor color) {
        dataTracker.set(PATTERN_COLOR, color);
    }

    public AngelfishColor getPatternColor() {
        return dataTracker.get(PATTERN_COLOR);
    }

    public void setPattern(AngelfishPattern pattern) {
        dataTracker.set(PATTERN, pattern);
    }

    public AngelfishPattern getPattern() {
        return dataTracker.get(PATTERN);
    }

    public void setMateData(NbtCompound mateData) {
        dataTracker.set(MATE_DATA, mateData);
    }

    public NbtCompound getMateData() {
        return dataTracker.get(MATE_DATA);
    }

    @Override @SuppressWarnings("deprecation")
    public void copyDataFromNbt(NbtCompound nbt) {
        Bucketable.copyDataFromNbt(this, nbt);
        if(nbt.contains("Pattern", NbtElement.STRING_TYPE)) {
            readCustomDataFromNbt(nbt);
        }
    }


    @Override
    public void copyDataToStack(ItemStack stack) {
        super.copyDataToStack(stack);
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        writeMateData(nbtCompound);
        nbtCompound.put("MateData", getMateData());
        nbtCompound.put(BUCKET_VARIANT_TAG_KEY, this.getMateData());
    }
}


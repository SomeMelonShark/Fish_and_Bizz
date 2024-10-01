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
import net.redmelon.fishandshiz.entity.ModEntities;
import net.redmelon.fishandshiz.entity.custom.CrayfishLarvaEntity;
import net.redmelon.fishandshiz.entity.custom.MudCrabLarvaEntity;
import net.redmelon.fishandshiz.entity.variant.*;
import net.redmelon.fishandshiz.item.ModItems;
import net.redmelon.fishandshiz.util.ModUtil;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class AmurCarpEntity extends SchoolingBreedEntity implements GeoEntity, VariableFishEntity<AmurCarpPattern, AmurCarpDetail> {
    private static final TrackedData<AmurCarpPattern> PATTERN = DataTracker.registerData(AmurCarpEntity.class, AmurCarpPattern.TRACKED_DATA_HANDLER);
    private static final TrackedData<AmurCarpDetail> DETAIL = DataTracker.registerData(AmurCarpEntity.class, AmurCarpDetail.TRACKED_DATA_HANDLER);
    private static final TrackedData<ModEntityColor> BASE_COLOR = DataTracker.registerData(AmurCarpEntity.class, ModEntityColor.TRACKED_DATA_HANDLER);
    private static final TrackedData<ModEntityColor> PATTERN_COLOR = DataTracker.registerData(AmurCarpEntity.class, ModEntityColor.TRACKED_DATA_HANDLER);
    private static final TrackedData<ModEntityColor> DETAIL_COLOR = DataTracker.registerData(AmurCarpEntity.class, ModEntityColor.TRACKED_DATA_HANDLER);
    private static final TrackedData<NbtCompound> MATE_DATA = DataTracker.registerData(AmurCarpEntity.class, TrackedDataHandlerRegistry.NBT_COMPOUND);
    public static final String BUCKET_VARIANT_TAG_KEY = "BucketVariantTag";
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    public AmurCarpEntity(EntityType<? extends SchoolingBreedEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected int getNitrogenIncreaseAmount() {
        if (!isFry() && !isMature()) {
            return 0;
        } else if (isFry()) {
            return 1;
        }
        return 4;
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalFishEntity.createFishAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 3)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3);
    }
    private PlayState genericFlopController(AnimationState animationState) {
        if (this.isTouchingWater()) {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.mediumfish.swim", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.mediumfish.flop", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(1, new FleeEntityGoal<PlayerEntity>(this, PlayerEntity.class, 8.0f, 1.6, 1.4, EntityPredicates.EXCEPT_SPECTATOR::test));
        this.goalSelector.add(2, new BreedAnimalMateGoal(this, 1));
        this.goalSelector.add(3, new BreedFollowGroupLeaderGoal(this));
        this.goalSelector.add(6, new MeleeAttackGoal(this, 1.0f, true));

        this.targetSelector.add(1, new ActiveTargetGoal<>((MobEntity)this, NeonTetraEntity.class, true));
        this.targetSelector.add(1, new ActiveTargetGoal<>((MobEntity)this, CorydorasEntity.class, true));

        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, NeonTetraFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, AuratusFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, CrayfishLarvaEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, MudCrabLarvaEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, CorydorasFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, AmurCarpFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, AngelfishFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, BettaFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, GraylingFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, MilkfishFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, OscarFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, RainbowfishFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, SalmonFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, PlatyFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, ClownfishFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, TangFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, DottybackFryEntity.class, true));

        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, AmurCarpEggEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, BettaEggEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, AngelfishEggEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, CorydorasEggEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, GraylingEggEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, MilkfishEggEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, NeonTetraEggEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, OscarEggEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, RainbowfishEggEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, SalmonEggEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, ClownfishEggEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, TangEggEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, DottybackEggEntity.class, true));
    }

    @Override
    public @Nullable AmurCarpEggEntity createChild(ServerWorld var1, PassiveWaterEntity var2) {
        AmurCarpEntity entity = (AmurCarpEntity) var2;
        AmurCarpEggEntity eggEntity = (AmurCarpEggEntity) ModEntities.AMUR_CARP_EGG.create(var1);
        if (eggEntity != null) {
            ModEntityColor color;
            ModEntityColor color2;
            ModEntityColor color3;
            AmurCarpPattern pattern;
            AmurCarpDetail detail;
                color = random.nextBoolean() ? this.getBaseColor() : entity.getBaseColor();
                color2 = random.nextBoolean() ? this.getPatternColor() : entity.getPatternColor();
                color3 = random.nextBoolean() ? this.getDetailColor() : entity.getDetailColor();
                pattern = random.nextBoolean() ? this.getPattern() : entity.getPattern();
                detail = random.nextBoolean() ? this.getDetail() : entity.getDetail();

            eggEntity.setBaseColor(color);
            eggEntity.setPatternColor(color2);
            eggEntity.setDetailColor(color3);
            eggEntity.setPattern(pattern);
            eggEntity.setDetail(detail);
        }
        return eggEntity;
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

    public NbtCompound writeMateData(NbtCompound nbt) {
        nbt.putString("Pattern", getPattern().getId().toString());
        nbt.putString("Detail", getDetail().getId().toString());
        nbt.putString("BaseColor", getBaseColor().getId().toString());
        nbt.putString("PatternColor", getPatternColor().getId().toString());
        nbt.putString("DetailColor", getDetailColor().getId().toString());
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
        setPattern(AmurCarpPattern.fromId(nbt.getString("Pattern")));
        setDetail(AmurCarpDetail.fromId(nbt.getString("Detail")));
        setBaseColor(ModEntityColor.fromId(nbt.getString("BaseColor")));
        setPatternColor(ModEntityColor.fromId(nbt.getString("PatternColor")));
        setDetailColor(ModEntityColor.fromId(nbt.getString("DetailColor")));
        if(nbt.contains("MateData", NbtElement.COMPOUND_TYPE))
            setMateData(nbt.getCompound("MateData"));
    }
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(PATTERN, AmurCarpPattern.WILD);
        dataTracker.startTracking(DETAIL, AmurCarpDetail.NONE);
        dataTracker.startTracking(BASE_COLOR, ModEntityColor.SILVER);
        dataTracker.startTracking(PATTERN_COLOR, ModEntityColor.SILVER);
        dataTracker.startTracking(DETAIL_COLOR, ModEntityColor.SILVER);
        dataTracker.startTracking(MATE_DATA, new NbtCompound());
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                 @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        RegistryEntry<Biome> registryEntry = world.getBiome(this.getBlockPos());
        ModEntityColor color;
        ModEntityColor color2;
        ModEntityColor color3;
        AmurCarpPattern pattern;
        AmurCarpDetail detail;
        if (spawnReason == SpawnReason.NATURAL) {
            if (registryEntry.matchesKey(BiomeKeys.FROZEN_RIVER) | registryEntry.matchesKey(BiomeKeys.TAIGA)) {
                pattern = (AmurCarpPattern.WILD);
                detail = (AmurCarpDetail.NONE);
                color = (ModEntityColor.OFF_YELLOW);
                color2 = (ModEntityColor.GREEN);
                color3 = (ModEntityColor.SILVER);
            } else {
                pattern = (ModUtil.getRandomTagValue(getWorld(), AmurCarpPattern.Tag.PATTERNS, random));
                detail = (ModUtil.getRandomTagValue(getWorld(), AmurCarpDetail.Tag.DETAILS, random));
                color = (ModUtil.getRandomTagValue(getWorld(), ModEntityColor.Tag.BASE_COLORS, random));
                color2 = (ModUtil.getRandomTagValue(getWorld(), ModEntityColor.Tag.PATTERN_COLORS, random));
                color3 = (ModUtil.getRandomTagValue(getWorld(), ModEntityColor.Tag.DETAIL_COLORS, random));
            }
        } else {
            pattern = (ModUtil.getRandomTagValue(getWorld(), AmurCarpPattern.Tag.PATTERNS, random));
            detail = (ModUtil.getRandomTagValue(getWorld(), AmurCarpDetail.Tag.DETAILS, random));
            color = (ModUtil.getRandomTagValue(getWorld(), ModEntityColor.Tag.BASE_COLORS, random));
            color2 = (ModUtil.getRandomTagValue(getWorld(), ModEntityColor.Tag.PATTERN_COLORS, random));
            color3 = (ModUtil.getRandomTagValue(getWorld(), ModEntityColor.Tag.DETAIL_COLORS, random));
        }
        setPattern(pattern);
        setDetail(detail);
        setBaseColor(color);
        setPatternColor(color2);
        setDetailColor(color3);
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        return entityData;
    }
    public void setBaseColor(ModEntityColor color) {
        dataTracker.set(BASE_COLOR, color);
    }

    public ModEntityColor getBaseColor() {
        return dataTracker.get(BASE_COLOR);
    }

    public void setPatternColor(ModEntityColor color) {
        dataTracker.set(PATTERN_COLOR, color);
    }

    public ModEntityColor getPatternColor() {
        return dataTracker.get(PATTERN_COLOR);
    }

    public void setDetailColor(ModEntityColor color) {
        dataTracker.set(DETAIL_COLOR, color);
    }

    public ModEntityColor getDetailColor() {
        return dataTracker.get(DETAIL_COLOR);
    }

    public void setPattern(AmurCarpPattern pattern) {
        dataTracker.set(PATTERN, pattern);
    }

    public AmurCarpPattern getPattern() {
        return dataTracker.get(PATTERN);
    }

    public void setDetail(AmurCarpDetail detail) {
        dataTracker.set(DETAIL, detail);
    }

    public AmurCarpDetail getDetail() {
        return dataTracker.get(DETAIL);
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

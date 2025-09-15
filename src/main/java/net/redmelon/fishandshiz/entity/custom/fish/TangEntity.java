package net.redmelon.fishandshiz.entity.custom.fish;

import net.minecraft.entity.*;
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
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.redmelon.fishandshiz.cclass.AnimalFishEntity;
import net.redmelon.fishandshiz.cclass.HolometabolousAquaticEntity;
import net.redmelon.fishandshiz.cclass.PassiveWaterEntity;
import net.redmelon.fishandshiz.cclass.SchoolingBreedEntity;
import net.redmelon.fishandshiz.cclass.cmethods.EntitySize;
import net.redmelon.fishandshiz.cclass.cmethods.SizeCategory;
import net.redmelon.fishandshiz.cclass.cmethods.goals.*;
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
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class TangEntity extends SchoolingBreedEntity implements GeoEntity, VariableFishEntity<TangPattern, TangDetail>, EntitySize {
    private static final TrackedData<TangPattern> PATTERN = DataTracker.registerData(TangEntity.class, TangPattern.TRACKED_DATA_HANDLER);
    private static final TrackedData<TangDetail> DETAIL = DataTracker.registerData(TangEntity.class, TangDetail.TRACKED_DATA_HANDLER);
    private static final TrackedData<ModEntityColor> BASE_COLOR = DataTracker.registerData(TangEntity.class, ModEntityColor.TRACKED_DATA_HANDLER);
    private static final TrackedData<ModEntityColor> PATTERN_COLOR = DataTracker.registerData(TangEntity.class, ModEntityColor.TRACKED_DATA_HANDLER);
    private static final TrackedData<ModEntityColor> DETAIL_COLOR = DataTracker.registerData(TangEntity.class, ModEntityColor.TRACKED_DATA_HANDLER);
    private static final TrackedData<NbtCompound> MATE_DATA = DataTracker.registerData(TangEntity.class, TrackedDataHandlerRegistry.NBT_COMPOUND);

    public static final Ingredient FISH_FOOD = Ingredient.ofItems(ModItems.FISH_FOOD);
    public static final String BUCKET_VARIANT_TAG_KEY = "BucketVariantTag";

    public TangEntity(EntityType<? extends SchoolingBreedEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public SizeCategory getSizeCategory() {
        return SizeCategory.MEDIUM;
    }

    @Override
    protected int getNitrogenIncreaseAmount() {
        if (!isFry() && !isMature()) {
            return 0;
        } else if (isFry()) {
            return 1;
        }
        return 3;
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalFishEntity.createFishAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 2)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(1, new FleeEntityGoal<PlayerEntity>(this, PlayerEntity.class, 8.0f, 1.6, 1.4, EntityPredicates.EXCEPT_SPECTATOR::test));
        this.goalSelector.add(2, new BreedAnimalMateGoal(this, 1));
        this.goalSelector.add(3, new BreedFollowGroupLeaderGoal(this));
        this.goalSelector.add(6, new ShortRangeAttackGoal(this, 1.0f, true, 1.0f));

        this.targetSelector.add(1, new SizedTargetGoal<>(this, LivingEntity.class, true, SizeCategory.MEDIUM, 2, 4));
    }

    @Override
    public @Nullable TangEggEntity createChild(ServerWorld var1, PassiveWaterEntity var2) {
        TangEntity entity = (TangEntity) var2;
        TangEggEntity eggEntity = (TangEggEntity) ModEntities.TANG_EGG.create(var1);
        if (eggEntity != null) {
            ModEntityColor color;
            ModEntityColor color2;
            ModEntityColor color3;
            TangPattern pattern;
            TangDetail detail;
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
        return new ItemStack(ModItems.TANG_BUCKET);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_TROPICAL_FISH_HURT;
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
        setPattern(TangPattern.fromId(nbt.getString("Pattern")));
        setDetail(TangDetail.fromId(nbt.getString("Detail")));
        setBaseColor(ModEntityColor.fromId(nbt.getString("BaseColor")));
        setPatternColor(ModEntityColor.fromId(nbt.getString("PatternColor")));
        setDetailColor(ModEntityColor.fromId(nbt.getString("DetailColor")));
        if(nbt.contains("MateData", NbtElement.COMPOUND_TYPE))
            setMateData(nbt.getCompound("MateData"));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(PATTERN, TangPattern.GIBBOUS);
        dataTracker.startTracking(DETAIL, TangDetail.NONE);
        dataTracker.startTracking(BASE_COLOR, ModEntityColor.SILVER);
        dataTracker.startTracking(PATTERN_COLOR, ModEntityColor.SILVER);
        dataTracker.startTracking(DETAIL_COLOR, ModEntityColor.SILVER);
        dataTracker.startTracking(MATE_DATA, new NbtCompound());
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                 @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        int i = random.nextInt(4);
        RegistryEntry<Biome> registryEntry = world.getBiome(this.getBlockPos());
        Identifier biomeId = registryEntry.getKey().map(RegistryKey::getValue).orElse(null);
        ModEntityColor color;
        ModEntityColor color2;
        ModEntityColor color3;
        TangPattern pattern;
        TangDetail detail;
        if (spawnReason == SpawnReason.NATURAL) {
            if (registryEntry.matchesKey(BiomeKeys.WARM_OCEAN) | registryEntry.matchesKey(BiomeKeys.LUKEWARM_OCEAN) | registryEntry.matchesKey(BiomeKeys.MANGROVE_SWAMP) | (biomeId != null && biomeId.getNamespace().equals("terrestria") && biomeId.getPath().equals("volcanic_island"))) {
                if (i == 0) {
                    pattern = (TangPattern.GIBBOUS);
                    detail = (TangDetail.NONE);
                    color = (ModEntityColor.MATTE_BLACK);
                    color2 = (ModEntityColor.MATTE_BLACK);
                    color3 = (ModEntityColor.SILVER);
                } else if (i == 1) {
                    pattern = (TangPattern.RIPPLE);
                    detail = (TangDetail.SPOT);
                    color = (ModEntityColor.NEON_BLUE);
                    color2 = (ModEntityColor.MATTE_BLACK);
                    color3 = (ModEntityColor.YELLOW);
                } else if (i == 2) {
                    pattern = (TangPattern.BACKSPLASH);
                    detail = (TangDetail.MOORED);
                    color = (ModEntityColor.SILVER);
                    color2 = (ModEntityColor.GOLD);
                    color3 = (ModEntityColor.EARTH_BLACK);
                } else {
                    pattern = (TangPattern.GIBBOUS);
                    detail = (TangDetail.NONE);
                    color = (ModEntityColor.GOLD);
                    color2 = (ModEntityColor.GOLD);
                    color3 = (ModEntityColor.SILVER);
                }
            } else {
                pattern = (ModUtil.getRandomTagValue(getWorld(), TangPattern.Tag.PATTERNS, random));
                detail = (ModUtil.getRandomTagValue(getWorld(), TangDetail.Tag.DETAILS, random));
                color = (ModUtil.getRandomTagValue(getWorld(), ModEntityColor.Tag.BASE_COLORS, random));
                color2 = (ModUtil.getRandomTagValue(getWorld(), ModEntityColor.Tag.PATTERN_COLORS, random));
                color3 = (ModUtil.getRandomTagValue(getWorld(), ModEntityColor.Tag.DETAIL_COLORS, random));
            }
        } else {
            pattern = (ModUtil.getRandomTagValue(getWorld(), TangPattern.Tag.PATTERNS, random));
            detail = (ModUtil.getRandomTagValue(getWorld(), TangDetail.Tag.DETAILS, random));
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

    public void setPattern(TangPattern pattern) {
        dataTracker.set(PATTERN, pattern);
    }

    public TangPattern getPattern() {
        return dataTracker.get(PATTERN);
    }

    public void setDetail(TangDetail detail) {
        dataTracker.set(DETAIL, detail);
    }

    public TangDetail getDetail() {
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

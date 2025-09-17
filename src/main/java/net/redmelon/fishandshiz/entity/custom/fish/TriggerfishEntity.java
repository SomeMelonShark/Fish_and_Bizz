package net.redmelon.fishandshiz.entity.custom.fish;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.MobEntity;
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
import net.redmelon.fishandshiz.cclass.cmethods.goals.BreedAnimalMateGoal;
import net.redmelon.fishandshiz.cclass.cmethods.goals.ShortRangeAttackGoal;
import net.redmelon.fishandshiz.cclass.cmethods.goals.SizedTargetGoal;
import net.redmelon.fishandshiz.entity.custom.CrayfishEntity;
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

public class TriggerfishEntity extends AnimalFishEntity implements GeoEntity, VariableFishEntity<TriggerfishPattern, TriggerfishDetail>, EntitySize {
    private static final TrackedData<TriggerfishPattern> PATTERN = DataTracker.registerData(TriggerfishEntity.class, TriggerfishPattern.TRACKED_DATA_HANDLER);
    private static final TrackedData<TriggerfishDetail> DETAIL = DataTracker.registerData(TriggerfishEntity.class, TriggerfishDetail.TRACKED_DATA_HANDLER);
    private static final TrackedData<ModEntityColor> BASE_COLOR = DataTracker.registerData(TriggerfishEntity.class, ModEntityColor.TRACKED_DATA_HANDLER);
    private static final TrackedData<ModEntityColor> PATTERN_COLOR = DataTracker.registerData(TriggerfishEntity.class, ModEntityColor.TRACKED_DATA_HANDLER);
    private static final TrackedData<ModEntityColor> DETAIL_COLOR = DataTracker.registerData(TriggerfishEntity.class, ModEntityColor.TRACKED_DATA_HANDLER);
    private static final TrackedData<NbtCompound> MATE_DATA = DataTracker.registerData(TriggerfishEntity.class, TrackedDataHandlerRegistry.NBT_COMPOUND);

    public static final Ingredient FISH_FOOD = Ingredient.ofItems(Items.SEAGRASS);
    public static final String BUCKET_VARIANT_TAG_KEY = "BucketVariantTag";

    public TriggerfishEntity(EntityType<? extends AnimalFishEntity> entityType, World world) {
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
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(1, new FleeEntityGoal<PlayerEntity>(this, PlayerEntity.class, 8.0f, 1.6, 1.4, EntityPredicates.EXCEPT_SPECTATOR::test));
        this.goalSelector.add(2, new BreedAnimalMateGoal(this, 1));
        this.goalSelector.add(6, new ShortRangeAttackGoal(this, 3.0f, true, 1.0f));

        this.targetSelector.add(0, new ActiveTargetGoal<>((MobEntity)this, PlayerEntity.class, true));
        this.targetSelector.add(1, new SizedTargetGoal<>(this, LivingEntity.class, true, SizeCategory.MEDIUM, 2, 4));

    }

    @Override
    public @Nullable PassiveWaterEntity createChild(ServerWorld var1, PassiveWaterEntity var2) {
        return null;
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
        return new ItemStack(ModItems.TRIGGERFISH_BUCKET);
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
        setPattern(TriggerfishPattern.fromId(nbt.getString("Pattern")));
        setDetail(TriggerfishDetail.fromId(nbt.getString("Detail")));
        setBaseColor(ModEntityColor.fromId(nbt.getString("BaseColor")));
        setPatternColor(ModEntityColor.fromId(nbt.getString("PatternColor")));
        setDetailColor(ModEntityColor.fromId(nbt.getString("DetailColor")));
        if(nbt.contains("MateData", NbtElement.COMPOUND_TYPE))
            setMateData(nbt.getCompound("MateData"));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(PATTERN, TriggerfishPattern.SUNSPOT);
        dataTracker.startTracking(DETAIL, TriggerfishDetail.NONE);
        dataTracker.startTracking(BASE_COLOR, ModEntityColor.SILVER);
        dataTracker.startTracking(PATTERN_COLOR, ModEntityColor.SILVER);
        dataTracker.startTracking(DETAIL_COLOR, ModEntityColor.SILVER);
        dataTracker.startTracking(MATE_DATA, new NbtCompound());
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                 @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        int i = random.nextInt(3);
        RegistryEntry<Biome> registryEntry = world.getBiome(this.getBlockPos());
        ModEntityColor color;
        ModEntityColor color2;
        ModEntityColor color3;
        TriggerfishPattern pattern;
        TriggerfishDetail detail;
        if (spawnReason == SpawnReason.NATURAL) {
            if (registryEntry.matchesKey(BiomeKeys.WARM_OCEAN)) {
                if (i == 0) {
                    pattern = (TriggerfishPattern.SUNSPOT);
                    detail = (TriggerfishDetail.BRUX);
                    color = (ModEntityColor.EARTH_BLACK);
                    color2 = (ModEntityColor.SILVER);
                    color3 = (ModEntityColor.GOLD);
                } else {
                    pattern = (TriggerfishPattern.GARTER);
                    detail = (TriggerfishDetail.BEARD);
                    color = (ModEntityColor.MUDDY);
                    color2 = (ModEntityColor.MATTE_BLACK);
                    color3 = (ModEntityColor.SILVER);
                }
            } else {
                pattern = (ModUtil.getRandomTagValue(getWorld(), TriggerfishPattern.Tag.PATTERNS, random));
                detail = (ModUtil.getRandomTagValue(getWorld(), TriggerfishDetail.Tag.DETAILS, random));
                color = (ModUtil.getRandomTagValue(getWorld(), ModEntityColor.Tag.BASE_COLORS, random));
                color2 = (ModUtil.getRandomTagValue(getWorld(), ModEntityColor.Tag.PATTERN_COLORS, random));
                color3 = (ModUtil.getRandomTagValue(getWorld(), ModEntityColor.Tag.DETAIL_COLORS, random));
            }
        } else {
            pattern = (ModUtil.getRandomTagValue(getWorld(), TriggerfishPattern.Tag.PATTERNS, random));
            detail = (ModUtil.getRandomTagValue(getWorld(), TriggerfishDetail.Tag.DETAILS, random));
            color = (ModUtil.getRandomTagValue(getWorld(), ModEntityColor.Tag.BASE_COLORS, random));
            color2 = (ModUtil.getRandomTagValue(getWorld(), ModEntityColor.Tag.PATTERN_COLORS, random));
            color3 = (ModUtil.getRandomTagValue(getWorld(), ModEntityColor.Tag.DETAIL_COLORS, random));
        }
        setPattern(pattern);
        setDetail(detail);
        setBaseColor(color);
        setPatternColor(color2);
        setDetailColor(color3);
        this.setMacro(this.isMature());
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

    public void setPattern(TriggerfishPattern pattern) {
        dataTracker.set(PATTERN, pattern);
    }

    public TriggerfishPattern getPattern() {
        return dataTracker.get(PATTERN);
    }

    public void setDetail(TriggerfishDetail detail) {
        dataTracker.set(DETAIL, detail);
    }

    public TriggerfishDetail getDetail() {
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

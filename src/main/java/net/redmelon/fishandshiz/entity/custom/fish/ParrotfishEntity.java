package net.redmelon.fishandshiz.entity.custom.fish;

import net.minecraft.block.Blocks;
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
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.redmelon.fishandshiz.cclass.AnimalFishEntity;
import net.redmelon.fishandshiz.cclass.PassiveWaterEntity;
import net.redmelon.fishandshiz.cclass.SchoolingBreedEntity;
import net.redmelon.fishandshiz.cclass.cmethods.goals.BreedAnimalMateGoal;
import net.redmelon.fishandshiz.entity.custom.CrayfishEntity;
import net.redmelon.fishandshiz.entity.custom.CrayfishLarvaEntity;
import net.redmelon.fishandshiz.entity.custom.MudCrabLarvaEntity;
import net.redmelon.fishandshiz.entity.variant.ModEntityColor;
import net.redmelon.fishandshiz.entity.variant.ParrotfishDetail;
import net.redmelon.fishandshiz.entity.variant.ParrotfishPattern;
import net.redmelon.fishandshiz.item.ModItems;
import net.redmelon.fishandshiz.util.ModUtil;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.EnumSet;
import java.util.function.Predicate;

public class ParrotfishEntity extends SchoolingBreedEntity implements GeoEntity, VariableFishEntity<ParrotfishPattern, ParrotfishDetail> {
    private static final TrackedData<ParrotfishPattern> PATTERN = DataTracker.registerData(ParrotfishEntity.class, ParrotfishPattern.TRACKED_DATA_HANDLER);
    private static final TrackedData<ParrotfishDetail> DETAIL = DataTracker.registerData(ParrotfishEntity.class, ParrotfishDetail.TRACKED_DATA_HANDLER);
    private static final TrackedData<ModEntityColor> BASE_COLOR = DataTracker.registerData(ParrotfishEntity.class, ModEntityColor.TRACKED_DATA_HANDLER);
    private static final TrackedData<ModEntityColor> PATTERN_COLOR = DataTracker.registerData(ParrotfishEntity.class, ModEntityColor.TRACKED_DATA_HANDLER);
    private static final TrackedData<ModEntityColor> DETAIL_COLOR = DataTracker.registerData(ParrotfishEntity.class, ModEntityColor.TRACKED_DATA_HANDLER);
    private static final TrackedData<NbtCompound> MATE_DATA = DataTracker.registerData(ParrotfishEntity.class, TrackedDataHandlerRegistry.NBT_COMPOUND);

    public static final Ingredient FISH_FOOD = Ingredient.ofItems(Items.SEAGRASS);
    public static final String BUCKET_VARIANT_TAG_KEY = "BucketVariantTag";
    private boolean reachedTarget;

    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public ParrotfishEntity(EntityType<? extends SchoolingBreedEntity> entityType, World world) {
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
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2)
                .add(EntityAttributes.GENERIC_ARMOR, 2);
    }

    private PlayState genericFlopController(AnimationState animationState) {
        if (this.isTouchingWater() && animationState.isMoving()) {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.mediumfish.swim", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else if (this.isTouchingWater() && !animationState.isMoving() && !this.isNavigating()){
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.mediumfish.eating", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.mediumfish.flop", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
    }

    public boolean hasReachedTarget() {
        return this.reachedTarget;
    }

    public void setHasReachedTarget(boolean reachedTarget) {
        this.reachedTarget = reachedTarget;
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(1, new FleeEntityGoal<PlayerEntity>(this, PlayerEntity.class, 8.0f, 1.6, 1.4, EntityPredicates.EXCEPT_SPECTATOR::test));
        this.goalSelector.add(2, new BreedAnimalMateGoal(this, 1));
        this.goalSelector.add(3, new CoralSandingGoal(this, 1.0, this::isTargetBlock, new ItemStack(Items.SAND), 100, 3600));
        this.goalSelector.add(6, new MeleeAttackGoal(this, 1.0f, true));

        this.targetSelector.add(1, new ActiveTargetGoal<>((MobEntity)this, CrayfishEntity.class, true));
        this.targetSelector.add(1, new ActiveTargetGoal<>((MobEntity)this, CreeperEntity.class, true));

        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, NeonTetraFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, AuratusFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, CrayfishLarvaEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, MudCrabLarvaEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, CorydorasFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, AmurCarpFryEntity.class, true));
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

    private boolean isTargetBlock(BlockPos pos) {
        return this.getWorld().getBlockState(pos).getBlock() == Blocks.BRAIN_CORAL_BLOCK ||
                this.getWorld().getBlockState(pos).getBlock() == Blocks.TUBE_CORAL_BLOCK ||
                this.getWorld().getBlockState(pos).getBlock() == Blocks.HORN_CORAL_BLOCK ||
                this.getWorld().getBlockState(pos).getBlock() == Blocks.FIRE_CORAL_BLOCK ||
                this.getWorld().getBlockState(pos).getBlock() == Blocks.BUBBLE_CORAL_BLOCK;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.hasReachedTarget()) {
            double d = this.random.nextGaussian() * 0.01;
            double e = this.random.nextGaussian() * 0.01;
            double f = this.random.nextGaussian() * 0.01;
            this.getWorld().addParticle(ParticleTypes.BUBBLE_COLUMN_UP, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), d, e, f);
        }
    }

    @Override
    public @Nullable PassiveWaterEntity createChild(ServerWorld var1, PassiveWaterEntity var2) {
        return null;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == ModItems.CORN || stack.getItem() == Items.SEAGRASS;
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
        return new ItemStack(ModItems.PARROTFISH_BUCKET);
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
        setPattern(ParrotfishPattern.fromId(nbt.getString("Pattern")));
        setDetail(ParrotfishDetail.fromId(nbt.getString("Detail")));
        setBaseColor(ModEntityColor.fromId(nbt.getString("BaseColor")));
        setPatternColor(ModEntityColor.fromId(nbt.getString("PatternColor")));
        setDetailColor(ModEntityColor.fromId(nbt.getString("DetailColor")));
        if(nbt.contains("MateData", NbtElement.COMPOUND_TYPE))
            setMateData(nbt.getCompound("MateData"));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(PATTERN, ParrotfishPattern.GLITTER);
        dataTracker.startTracking(DETAIL, ParrotfishDetail.NONE);
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
        ParrotfishPattern pattern;
        ParrotfishDetail detail;
        if (spawnReason == SpawnReason.NATURAL) {
            if (registryEntry.matchesKey(BiomeKeys.WARM_OCEAN)) {
                if (i == 0) {
                    pattern = (ParrotfishPattern.GLITTER);
                    detail = (ParrotfishDetail.NONE);
                    color = (ModEntityColor.NEON_BLUE);
                    color2 = (ModEntityColor.PINK);
                    color3 = (ModEntityColor.SILVER);
                } else if (i == 1) {
                    pattern = (ParrotfishPattern.TAILGATE);
                    detail = (ParrotfishDetail.NONE);
                    color = (ModEntityColor.EARTH_BLACK);
                    color2 = (ModEntityColor.YELLOW);
                    color3 = (ModEntityColor.SILVER);
                } else {
                    pattern = (ParrotfishPattern.MASKED);
                    detail = (ParrotfishDetail.NOSETRIP);
                    color = (ModEntityColor.SICKLY);
                    color2 = (ModEntityColor.NEON_BLUE);
                    color3 = (ModEntityColor.LIGHT_BLUE);
                }
            } else {
                pattern = (ModUtil.getRandomTagValue(getWorld(), ParrotfishPattern.Tag.PATTERNS, random));
                detail = (ModUtil.getRandomTagValue(getWorld(), ParrotfishDetail.Tag.DETAILS, random));
                color = (ModUtil.getRandomTagValue(getWorld(), ModEntityColor.Tag.BASE_COLORS, random));
                color2 = (ModUtil.getRandomTagValue(getWorld(), ModEntityColor.Tag.PATTERN_COLORS, random));
                color3 = (ModUtil.getRandomTagValue(getWorld(), ModEntityColor.Tag.DETAIL_COLORS, random));
            }
        } else {
            pattern = (ModUtil.getRandomTagValue(getWorld(), ParrotfishPattern.Tag.PATTERNS, random));
            detail = (ModUtil.getRandomTagValue(getWorld(), ParrotfishDetail.Tag.DETAILS, random));
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

    public void setPattern(ParrotfishPattern pattern) {
        dataTracker.set(PATTERN, pattern);
    }

    public ParrotfishPattern getPattern() {
        return dataTracker.get(PATTERN);
    }

    public void setDetail(ParrotfishDetail detail) {
        dataTracker.set(DETAIL, detail);
    }

    public ParrotfishDetail getDetail() {
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

    static class CoralSandingGoal extends Goal {
        private final ParrotfishEntity entity;
        private final double speed;
        private final Predicate<BlockPos> targetBlockPredicate;
        private final ItemStack itemToSpawn;
        private final int stayDuration;
        private final int spawnCooldown;
        private BlockPos targetBlock;
        private int stayTimer;
        private int cooldownTimer;

        public CoralSandingGoal(ParrotfishEntity entity, double speed, Predicate<BlockPos> targetBlockPredicate, ItemStack itemToSpawn, int stayDuration, int spawnCooldown) {
            this.entity = entity;
            this.speed = speed;
            this.targetBlockPredicate = targetBlockPredicate;
            this.itemToSpawn = itemToSpawn;
            this.stayDuration = stayDuration;
            this.spawnCooldown = spawnCooldown;
            this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        }

        @Override
        public boolean canStart() {
            if (cooldownTimer > 0) {
                cooldownTimer--;
                return false;
            }
            targetBlock = findNearestTargetBlock();
            return targetBlock != null;
        }

        @Override
        public boolean shouldContinue() {
            return targetBlock != null && stayTimer < stayDuration && targetBlockPredicate.test(targetBlock);
        }

        @Override
        public void start() {
            stayTimer = 0;
            entity.setHasReachedTarget(false);
            moveToTargetBlock();
        }

        @Override
        public void stop() {
            targetBlock = null;
            cooldownTimer = spawnCooldown;
            entity.setHasReachedTarget(false);
        }

        @Override
        public void tick() {
            if (targetBlock == null) {
                return;
            }

            if (entity.getBlockPos().isWithinDistance(targetBlock, 2.0)) {
                entity.getNavigation().stop();
                entity.getLookControl().lookAt(targetBlock.getX() + 0.5, targetBlock.getY() + 0.5, targetBlock.getZ() + 0.5);
                stayTimer++;
                entity.setHasReachedTarget(true);

                playSound();

                if (stayTimer >= stayDuration) {
                    spawnItem();
                    stop();
                }
            } else {
                moveToTargetBlock();
            }
        }

        private BlockPos findNearestTargetBlock() {
            BlockPos entityPos = entity.getBlockPos();
            for (int dx = -10; dx <= 10; dx++) {
                for (int dy = -10; dy <= 10; dy++) {
                    for (int dz = -10; dz <= 10; dz++) {
                        BlockPos pos = entityPos.add(dx, dy, dz);
                        if (targetBlockPredicate.test(pos)) {
                            return pos;
                        }
                    }
                }
            }
            return null;
        }

        private void moveToTargetBlock() {
            if (targetBlock != null) {
                entity.getNavigation().startMovingTo(targetBlock.getX() + 0.5, targetBlock.getY() + 0.5, targetBlock.getZ() + 0.5, speed);
            }
        }

        private void spawnItem() {
            World world = entity.getWorld();
            if (!world.isClient) {
                world.spawnEntity(new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), itemToSpawn.copy()));
            }
        }

        private void playSound() {
            World world = entity.getWorld();
            if (!world.isClient) {
                world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BLOCK_CORAL_BLOCK_HIT, SoundCategory.BLOCKS, 0.15f, 1.3f);
            }
        }
    }
}

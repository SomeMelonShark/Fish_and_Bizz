package net.redmelon.fishandshiz.entity.custom.fish;

import com.google.common.annotations.VisibleForTesting;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.redmelon.fishandshiz.cclass.AnimalFishEntity;
import net.redmelon.fishandshiz.cclass.PassiveWaterEntity;
import net.redmelon.fishandshiz.cclass.cmethods.EntitySize;
import net.redmelon.fishandshiz.cclass.cmethods.SizeCategory;
import net.redmelon.fishandshiz.cclass.cmethods.goals.FloatGoal;
import net.redmelon.fishandshiz.entity.ModEntities;
import net.redmelon.fishandshiz.entity.variant.*;
import net.redmelon.fishandshiz.item.ModItems;
import net.redmelon.fishandshiz.util.ModUtil;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import static com.ibm.icu.text.PluralRules.Operand.j;

public class BettaEggEntity extends BettaEntity implements GeoEntity, EntitySize {

    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    public BettaEggEntity(EntityType<? extends BettaEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public SizeCategory getSizeCategory() {
        return SizeCategory.EGG;
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalFishEntity.createFishAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.05f);
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.canMoveVoluntarily() && this.isTouchingWater()) {
            this.hasNoGravity();
            this.updateVelocity(0.01f, movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.00001));
        } else {
            this.move(MovementType.SELF, this.getVelocity());
            this.updateVelocity(0.01f, movementInput);
            this.setVelocity(this.getVelocity().add(0.0, -0.01, 0.0));
        }
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new FloatGoal(this));
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
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    @Override
    protected int getMaxStageAge() {
        return 6000;
    }

    @Override
    protected void growUp() {
        ModEntityColor color;
        ModEntityColor color2;
        ModEntityColor color3;
        BettaPattern pattern;
        BettaDetail detail;
        World world = this.getWorld();
        int i = random.nextBetweenExclusive(1, 4);
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
                    pattern = random.nextBoolean() ? this.getPattern() : (ModUtil.getRandomTagValue(getWorld(), BettaPattern.Tag.PATTERNS, random));
                    detail = random.nextBoolean() ? this.getDetail() : (ModUtil.getRandomTagValue(getWorld(), BettaDetail.Tag.DETAILS, random));
                    color = random.nextBoolean() ? this.getBaseColor() : (ModUtil.getRandomTagValue(getWorld(), ModEntityColor.Tag.BASE_COLORS, random));
                    color2 = random.nextBoolean() ? this.getPatternColor() : (ModUtil.getRandomTagValue(getWorld(), ModEntityColor.Tag.PATTERN_COLORS, random));
                    color3 = random.nextBoolean() ? this.getDetailColor() : (ModUtil.getRandomTagValue(getWorld(), ModEntityColor.Tag.DETAIL_COLORS, random));
                }
                ServerWorld serverWorld = (ServerWorld)world;
                BettaFryEntity nextEntity = ModEntities.BETTA_FRY.create(this.getWorld());
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
        return new ItemStack(ModItems.BETTA_EGG_BUCKET);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }
}

package net.redmelon.fishandshiz.entity.custom;

import com.google.common.annotations.VisibleForTesting;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.redmelon.fishandshiz.cclass.AnimalFishEntity;
import net.redmelon.fishandshiz.cclass.AnimalWaterEntity;
import net.redmelon.fishandshiz.cclass.EggboundEntity;
import net.redmelon.fishandshiz.cclass.PassiveWaterEntity;
import net.redmelon.fishandshiz.cclass.cmethods.CustomCriteria;
import net.redmelon.fishandshiz.cclass.cmethods.goals.BreedWaterAnimalMateGoal;
import net.redmelon.fishandshiz.cclass.cmethods.goals.EggboundMateGoal;
import net.redmelon.fishandshiz.cclass.cmethods.goals.LandWanderFarGoal;
import net.redmelon.fishandshiz.cclass.cmethods.goals.WaterWanderGoal;
import net.redmelon.fishandshiz.entity.ModEntities;
import net.redmelon.fishandshiz.entity.custom.fish.*;
import net.redmelon.fishandshiz.item.ModItems;
import net.redmelon.fishandshiz.sound.ModSounds;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class CrayfishEntity extends EggboundEntity implements GeoEntity {
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public CrayfishEntity(EntityType<? extends EggboundEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalFishEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 2)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1f);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new EscapeDangerGoal(this, 2.0));
        this.goalSelector.add(2, new EggboundMateGoal(this, 1.0));
        this.goalSelector.add(3, new WaterWanderGoal(this, 3.5));
        this.goalSelector.add(4, new LandWanderFarGoal(this, 1.0));
        this.goalSelector.add(4, new LookAroundGoal(this));
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
    }

    @Override
    protected void tickWaterBreathingAir(int air) {
        if (this.isAlive() && !this.isInsideWaterOrBubbleColumn()) {
            this.setAir(air - 1);
            if (this.getAir() == -20) {
                this.setAir(0);
            }
        } else {
            this.setAir(300);
        }

    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_GUARDIAN_FLOP;
    }

    private PlayState genericFlopController(AnimationState animationState) {
        if (animationState.isMoving() && this.isOnGround()) {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.crayfish.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else if (!animationState.isMoving() && this.isOnGround()) {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.crayfish.idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else if (this.isTouchingWater()){
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.crayfish.swim", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.crayfish.idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.COD;
    }

    @Override
    protected void birth() {
        World world = this.getWorld();
        int i = random.nextBetweenExclusive(5, 7);
        for (int j = 1; j <= i; ++j)
            if (world instanceof ServerWorld) {
                ServerWorld serverWorld = (ServerWorld)world;
                CrayfishLarvaEntity nextEntity = ModEntities.CRAYFISH_LARVA.create(this.getWorld());
                if (nextEntity != null) {
                    nextEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                    nextEntity.initialize(serverWorld, this.getWorld().getLocalDifficulty(nextEntity.getBlockPos()), SpawnReason.CONVERSION, null, null);
                    nextEntity.setAiDisabled(this.isAiDisabled());
                    if (this.hasCustomName()) {
                        nextEntity.setCustomName(this.getCustomName());
                        nextEntity.setCustomNameVisible(this.isCustomNameVisible());
                    }
                    nextEntity.setPersistent();
                    this.playSound(SoundEvents.BLOCK_FROGSPAWN_HATCH, 0.15f, 1.0f);
                    serverWorld.spawnEntityAndPassengers(nextEntity);
                }
            }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.CRAB_DEATH;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.CRAB_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.CRAB_HURT;
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 2, this::genericFlopController));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(ModItems.CRAYFISH_BUCKET);
    }

    @Override
    public @Nullable PassiveWaterEntity createChild(ServerWorld var1, PassiveWaterEntity var2) {
        return null;
    }
}

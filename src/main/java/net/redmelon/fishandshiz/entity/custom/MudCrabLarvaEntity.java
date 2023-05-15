package net.redmelon.fishandshiz.entity.custom;

import net.minecraft.entity.Bucketable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.FollowGroupLeaderGoal;
import net.minecraft.entity.ai.goal.SwimAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.SchoolingFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.redmelon.fishandshiz.entity.ModEntities;
import net.redmelon.fishandshiz.item.ModItems;
import org.jetbrains.annotations.VisibleForTesting;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MudCrabLarvaEntity extends SchoolingFishEntity implements GeoEntity {
    @VisibleForTesting
    public static int MAX_CRAB_LARVA_AGE = Math.abs(-18000);
    public static float WIDTH = 0.4f;
    public static float HEIGHT = 0.4f;
    private int mudCrabLarvaAge;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public MudCrabLarvaEntity(EntityType<? extends SchoolingFishEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return FishEntity.createFishAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1);
    }

    private PlayState genericFlopController(AnimationState animationState) {
        if (this.isTouchingWater()) {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.mud_crab_larva.swim", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.mud_crab_larva.dry", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
    }

    @Override
    public void tickMovement() {
        if(!this.isTouchingWater() && this.onGround && this.verticalCollision) {
            this.verticalCollision = false;
        }
        super.tickMovement();
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(2, new FleeEntityGoal<PlayerEntity>(this, PlayerEntity.class, 8.0f, 1.6, 1.4, EntityPredicates.EXCEPT_SPECTATOR::test));
        this.goalSelector.add(4, new SwimAroundGoal(this, 1.0, 10));
        this.goalSelector.add(4, new FollowGroupLeaderGoal(this));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Age", this.mudCrabLarvaAge);
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setMudCrabLarvaAge(nbt.getInt("Age"));
    }
    @Override
    public void copyDataToStack(ItemStack stack) {
        Bucketable.copyDataToStack(this, stack);
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        nbtCompound.putInt("Age", this.getMudCrabLarvaAge());
    }
    @Override
    public void copyDataFromNbt(NbtCompound nbt) {
        Bucketable.copyDataFromNbt(this, nbt);
        if (nbt.contains("Age")) {
            this.setMudCrabLarvaAge(nbt.getInt("Age"));
        }
    }

    private void decrementItem(PlayerEntity player, ItemStack stack) {
        if (!player.getAbilities().creativeMode) {
            stack.decrement(1);
        }
    }

    private int getMudCrabLarvaAge() {
        return this.mudCrabLarvaAge;
    }
    private void increaseAge(int seconds) {
        this.setMudCrabLarvaAge(this.mudCrabLarvaAge + seconds * 20);
    }
    private void setMudCrabLarvaAge(int mudCrabLarvaAge) {
        this.mudCrabLarvaAge = mudCrabLarvaAge;
        if (this.mudCrabLarvaAge >= MAX_CRAB_LARVA_AGE) {
            this.growUp();
        }
    }

    private void growUp() {
        World world = this.world;
        if (world instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld)world;
            MudCrabEntity mudCrabEntity = ModEntities.MUD_CRAB.create(this.world);
            if (mudCrabEntity != null) {
                mudCrabEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                mudCrabEntity.initialize(serverWorld, this.world.getLocalDifficulty(mudCrabEntity.getBlockPos()), SpawnReason.CONVERSION, null, null);
                mudCrabEntity.setAiDisabled(this.isAiDisabled());
                if (this.hasCustomName()) {
                    mudCrabEntity.setCustomName(this.getCustomName());
                    mudCrabEntity.setCustomNameVisible(this.isCustomNameVisible());
                }
                mudCrabEntity.setPersistent();
                this.playSound(SoundEvents.ENTITY_TURTLE_EGG_CRACK, 0.15f, 1.0f);
                serverWorld.spawnEntityAndPassengers(mudCrabEntity);
                this.discard();
            }
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.BLOCK_FROGSPAWN_HIT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BLOCK_MOSS_BREAK;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_FROGLIGHT_BREAK;
    }

    private int getTicksUntilGrowth() {
        return Math.max(0, MAX_CRAB_LARVA_AGE - this.mudCrabLarvaAge);
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_GUARDIAN_FLOP;
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(ModItems.MUD_CRAB_LARVA_BUCKET);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController(this, "controller", 5, this::genericFlopController));
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}

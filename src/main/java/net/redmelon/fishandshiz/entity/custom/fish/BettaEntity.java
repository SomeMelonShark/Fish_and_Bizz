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
import net.redmelon.fishandshiz.entity.custom.CrayfishEggEntity;
import net.redmelon.fishandshiz.entity.custom.CrayfishLarvaEntity;
import net.redmelon.fishandshiz.entity.custom.MudCrabEggEntity;
import net.redmelon.fishandshiz.entity.custom.MudCrabLarvaEntity;
import net.redmelon.fishandshiz.entity.tags.TropicalSpawn;
import net.redmelon.fishandshiz.entity.variant.AngelfishVariant;
import net.redmelon.fishandshiz.entity.variant.BettaVariant;
import net.redmelon.fishandshiz.item.ModItems;
import net.redmelon.fishandshiz.world.biome.ModBiomes;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BettaEntity extends SchoolingBreedEntity implements GeoEntity {
    protected static final TrackedData<Integer> VARIANT =
            DataTracker.registerData(BettaEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final String BUCKET_VARIANT_TAG_KEY = "BucketVariantTag";
    public static final Ingredient FISH_FOOD = Ingredient.ofItems(ModItems.FISH_FOOD);
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public BettaEntity(EntityType<? extends SchoolingBreedEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalFishEntity.createFishAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 2)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.5);
    }
    private PlayState genericFlopController(AnimationState animationState) {
        if (this.isTouchingWater() && animationState.isMoving()) {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.betta.swim", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else if (this.isTouchingWater() && !animationState.isMoving()){
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.betta.idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.betta.flop", Animation.LoopType.LOOP));
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
        this.goalSelector.add(6, new MeleeAttackGoal(this, 0.2f, true));

        this.targetSelector.add(1, new ActiveTargetGoal<>((MobEntity)this, NeonTetraEntity.class, true));
        this.targetSelector.add(1, new ActiveTargetGoal<>((MobEntity)this, BettaEntity.class, true));

        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, NeonTetraFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, AuratusFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, CrayfishLarvaEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, MudCrabLarvaEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, CorydorasFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, AmurCarpFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, AngelfishFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, BettaFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, ClownfishFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, GraylingFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, MilkfishFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, OscarFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, RainbowfishFryEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, SalmonFryEntity.class, true));

        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, AmurCarpEggEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, AuratusEggEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, AngelfishEggEntity.class, true));
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
    public @Nullable BettaEggEntity createChild(ServerWorld var1, PassiveWaterEntity var2) {
        BettaEntity bettaEntity = (BettaEntity) var2;
        BettaEggEntity bettaEggEntity = (BettaEggEntity) ModEntities.BETTA_EGG.create(var1);
        if (bettaEggEntity != null) {
            int i = random.nextInt(4);
            BettaVariant variant;
            if (i < 2) {
                variant = this.getVariant();
            } else if (i > 2) {
                variant = bettaEntity.getVariant();
            } else {
                variant = (BettaVariant) Util.getRandom(BettaVariant.values(), this.random);
            }

            bettaEggEntity.setVariant(variant);
        }
        return bettaEggEntity;
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
        return new ItemStack(ModItems.BETTA_BUCKET);
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

    public static BettaVariant getVariety(int variant) {
        return BettaVariant.byId(variant);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", this.getTypeVariant());
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(VARIANT, nbt.getInt("Variant"));
    }
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(VARIANT, 0);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                 @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        RegistryEntry<Biome> registryEntry = world.getBiome(this.getBlockPos());
        BettaVariant variant;
        int j = random.nextInt(1200);

        if (spawnReason == SpawnReason.BUCKET && entityNbt != null && entityNbt.contains(BUCKET_VARIANT_TAG_KEY, NbtElement.INT_TYPE)) {
            this.setBettaVariant(entityNbt.getInt(BUCKET_VARIANT_TAG_KEY));
            return entityData;
        }

        if (spawnReason == SpawnReason.CONVERSION && entityNbt != null && entityNbt.contains(BUCKET_VARIANT_TAG_KEY, NbtElement.INT_TYPE) && j != 0) {
            this.setBettaVariant(entityNbt.getInt(BUCKET_VARIANT_TAG_KEY));
            return entityData;
        } else if (spawnReason == SpawnReason.CONVERSION && entityNbt != null && entityNbt.contains(BUCKET_VARIANT_TAG_KEY, NbtElement.INT_TYPE) && j == 0) {
            variant = (BettaVariant.SPECIAL);
            this.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0f, 1.5f);
        }else if (spawnReason == SpawnReason.CONVERSION) {
            int i = random.nextInt(7);
            if (i == 1) {
                variant = BettaVariant.byId(random.nextInt(7));
            } else {
                variant = (BettaVariant.WILD1);
            }
        } else {
            variant = BettaVariant.byId(random.nextInt(7));
        }

        if (spawnReason == SpawnReason.NATURAL && j == 0){
            variant = (BettaVariant.SPECIAL);
            this.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 2.0f, 1.5f);
        } else if (spawnReason == SpawnReason.NATURAL) {
            if (registryEntry.matchesKey(BiomeKeys.SWAMP)) {
                variant = (BettaVariant.WILD1);
            } else if (registryEntry.matchesKey(BiomeKeys.PLAINS)) {
                variant = (BettaVariant.WILD2);
            }
        } else {
            variant = BettaVariant.byId(random.nextInt(7));
        }
        setVariant(variant);
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        this.setBettaVariant(variant.getId());
        return entityData;
    }

    @Override
    public void copyDataToStack(ItemStack stack) {
        super.copyDataToStack(stack);
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        nbtCompound.putInt(BUCKET_VARIANT_TAG_KEY, this.getTypeVariant());
    }

    public BettaVariant getVariant() {
        return BettaVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.dataTracker.get(VARIANT);
    }

    protected void setVariant(BettaVariant variant) {
        this.dataTracker.set(VARIANT, variant.getId() & 255);
    }

    protected void setBettaVariant(int variant) {
        this.dataTracker.set(VARIANT, variant);
    }
}

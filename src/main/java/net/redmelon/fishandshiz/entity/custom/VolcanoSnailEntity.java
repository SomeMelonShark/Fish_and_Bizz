package net.redmelon.fishandshiz.entity.custom;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import net.redmelon.fishandshiz.cclass.AnimalFishEntity;
import net.redmelon.fishandshiz.cclass.AnimalWaterEntity;
import net.redmelon.fishandshiz.cclass.PassiveWaterEntity;
import net.redmelon.fishandshiz.cclass.cmethods.goals.BreedWaterAnimalMateGoal;
import net.redmelon.fishandshiz.cclass.cmethods.goals.WaterWanderGoal;
import net.redmelon.fishandshiz.entity.ModEntities;
import net.redmelon.fishandshiz.entity.tags.TropicalSpawn;
import net.redmelon.fishandshiz.item.ModItems;
import net.redmelon.fishandshiz.sound.ModSounds;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.shadowed.eliotlash.mclib.math.functions.classic.Mod;

public class VolcanoSnailEntity extends AnimalWaterEntity implements GeoEntity {
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    public VolcanoSnailEntity(EntityType<? extends AnimalWaterEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalFishEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 2)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.22f);
    }

    protected void initGoals() {
        this.goalSelector.add(2, new BreedWaterAnimalMateGoal(this, 1.0));
        this.goalSelector.add(3, new WaterWanderGoal(this, 1.0));
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

    private PlayState genericFlopController(AnimationState animationState) {
        if (this.isInsideWaterOrBubbleColumn() && animationState.isMoving()) {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.volcano_snail.crawl", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else if (this.isInsideWaterOrBubbleColumn() && !animationState.isMoving()){
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.volcano_snail.idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.volcano_snail.flop", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.COBBLESTONE || stack.getItem() == Items.STONE;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.SNAIL_DEATH;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.SNAIL_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {return ModSounds.SNAIL_HURT;}

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(ModItems.VOLCANO_SNAIL_BUCKET);
    }

    @Override
    public @Nullable PassiveWaterEntity createChild(ServerWorld var1, PassiveWaterEntity var2) {
        return ModEntities.VOLCANO_SNAIL_EGG.create(getWorld());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 2, this::genericFlopController));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    public static boolean canSpawn(EntityType<? extends WaterCreatureEntity> type, WorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        int i = world.getSeaLevel();
        int j = i - 33;
        int k = j - 40;
        return pos.getY() <= j && pos.getY() >= k && world.getFluidState(pos.down()).isIn(FluidTags.WATER) && world.getBlockState(pos.up()).isOf(Blocks.WATER);
    }
}

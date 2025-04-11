package net.redmelon.fishandshiz.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.redmelon.fishandshiz.cclass.FishNitrogenAccessor;
import net.redmelon.fishandshiz.cclass.HolometabolousAquaticEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(FishEntity.class)
public class FishNitrogenMixin extends WaterCreatureEntity implements FishNitrogenAccessor {
    @Unique
    private static final TrackedData<Integer> NITROGEN_LEVEL = DataTracker.registerData(FishEntity.class, TrackedDataHandlerRegistry.INTEGER);
    @Unique
    private static final int NITROGEN_THRESHOLD = 1200;

    protected FishNitrogenMixin(EntityType<? extends WaterCreatureEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    protected void initDataTracker(CallbackInfo ci) {
        this.dataTracker.startTracking(NITROGEN_LEVEL, 0);
    }

    @Unique
    public int getNitrogenLevel() {
        return this.dataTracker.get(NITROGEN_LEVEL);
    }

    @Unique
    public void setNitrogenLevel(int level) {
        this.dataTracker.set(NITROGEN_LEVEL, Math.max(0, level));
    }

    @Inject(method = "tickMovement", at = @At("TAIL"))
    public void tickMovement(CallbackInfo ci) {
        int increase = getNitrogenIncreaseAmount();
        if (this.age % 100 == 0) {
            int decrease = 12;

            this.setNitrogenLevel(this.getNitrogenLevel() + increase - decrease);
            this.influenceNearbyEntities();

            checkNitrogenLevelForDamage();
        }
    }

    @Unique
    protected void influenceNearbyEntities() {
        List<Entity> nearbyEntities = this.getWorld().getEntitiesByClass(Entity.class, this.getBoundingBox().expand(8), entity -> entity != this);

        for (Entity entity : nearbyEntities) {
            if (floodFill(this, entity, 251, 4)) {
                if (entity instanceof FishEntity || entity instanceof HolometabolousAquaticEntity) {
                    this.setNitrogenLevel(this.getNitrogenLevel() + getNitrogenIncreaseAmount() / 2);
                }
            }
        }
    }

    @Unique
    protected int getNitrogenIncreaseAmount() {
        return 4;
    }

    @Unique
    private boolean floodFill(Entity entity1, Entity entity2, int maxDepth, int maxRange) {
        BlockPos start = entity1.getBlockPos();
        BlockPos target = entity2.getBlockPos();
        Set<BlockPos> visited = new HashSet<>();
        Queue<BlockPos> queue = new LinkedList<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty() && maxDepth-- > 0) {
            BlockPos current = queue.poll();

            if (current.equals(target)) {
                return true;
            }

            for (Direction direction : Direction.values()) {
                BlockPos neighbor = current.offset(direction);

                if (!start.isWithinDistance(neighbor, maxRange)) continue;

                if (!visited.contains(neighbor) && entity1.getWorld().getFluidState(neighbor).isIn((FluidTags.WATER))) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }

        return false;
    }

    @Unique
    private void checkNitrogenLevelForDamage() {
        int nitrogenLevel = this.getNitrogenLevel();
        if (nitrogenLevel > NITROGEN_THRESHOLD) {
            int excessNitrogen = nitrogenLevel - NITROGEN_THRESHOLD;
            float damageAmount = excessNitrogen / 4.0F;
            this.damage(this.getDamageSources().generic(), damageAmount);
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("NitrogenLevel", this.getNitrogenLevel());
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        this.setNitrogenLevel(nbt.getInt("NitrogenLevel"));
    }
}

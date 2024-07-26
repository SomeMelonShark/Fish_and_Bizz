package net.redmelon.fishandshiz.cclass;

import com.google.common.annotations.VisibleForTesting;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public abstract class LivebearerEntity extends SchoolingBreedEntity{
    @VisibleForTesting
    protected static int MAX_EGG_AGE = Math.abs(-12000);
    private int gestStage;
    protected static final TrackedData<Boolean> HAS_EGG = DataTracker.registerData(LivebearerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public LivebearerEntity(EntityType<? extends SchoolingBreedEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(HAS_EGG, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("HasEgg", this.hasEgg());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setHasEgg(nbt.getBoolean("HasEgg"));
    }

    public boolean hasEgg() {
        return this.dataTracker.get(HAS_EGG);
    }

    public void setHasEgg(boolean hasEgg) {
        this.dataTracker.set(HAS_EGG, hasEgg);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.getWorld().isClient && this.hasEgg()) {
            this.setStageAge(this.gestStage + 1);
            this.playSound(SoundEvents.BLOCK_SLIME_BLOCK_FALL, 0.05f, 0.1f);
        }
    }

    protected void setStageAge(int eggAge) {
        this.gestStage = eggAge;
        if ((this.gestStage >= MAX_EGG_AGE) && this.hasEgg()) {
            this.birth();
            this.setHasEgg(false);
        }
    }

    protected void birth() {
    }
}

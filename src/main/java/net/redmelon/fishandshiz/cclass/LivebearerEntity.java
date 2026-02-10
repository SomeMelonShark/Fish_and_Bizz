package net.redmelon.fishandshiz.cclass;

import com.google.common.annotations.VisibleForTesting;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
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
            this.setGestAge(this.gestStage + 1);
        }
        if (this.hasEgg()) {
            double d = this.random.nextGaussian() * 0.02;
            double e = this.random.nextGaussian() * 0.02;
            double f = this.random.nextGaussian() * 0.02;
            this.getWorld().addParticle(ParticleTypes.EGG_CRACK, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), d, e, f);
        }
    }

    protected void setGestAge(int eggAge) {
        this.gestStage = eggAge;
        if ((this.gestStage >= MAX_EGG_AGE) && this.hasEgg()) {
            this.birth();
            this.setHasEgg(false);
        }
    }

    protected void birth() {
    }
}

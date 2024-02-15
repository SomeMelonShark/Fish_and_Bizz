package net.redmelon.fishandshiz.entity.custom;

import net.minecraft.block.AbstractBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.entity.projectile.LlamaSpitEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.redmelon.fishandshiz.entity.ModEntities;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ArcherfishSpitEntity extends ProjectileEntity implements GeoAnimatable {
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    public ArcherfishSpitEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public ArcherfishSpitEntity(World world, ArcherfishEntity owner) {
        this((EntityType<? extends ArcherfishSpitEntity>) ModEntities.ARCHERFISH_SPIT, world);
        this.setOwner(owner);
        this.setPosition(owner.getX() - (double)(owner.getWidth() + 1.0f) * 0.5 * (double) MathHelper.sin(owner.bodyYaw * ((float)Math.PI / 180)), owner.getEyeY() - (double)0.1f, owner.getZ() + (double)(owner.getWidth() + 1.0f) * 0.5 * (double)MathHelper.cos(owner.bodyYaw * ((float)Math.PI / 180)));
    }

    private PlayState genericFlopController(AnimationState animationState) {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.archerfish_spit.spit", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
    }

    @Override
    public void tick() {
        super.tick();
        Vec3d vec3d = this.getVelocity();
        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        this.onCollision(hitResult);
        double d = this.getX() + vec3d.x;
        double e = this.getY() + vec3d.y;
        double f = this.getZ() + vec3d.z;
        this.updateRotation();
        float g = 0.99f;
        float h = 0.06f;
        if (this.getWorld().getStatesInBox(this.getBoundingBox()).noneMatch(AbstractBlock.AbstractBlockState::isAir)) {
            this.discard();
            return;
        }
        this.setVelocity(vec3d.multiply(0.99f));
        if (!this.hasNoGravity()) {
            this.setVelocity(this.getVelocity().add(0.0, -0.06f, 0.0));
        }
        this.setPosition(d, e, f);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = this.getOwner();
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)entity;
            entityHitResult.getEntity().damage(this.getDamageSources().mobProjectile(this, livingEntity), 2.0f);
            this.discard();
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.getWorld().isClient) {
            this.discard();
        }
    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);
        double d = packet.getVelocityX();
        double e = packet.getVelocityY();
        double f = packet.getVelocityZ();
        for (int i = 0; i < 20; ++i) {
            double g = 0.4 + 0.1 * (double)i;
            this.getWorld().addParticle(ParticleTypes.BUBBLE, this.getX(), this.getY(), this.getZ(), d * g, e, f * g);
        }
        this.setVelocity(d, e, f);
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController(this, "controller", 5, this::genericFlopController));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    @Override
    public double getTick(Object o) {
        return 0;
    }
}

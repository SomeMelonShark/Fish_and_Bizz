package net.redmelon.fishandshiz.mixin;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import net.redmelon.fishandshiz.item.ModItems;
import net.redmelon.fishandshiz.item.custom.ArcherfishGunItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityModel.class)
public class HeldArcherfishGunMixin<T extends LivingEntity> extends BipedEntityModel<T> {
    private static boolean isLoadedArcherfishGun(ItemStack stack) {
        return stack.isOf(ModItems.ARCHERFISH_GUN);
    }

    @Shadow @Final public ModelPart rightSleeve;

    @Shadow @Final public ModelPart leftSleeve;

    @Inject(method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
        public void setAngles(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci){
        ItemStack itemStack = livingEntity.getMainHandStack();
        if (HeldArcherfishGunMixin.isLoadedArcherfishGun(itemStack)) {
            this.rightArm.pitch = -((float) Math.PI / 2);
            this.rightArm.yaw = -((float) Math.PI / 12);
            this.rightSleeve.pitch = -((float) Math.PI / 2);
            this.rightSleeve.yaw = -((float) Math.PI / 12);
            this.leftArm.pitch = -((float) Math.PI / 2);
            this.leftArm.yaw = ((float) Math.PI / 4);
            this.leftSleeve.pitch = -((float) Math.PI / 2);
            this.leftSleeve.yaw = ((float) Math.PI / 4);
        }
    }

    public HeldArcherfishGunMixin(ModelPart root) {
        super(root);
    }
}

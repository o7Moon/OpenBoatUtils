package dev.o7moon.openboatutils.mixin;

import dev.o7moon.openboatutils.GetStepHeight;
import dev.o7moon.openboatutils.OpenBoatUtils;
import net.minecraft.entity.Entity;
//? >=1.21.3 {
/*import net.minecraft.entity.vehicle.AbstractBoatEntity;
*///?}
import net.minecraft.entity.vehicle.BoatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    //? >=1.21 {
    /*@Inject(method = "getStepHeight", at = @At("HEAD"), cancellable = true)
    public void getStepHeight(CallbackInfoReturnable<Float> cir) {
        //? >=1.21.3 {
        if ((Object) this instanceof AbstractBoatEntity) {
        //?}
        //? <=1.21 {
        /^if ((Object) this instanceof BoatEntity) {
        ^///?}
            GetStepHeight step = (GetStepHeight) this;
            cir.setReturnValue(step.getStepHeight());
            cir.cancel();
        }
    }
    *///?}

    @ModifyVariable(method = "adjustMovementForCollisions(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;", at = @At("STORE"), ordinal = 3)
    private boolean hookStepHeightOnGroundCheck(boolean original) {
        //? <=1.21 {
        if ((Object) this instanceof BoatEntity) {
        //?}
        //? >=1.21.3 {
        /*if ((Object) this instanceof AbstractBoatEntity) {
        *///?}
            if (OpenBoatUtils.canStepWhileFalling()) {
                return true;
            }
        }

        return original;
    }
}

package dev.o7moon.openboatutils.mixin;

import dev.o7moon.openboatutils.GetStepHeight;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.BoatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Inject(method = "getStepHeight", at = @At("HEAD"), cancellable = true)
    public void getStepHeight(CallbackInfoReturnable<Float> cir) {
        if ((Object) this instanceof BoatEntity) {
            GetStepHeight step = (GetStepHeight) this;
            cir.setReturnValue(step.getStepHeight());
            cir.cancel();
        }
    }
}

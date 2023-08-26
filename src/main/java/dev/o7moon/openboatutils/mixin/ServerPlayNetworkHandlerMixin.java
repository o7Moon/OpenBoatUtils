package dev.o7moon.openboatutils.mixin;

import dev.o7moon.openboatutils.OpenBoatUtils;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
    @Inject(method = "isMovementInvalid", at = @At("HEAD"), cancellable = true)
    private static void isMovementInvalid(CallbackInfoReturnable<Boolean> cir) {
        if (OpenBoatUtils.enabled) {
            cir.setReturnValue(false);
        }
    }
}

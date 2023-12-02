package dev.o7moon.openboatutils.mixin;

import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

// only for dedicated servers
@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin2 {
    @ModifyVariable(method = "onVehicleMove", at = @At("STORE"), name = {"bl3","bl4"})
    private boolean movedWronglyHook(boolean bl3){
        return false;
    }
}

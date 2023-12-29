package dev.o7moon.openboatutils.mixin;

import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

// only for dedicated servers
@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin2 {
    @ModifyVariable(method = "onVehicleMove", at = @At(value = "STORE", ordinal = 1), ordinal = 2)
    private boolean movedWronglyHook(boolean b){
        return false;
    }
    @ModifyVariable(method = "onVehicleMove", at = @At(value = "STORE"), ordinal = 3)
    private boolean movedWronglyHook2(boolean b){
        return true;
    }
}

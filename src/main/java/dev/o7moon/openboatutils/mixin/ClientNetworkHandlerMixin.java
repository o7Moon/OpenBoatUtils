package dev.o7moon.openboatutils.mixin;

import dev.o7moon.openboatutils.OpenBoatUtils;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
//? >=1.21.3 {
/*import net.minecraft.entity.vehicle.AbstractBoatEntity;
*///?}
import net.minecraft.entity.vehicle.BoatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientNetworkHandlerMixin {
    //? >=1.21.3 {
    
    /*@Redirect(method = {"onEntityPositionSync"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;updateTrackedPositionAndAngles(DDDFFI)V"))
    private void onUpdateTrackedPositionAndAngles2(Entity instance, double a, double b, double c, float d, float e, int interpolation){
        hook(instance, a, b, c, d, e, interpolation);
    }

    @Redirect(method = {"setPosition"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;updateTrackedPositionAndAngles(DDDFFI)V"))
    private static void staticonUpdateTrackedPositionAndAngles(Entity instance, double a, double b, double c, float d, float e, int interpolation){
        hook(instance, a, b, c, d, e, interpolation);
    }
    *///?}

    //? >=1.21 {
    /*@Redirect(method = {"onEntity"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;updateTrackedPositionAndAngles(DDDFFI)V"))
    private void onUpdateTrackedPositionAndAngles(Entity instance, double a, double b, double c, float d, float e, int interpolation){
        hook(instance, a, b, c, d, e, interpolation);
    }

    //? <1.21.3 {
    /^@Redirect(method = {"onEntityPosition"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;updateTrackedPositionAndAngles(DDDFFI)V"))
    private void onUpdateTrackedPositionAndAngles3(Entity instance, double a, double b, double c, float d, float e, int interpolation){
        hook(instance, a, b, c, d, e, interpolation);
    }
    ^///?}

    private static void hook(Entity instance, double a, double b, double c, float d, float e, int interpolation) {
        //? >=1.21.3 {
        if (instance instanceof AbstractBoatEntity && OpenBoatUtils.interpolationCompat) interpolation = 10;
        //?}
        //? <1.21.3 {
        /^if (instance instanceof BoatEntity && OpenBoatUtils.interpolationCompat) interpolation = 10;
        ^///?}
        instance.updateTrackedPositionAndAngles(a,b,c,d,e,interpolation);
    }
    *///?}

    //? <=1.20.1 {
    @Redirect(method = {"onEntity"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;updateTrackedPositionAndAngles(DDDFFIZ)V"))
    private void onUpdateTrackedPositionAndAngles(Entity instance, double a, double b, double c, float d, float e, int interpolation, boolean interpolate){
        hook(instance, a, b, c, d, e, interpolation, interpolate);
    }

    private static void hook(Entity instance, double a, double b, double c, float d, float e, int interpolation, boolean interpolate) {
        if (instance instanceof BoatEntity && OpenBoatUtils.interpolationCompat) interpolation = 10;
        instance.updateTrackedPositionAndAngles(a,b,c,d,e,interpolation, interpolate);
    }
    //?}
}

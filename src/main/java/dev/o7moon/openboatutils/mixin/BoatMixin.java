package dev.o7moon.openboatutils.mixin;

import dev.o7moon.openboatutils.OpenBoatUtils;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BoatEntity.class)
public abstract class BoatMixin {
    @Shadow
    abstract boolean checkBoatInWater();

    @Shadow
    @Nullable
    abstract BoatEntity.Location getUnderWaterLocation();

    @Shadow
    abstract BoatEntity.Location checkLocation();
    @Shadow
    float nearbySlipperiness;
    @Shadow
    double waterLevel;

    @Redirect(method = {"tick","getPaddleSoundEvent"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/vehicle/BoatEntity;checkLocation()Lnet/minecraft/entity/vehicle/BoatEntity$Location;"))
    BoatEntity.Location hookCheckLocation(BoatEntity instance) {
        instance.setStepHeight(0f);

        BoatEntity.Location loc = this.checkLocation();
        if (!OpenBoatUtils.enabled) return loc;
        MinecraftClient minecraft = MinecraftClient.getInstance();
        if (minecraft == null) return loc;
        PlayerEntity player = minecraft.player;
        if (player == null) return loc;
        Entity vehicle = player.getVehicle();
        if (!(vehicle instanceof BoatEntity)) return loc;
        BoatEntity boat = (BoatEntity)vehicle;

        if (!boat.equals(instance)) return loc;

        instance.setStepHeight(OpenBoatUtils.getStepSize());

        if (loc == BoatEntity.Location.UNDER_WATER || loc == BoatEntity.Location.UNDER_FLOWING_WATER) {
            if (OpenBoatUtils.waterElevation) {
                instance.setPosition(instance.getX(), this.waterLevel += 1.0, instance.getZ());
                Vec3d velocity = instance.getVelocity();
                instance.setVelocity(velocity.x, 0f, velocity.z);// parity with old boatutils, but maybe in the future
                // there should be an implementation with different y velocities here.
                return BoatEntity.Location.IN_WATER;
            }
            return loc;
        }

        if (this.checkBoatInWater()) {
            if (OpenBoatUtils.waterElevation) {
                Vec3d velocity = instance.getVelocity();
                instance.setVelocity(velocity.x, 0.0,velocity.z);
            }
            return BoatEntity.Location.IN_WATER;
        }

        if (loc == BoatEntity.Location.IN_AIR && OpenBoatUtils.airControl) {
            this.nearbySlipperiness = OpenBoatUtils.getBlockSlipperiness("minecraft:air");
            return BoatEntity.Location.ON_LAND;
        }

        if (loc == BoatEntity.Location.ON_LAND && OpenBoatUtils.jumpForce > 0f && minecraft.options.jumpKey.isPressed()) {
            Vec3d velocity = boat.getVelocity();
            boat.setVelocity(velocity.x, OpenBoatUtils.jumpForce, velocity.z);
        }

        return loc;
    }

    @Redirect(method = "getNearbySlipperiness", at = @At(value="INVOKE",target="Lnet/minecraft/block/Block;getSlipperiness()F"))
    float getFriction(Block block) {
        if (!OpenBoatUtils.enabled) return block.getSlipperiness();
        return OpenBoatUtils.getBlockSlipperiness(Registries.BLOCK.getId(block).toString());
    }

    @Inject(method = "fall", at = @At("HEAD"), cancellable = true)
    void fallHook(CallbackInfo ci) {
        if (!OpenBoatUtils.fallDamage) ci.cancel();
    }
}

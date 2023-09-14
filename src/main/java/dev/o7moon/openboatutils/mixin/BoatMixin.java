package dev.o7moon.openboatutils.mixin;

import dev.o7moon.openboatutils.OpenBoatUtils;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
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
    BoatEntity.Location location;
    @Shadow
    float velocityDecay;
    @Shadow
    float nearbySlipperiness;
    @Shadow
    double waterLevel;

    @Shadow
    float yawVelocity;
    @Shadow
    boolean pressingForward;
    @Shadow
    boolean pressingBack;
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

    @ModifyVariable(method = "updateVelocity", at = @At(value = "STORE"), ordinal = 1)
    private double updateVelocityHook(double e){
        if (!OpenBoatUtils.enabled) return e;

        return OpenBoatUtils.gravityForce;
    }

    @Redirect(method = "updatePaddles", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/vehicle/BoatEntity;yawVelocity:F", opcode = Opcodes.PUTFIELD))
    private void redirectYawVelocityIncrement(BoatEntity boat, float yawVelocity) {
        if (!OpenBoatUtils.enabled) {
            this.yawVelocity = yawVelocity;
            return;
        }
        float original_delta = yawVelocity - this.yawVelocity;
        // sign isn't needed here because the vanilla acceleration is exactly 1,
        // but I suppose this helps if mojang ever decides to change that value for some reason
        this.yawVelocity += MathHelper.sign(original_delta) * OpenBoatUtils.yawAcceleration;
    }

    // a whole lotta modifyconstants because mojang put the acceleration values in literals
    @ModifyConstant(method = "updatePaddles", constant = @Constant(floatValue = 0.04f, ordinal = 0))
    private float forwardsAccel(float original) {
        if (!OpenBoatUtils.enabled) return original;
        return OpenBoatUtils.forwardsAcceleration;
    }

    @ModifyConstant(method = "updatePaddles", constant = @Constant(floatValue = 0.005f, ordinal = 0))
    private float turnAccel(float original) {
        if (!OpenBoatUtils.enabled) return original;
        return OpenBoatUtils.turningForwardsAcceleration;
    }

    @ModifyConstant(method = "updatePaddles", constant = @Constant(floatValue = 0.005f, ordinal = 1))
    private float backwardsAccel(float original) {
        if (!OpenBoatUtils.enabled) return original;
        return OpenBoatUtils.backwardsAcceleration;
    }

    @Redirect(method = "updatePaddles", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/vehicle/BoatEntity;pressingForward:Z", opcode = Opcodes.GETFIELD, ordinal = 0))
    private boolean pressingForwardHook(BoatEntity instance) {
        if (!OpenBoatUtils.enabled || !OpenBoatUtils.allowAccelStacking) return this.pressingForward;
        return false;
    }

    @Redirect(method = "updatePaddles", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/vehicle/BoatEntity;pressingBack:Z", opcode = Opcodes.GETFIELD, ordinal = 0))
    private boolean pressingBackHook(BoatEntity instance) {
        if (!OpenBoatUtils.enabled || !OpenBoatUtils.allowAccelStacking) return this.pressingBack;
        return false;
    }

    // UNDER_FLOWING_WATER velocity decay
    @Redirect(method="updateVelocity", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/vehicle/BoatEntity;velocityDecay:F", opcode = Opcodes.PUTFIELD, ordinal = 2))
    private void velocityDecayHook1(BoatEntity boat, float orig) {
        if (!OpenBoatUtils.enabled || !OpenBoatUtils.underwaterControl) velocityDecay = orig;
        else velocityDecay = OpenBoatUtils.getBlockSlipperiness("minecraft:water");
    }

    // UNDER_WATER velocity decay
    @Redirect(method="updateVelocity", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/vehicle/BoatEntity;velocityDecay:F", opcode = Opcodes.PUTFIELD, ordinal = 3))
    private void velocityDecayHook2(BoatEntity boat, float orig) {
        if (!OpenBoatUtils.enabled || !OpenBoatUtils.underwaterControl) velocityDecay = orig;
        else velocityDecay = OpenBoatUtils.getBlockSlipperiness("minecraft:water");
    }
}

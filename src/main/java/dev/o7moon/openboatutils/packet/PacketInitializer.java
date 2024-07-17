package dev.o7moon.openboatutils.packet;

import dev.o7moon.openboatutils.OpenBoatUtils;
import dev.o7moon.openboatutils.packet.c2s.PacketVersion;
import dev.o7moon.openboatutils.packet.s2c.*;

public final class PacketInitializer {

    public static void initS2CPackets() {
        // :(
        PacketAccelerationStacking.initPacket();
        PacketAirControl.initPacket();
        PacketBackwardsAcceleration.initPacket();
        PacketBlockSlipperiness.initPacket();
        PacketClearAllSlipperiness.initPacket();
        PacketClearBlocksSlipperiness.initPacket();
        PacketCoyoteTime.initPacket();
        PacketDefaultSlipperiness.initPacket();
        PacketExclusiveMode.initPacket();
        PacketExclusiveModeSeries.initPacket();
        PacketFallDamage.initPacket();
        PacketForwardAcceleration.initPacket();
        PacketGravity.initPacket();
        PacketJumpForce.initPacket();
        PacketMode.initPacket();
        PacketModeSeries.initPacket();
        PacketPerBlockSetting.initPacket();
        PacketResendVersion.initPacket();
        PacketReset.initPacket();
        PacketStepHeight.initPacket();
        PacketSurfaceWaterControl.initPacket();
        PacketSwimForce.initPacket();
        PacketTurningForwardAcceleration.initPacket();
        PacketUnderwaterControl.initPacket();
        PacketWaterJumping.initPacket();
        PacketYawAcceleration.initPacket();
    }

    public static void initC2SPackets() {
        PacketVersion.initPacket();
    }
}

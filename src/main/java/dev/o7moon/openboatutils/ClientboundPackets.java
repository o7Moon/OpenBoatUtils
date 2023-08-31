package dev.o7moon.openboatutils;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import java.util.Arrays;

public enum ClientboundPackets {
    RESET,
    SET_STEP_HEIGHT,
    SET_DEFAULT_SLIPPERINESS,
    SET_BLOCKS_SLIPPERINESS,
    SET_BOAT_FALL_DAMAGE,
    SET_BOAT_WATER_ELEVATION,
    SET_AIR_CONTROL,
    SET_BOAT_JUMP_FORCE,
    SET_MODE,
    SET_GRAVITY;

    public static void registerHandlers(){
        ClientPlayNetworking.registerGlobalReceiver(OpenBoatUtils.settingsChannel, (client, handler, buf, responseSender) -> {
            try {
                short packetID = buf.readShort();
                switch (packetID) {
                    case 0:
                        OpenBoatUtils.resetSettings();
                        return;
                    case 1:
                        float stepSize = buf.readFloat();
                        OpenBoatUtils.setStepSize(stepSize);
                        return;
                    case 2:
                        float slipperiness = buf.readFloat();
                        OpenBoatUtils.setAllBlocksSlipperiness(slipperiness);
                        return;
                    case 3:
                        slipperiness = buf.readFloat();
                        String blocks = buf.readString();
                        String[] blocksArray = blocks.split(",");
                        OpenBoatUtils.setBlocksSlipperiness(Arrays.asList(blocksArray), slipperiness);
                        return;
                    case 4:
                        boolean fallDamage = buf.readBoolean();
                        OpenBoatUtils.setFallDamage(fallDamage);
                        return;
                    case 5:
                        boolean waterElevation = buf.readBoolean();
                        OpenBoatUtils.setWaterElevation(waterElevation);
                        return;
                    case 6:
                        boolean airControl = buf.readBoolean();
                        OpenBoatUtils.setAirControl(airControl);
                        return;
                    case 7:
                        float jumpForce = buf.readFloat();
                        OpenBoatUtils.setJumpForce(jumpForce);
                        return;
                    case 8:
                        short mode = buf.readShort();
                        Modes.setMode(Modes.values()[mode]);
                        return;
                    case 9:
                        double gravity = buf.readDouble();
                        OpenBoatUtils.setGravityForce(gravity);
                        return;
                }
            } catch (Exception E) {
                OpenBoatUtils.LOG.error("Error when handling clientbound openboatutils packet: ");
                for (StackTraceElement e : E.getStackTrace()){
                    OpenBoatUtils.LOG.error(e.toString());
                }
            }
        });
    }
}

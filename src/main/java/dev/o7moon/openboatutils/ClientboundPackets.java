package dev.o7moon.openboatutils;

//? >=1.21 {
/*import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
*///?}
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.PacketByteBuf;

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
    SET_GRAVITY,
    SET_YAW_ACCEL,
    SET_FORWARD_ACCEL,
    SET_BACKWARD_ACCEL,
    SET_TURN_ACCEL,
    ALLOW_ACCEL_STACKING,
    RESEND_VERSION,
    SET_UNDERWATER_CONTROL,
    SET_SURFACE_WATER_CONTROL,
    SET_EXCLUSIVE_MODE,
    SET_COYOTE_TIME,
    SET_WATER_JUMPING,
    SET_SWIM_FORCE,
    REMOVE_BLOCKS_SLIPPERINESS,
    CLEAR_SLIPPERINESS,
    MODE_SERIES,
    EXCLUSIVE_MODE_SERIES,
    SET_PER_BLOCK;

    public static void registerCodecs() {
        //? >=1.21 {
        /*PayloadTypeRegistry.playS2C().register(OpenBoatUtils.BytePayload.ID, OpenBoatUtils.BytePayload.CODEC);*/
        //? }
    }

    public static void registerHandlers(){
        //? <=1.20.1 {
        ClientPlayNetworking.registerGlobalReceiver(OpenBoatUtils.settingsChannel, (client, handler, buf, responseSender) -> {
            handlePacket(buf);
        });
        //?}
        //? >=1.21 {
        /*ClientPlayNetworking.registerGlobalReceiver(OpenBoatUtils.BytePayload.ID, ((payload, context) ->
                context.client().execute(() ->
                    handlePacket(new PacketByteBuf(Unpooled.wrappedBuffer(payload.data()))) )));
        *///?}
    }

    public static void handlePacket(PacketByteBuf buf) {
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
                case 10:
                    float accel = buf.readFloat();
                    OpenBoatUtils.setYawAcceleration(accel);
                    return;
                case 11:
                    accel = buf.readFloat();
                    OpenBoatUtils.setForwardsAcceleration(accel);
                    return;
                case 12:
                    accel = buf.readFloat();
                    OpenBoatUtils.setBackwardsAcceleration(accel);
                    return;
                case 13:
                    accel = buf.readFloat();
                    OpenBoatUtils.setTurningForwardsAcceleration(accel);
                    return;
                case 14:
                    boolean allowed = buf.readBoolean();
                    OpenBoatUtils.setAllowAccelStacking(allowed);
                    return;
                case 15:
                    OpenBoatUtils.sendVersionPacket();
                    return;
                case 16:
                    boolean enabled = buf.readBoolean();
                    OpenBoatUtils.setUnderwaterControl(enabled);
                    return;
                case 17:
                    enabled = buf.readBoolean();
                    OpenBoatUtils.setSurfaceWaterControl(enabled);
                    return;
                case 18:
                    mode = buf.readShort();
                    OpenBoatUtils.resetSettings();
                    Modes.setMode(Modes.values()[mode]);
                    return;
                case 19:
                    int time = buf.readInt();
                    OpenBoatUtils.setCoyoteTime(time);
                    return;
                case 20:
                    enabled = buf.readBoolean();
                    OpenBoatUtils.setWaterJumping(enabled);
                    return;
                case 21:
                    float force = buf.readFloat();
                    OpenBoatUtils.setSwimForce(force);
                    return;
                case 22:
                    blocks = buf.readString();
                    blocksArray = blocks.split(",");
                    OpenBoatUtils.removeBlocksSlipperiness(Arrays.asList(blocksArray));
                    return;
                case 23:
                    OpenBoatUtils.clearSlipperinessMap();
                    return;
                case 24:
                    short amount = buf.readShort();
                    for (int i = 0; i < amount; i++) {
                        mode = buf.readShort();
                        Modes.setMode(Modes.values()[mode]);
                    }
                    return;
                case 25:
                    OpenBoatUtils.resetSettings();
                    amount = buf.readShort();
                    for (int i = 0; i < amount; i++) {
                        mode = buf.readShort();
                        Modes.setMode(Modes.values()[mode]);
                    }
                    return;
                case 26:
                    short setting = buf.readShort();
                    float value = buf.readFloat();
                    blocks = buf.readString();
                    blocksArray = blocks.split(",");
                    OpenBoatUtils.setBlocksSetting(OpenBoatUtils.PerBlockSettingType.values()[setting], Arrays.asList(blocksArray), value);
                    return;
            }
        } catch (Exception E) {
            OpenBoatUtils.LOG.error("Error when handling clientbound openboatutils packet: ");
            for (StackTraceElement e : E.getStackTrace()){
                OpenBoatUtils.LOG.error(e.toString());
            }
        }
    }
}

package dev.o7moon.openboatutils;

import io.netty.buffer.ByteBuf;
//? >=1.21
/*import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;*/
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public enum ServerboundPackets {
    VERSION;

    public static void registerCodecs() {
        //? >=1.21 {
        /*PayloadTypeRegistry.playC2S().register(OpenBoatUtils.BytePayload.ID, OpenBoatUtils.BytePayload.CODEC);*/
        //? }
    }

    public static void registerHandlers(){
        //? <=1.20.1 {
        ServerPlayNetworking.registerGlobalReceiver(OpenBoatUtils.settingsChannel, (server, player, handler, buf, responseSender) -> {
            handlePacket(buf);
        });
        //?}
        //? >=1.21 {
        /*ServerPlayNetworking.registerGlobalReceiver(OpenBoatUtils.BytePayload.ID, ((payload, context) ->
                context.server().execute(() ->
                    handlePacket(payload.data()) )));
        *///?}
    }

    public static void handlePacket(ByteBuf buf) {
        try {
            short packetID = buf.readShort();
            switch (packetID) {
                case 0:
                    int versionID = buf.readInt();
                    OpenBoatUtils.LOG.info("OpenBoatUtils version received by server: "+versionID);
                    return;
            }
        } catch (Exception E) {
            OpenBoatUtils.LOG.error("Error when handling serverbound openboatutils packet: ");
            for (StackTraceElement e : E.getStackTrace()){
                OpenBoatUtils.LOG.error(e.toString());
            }
        }
    }
}

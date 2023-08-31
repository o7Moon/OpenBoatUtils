package dev.o7moon.openboatutils;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public enum ServerboundPackets {
    VERSION;

    public static void registerHandlers(){
        ServerPlayNetworking.registerGlobalReceiver(OpenBoatUtils.settingsChannel, (server, player, handler, buf, responseSender) -> {
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
        });
    }
}

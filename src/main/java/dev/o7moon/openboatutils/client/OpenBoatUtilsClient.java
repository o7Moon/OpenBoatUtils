package dev.o7moon.openboatutils.client;

import dev.o7moon.openboatutils.ClientboundPackets;
import dev.o7moon.openboatutils.OpenBoatUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

public class OpenBoatUtilsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientboundPackets.registerHandlers();

        ClientPlayConnectionEvents.INIT.register((handler, client) -> {
            OpenBoatUtils.resetSettings();
        });

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            OpenBoatUtils.sendVersionPacket();
        });
    }
}

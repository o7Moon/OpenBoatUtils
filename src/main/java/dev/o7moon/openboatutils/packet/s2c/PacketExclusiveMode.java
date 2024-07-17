package dev.o7moon.openboatutils.packet.s2c;

import dev.o7moon.openboatutils.Modes;
import dev.o7moon.openboatutils.OpenBoatUtils;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record PacketExclusiveMode(short value) implements CustomPayload {

    public static final PacketCodec<RegistryByteBuf, PacketExclusiveMode> CODEC = CustomPayload.codecOf(PacketExclusiveMode::write, PacketExclusiveMode::new);
    public static final Id<PacketExclusiveMode> ID = new Id<>(Identifier.of("openboatutils", "exclusive_mode"));

    public PacketExclusiveMode(RegistryByteBuf buf) {
        this(buf.readShort());
    }

    private void write(RegistryByteBuf buf) {
        buf.writeShort(value);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void initPacket() {
        PayloadTypeRegistry.playS2C().register(ID, CODEC);
        PayloadTypeRegistry.playC2S().register(ID, CODEC);
        ClientPlayNetworking.registerGlobalReceiver(ID, (payload, ctx) -> {
            OpenBoatUtils.resetSettings();
            Modes.setMode(Modes.values()[payload.value()]);
        });
    }
}

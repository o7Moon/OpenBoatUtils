package dev.o7moon.openboatutils.packet.s2c;

import dev.o7moon.openboatutils.Modes;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record PacketMode(short value) implements CustomPayload {

    public static final PacketCodec<RegistryByteBuf, PacketMode> CODEC = CustomPayload.codecOf(PacketMode::write, PacketMode::new);
    public static final Id<PacketMode> ID = new Id<>(Identifier.of("openboatutils", "mode"));

    public PacketMode(RegistryByteBuf buf) {
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
        ClientPlayNetworking.registerGlobalReceiver(ID, ((payload, ctx) -> Modes.setMode(Modes.values()[payload.value()])));
    }
}

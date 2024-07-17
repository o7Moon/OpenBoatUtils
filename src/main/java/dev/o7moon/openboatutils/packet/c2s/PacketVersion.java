package dev.o7moon.openboatutils.packet.c2s;

import dev.o7moon.openboatutils.OpenBoatUtils;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record PacketVersion(int id) implements CustomPayload {

    public static final PacketCodec<RegistryByteBuf, PacketVersion> CODEC = CustomPayload.codecOf(PacketVersion::write, PacketVersion::new);
    public static final Id<PacketVersion> ID = new Id<>(Identifier.of("openboatutils", "version"));

    public PacketVersion(RegistryByteBuf buf) {
        this(buf.readInt());
    }

    private void write(RegistryByteBuf buf) {
        buf.writeInt(id);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void initPacket() {
        PayloadTypeRegistry.playS2C().register(ID, CODEC);
        PayloadTypeRegistry.playC2S().register(ID, CODEC);
        ServerPlayNetworking.registerGlobalReceiver(ID, (payload, ctx) -> {
            OpenBoatUtils.LOG.info("OpenBoatUtils version received by server: {}", payload.id);
        });
    }
}

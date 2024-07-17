package dev.o7moon.openboatutils.packet.s2c;

import dev.o7moon.openboatutils.OpenBoatUtils;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record PacketResendVersion() implements CustomPayload {

    public static final PacketCodec<RegistryByteBuf, PacketResendVersion> CODEC = CustomPayload.codecOf(PacketResendVersion::write, PacketResendVersion::new);
    public static final Id<PacketResendVersion> ID = new Id<>(Identifier.of("openboatutils", "resend_version"));

    public PacketResendVersion(RegistryByteBuf buf) {
        this();
    }

    private void write(RegistryByteBuf buf) {
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void initPacket() {
        PayloadTypeRegistry.playS2C().register(ID, CODEC);
        PayloadTypeRegistry.playC2S().register(ID, CODEC);
        ClientPlayNetworking.registerGlobalReceiver(ID, (payload, ctx) -> OpenBoatUtils.sendVersionPacket());
    }
}

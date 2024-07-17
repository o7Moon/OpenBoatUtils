package dev.o7moon.openboatutils.packet.s2c;

import dev.o7moon.openboatutils.OpenBoatUtils;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record PacketGravity(double value) implements CustomPayload {

    public static final PacketCodec<RegistryByteBuf, PacketGravity> CODEC = CustomPayload.codecOf(PacketGravity::write, PacketGravity::new);
    public static final Id<PacketGravity> ID = new Id<>(Identifier.of("openboatutils", "gravity"));

    public PacketGravity(RegistryByteBuf buf) {
        this(buf.readDouble());
    }

    private void write(RegistryByteBuf buf) {
        buf.writeDouble(value);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void initPacket() {
        PayloadTypeRegistry.playS2C().register(ID, CODEC);
        PayloadTypeRegistry.playC2S().register(ID, CODEC);
        ClientPlayNetworking.registerGlobalReceiver(ID, (payload, ctx) -> OpenBoatUtils.setGravityForce(payload.value()));
    }
}

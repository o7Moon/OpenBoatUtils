package dev.o7moon.openboatutils.packet.s2c;

import dev.o7moon.openboatutils.OpenBoatUtils;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record PacketSwimForce(float value) implements CustomPayload {

    public static final PacketCodec<RegistryByteBuf, PacketSwimForce> CODEC = CustomPayload.codecOf(PacketSwimForce::write, PacketSwimForce::new);
    public static final Id<PacketSwimForce> ID = new Id<>(Identifier.of("openboatutils", "swim_force"));

    public PacketSwimForce(RegistryByteBuf buf) {
        this(buf.readFloat());
    }

    private void write(RegistryByteBuf buf) {
        buf.writeFloat(value);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void initPacket() {
        PayloadTypeRegistry.playS2C().register(ID, CODEC);
        PayloadTypeRegistry.playC2S().register(ID, CODEC);
        ClientPlayNetworking.registerGlobalReceiver(ID, (payload, ctx) -> OpenBoatUtils.setSwimForce(payload.value()));
    }
}

package dev.o7moon.openboatutils.packet.s2c;

import dev.o7moon.openboatutils.OpenBoatUtils;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record PacketAccelerationStacking(boolean value) implements CustomPayload {

    public static final PacketCodec<RegistryByteBuf, PacketAccelerationStacking> CODEC = CustomPayload.codecOf(PacketAccelerationStacking::write, PacketAccelerationStacking::new);
    public static final Id<PacketAccelerationStacking> ID = new Id<>(Identifier.of("openboatutils", "acceleration_stacking"));

    public PacketAccelerationStacking(RegistryByteBuf buf) {
        this(buf.readBoolean());
    }

    private void write(RegistryByteBuf buf) {
        buf.writeBoolean(value);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void initPacket() {
        PayloadTypeRegistry.playS2C().register(ID, CODEC);
        PayloadTypeRegistry.playC2S().register(ID, CODEC);
        ClientPlayNetworking.registerGlobalReceiver(ID, (payload, ctx) -> OpenBoatUtils.setAllowAccelStacking(payload.value));
    }
}

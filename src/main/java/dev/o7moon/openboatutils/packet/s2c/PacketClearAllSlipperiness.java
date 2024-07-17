package dev.o7moon.openboatutils.packet.s2c;

import dev.o7moon.openboatutils.OpenBoatUtils;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public record PacketClearAllSlipperiness() implements CustomPayload {

    public static final PacketCodec<RegistryByteBuf, PacketClearAllSlipperiness> CODEC = CustomPayload.codecOf(PacketClearAllSlipperiness::write, PacketClearAllSlipperiness::new);
    public static final Id<PacketClearAllSlipperiness> ID = new Id<>(Identifier.of("openboatutils", "clear_all_slipperiness"));

    public PacketClearAllSlipperiness(RegistryByteBuf buf) {
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
        ClientPlayNetworking.registerGlobalReceiver(ID, (payload, ctx) -> OpenBoatUtils.clearSlipperinessMap());
    }
}

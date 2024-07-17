package dev.o7moon.openboatutils.packet.s2c;

import dev.o7moon.openboatutils.Modes;
import dev.o7moon.openboatutils.OpenBoatUtils;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record PacketExclusiveModeSeries(short amount, short... modes) implements CustomPayload {

    public static final PacketCodec<RegistryByteBuf, PacketExclusiveModeSeries> CODEC = CustomPayload.codecOf(PacketExclusiveModeSeries::write, PacketExclusiveModeSeries::new);
    public static final Id<PacketExclusiveModeSeries> ID = new Id<>(Identifier.of("openboatutils", "exclusive_mode_series"));

    public PacketExclusiveModeSeries(RegistryByteBuf buf) {
        this(buf.readShort());
        for (int i = 0; i < amount; i++) {
            modes[i] = buf.readShort();
        }
    }

    private void write(RegistryByteBuf buf) {
        buf.writeShort(amount);
        for (short mode : modes) {
            buf.writeShort(mode);
        }
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
            for (short modeId : payload.modes) {
                Modes.setMode(Modes.values()[modeId]);
            }
        });
    }
}

package dev.o7moon.openboatutils.packet.s2c;

import dev.o7moon.openboatutils.OpenBoatUtils;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public record PacketClearBlocksSlipperiness(List<String> blocks) implements CustomPayload {

    public static final PacketCodec<RegistryByteBuf, PacketClearBlocksSlipperiness> CODEC = CustomPayload.codecOf(PacketClearBlocksSlipperiness::write, PacketClearBlocksSlipperiness::new);
    public static final Id<PacketClearBlocksSlipperiness> ID = new Id<>(Identifier.of("openboatutils", "clear_blocks_slipperiness"));

    public PacketClearBlocksSlipperiness(RegistryByteBuf buf) {
        this(Arrays.stream(buf.readString().split(",")).toList());
    }

    private void write(RegistryByteBuf buf) {
        StringBuilder sb = new StringBuilder();
        blocks.forEach(s -> sb.append(s).append(","));
        buf.writeString(sb.substring(0, sb.length() - 1));
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void initPacket() {
        PayloadTypeRegistry.playS2C().register(ID, CODEC);
        PayloadTypeRegistry.playC2S().register(ID, CODEC);
        ClientPlayNetworking.registerGlobalReceiver(ID, (payload, ctx) -> OpenBoatUtils.removeBlocksSlipperiness(payload.blocks()));
    }
}

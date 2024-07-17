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

public record PacketPerBlockSetting(short setting, float value, List<String> blocks) implements CustomPayload {

    public static final PacketCodec<RegistryByteBuf, PacketPerBlockSetting> CODEC = CustomPayload.codecOf(PacketPerBlockSetting::write, PacketPerBlockSetting::new);
    public static final Id<PacketPerBlockSetting> ID = new Id<>(Identifier.of("openboatutils", "per_block_setting"));

    public PacketPerBlockSetting(RegistryByteBuf buf) {
        this(buf.readShort(), buf.readFloat(), Arrays.stream(buf.readString().split(",")).toList());
    }

    private void write(RegistryByteBuf buf) {
        buf.writeShort(setting);
        buf.writeFloat(value);
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
        ClientPlayNetworking.registerGlobalReceiver(ID, (payload, ctx) -> OpenBoatUtils.setBlocksSetting(OpenBoatUtils.PerBlockSettingType.values()[payload.setting], payload.blocks, payload.value));
    }
}

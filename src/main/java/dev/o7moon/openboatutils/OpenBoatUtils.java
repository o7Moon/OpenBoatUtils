package dev.o7moon.openboatutils;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static net.minecraft.server.command.CommandManager.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class OpenBoatUtils implements ModInitializer {
    @Override
    public void onInitialize() {
        ServerPlayNetworking.registerGlobalReceiver(settingsChannel, (server, player, handler, buf, responseSender) -> {
            try {
                short packetID = buf.readShort();
                switch (packetID) {
                    case 0:
                        int versionID = buf.readInt();
                        LOG.info("OpenBoatUtils version received by server: "+versionID);
                        return;
                }
            } catch (Exception E) {
                LOG.error("Error when handling serverbound openboatutils packet: ");
                for (StackTraceElement e : E.getStackTrace()){
                    LOG.error(e.toString());
                }
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(settingsChannel, (client, handler, buf, responseSender) -> {
            try {
                short packetID = buf.readShort();
                switch (packetID) {
                    case 0:
                        resetSettings();
                        return;
                    case 1:
                        float stepSize = buf.readFloat();
                        setStepSize(stepSize);
                        return;
                    case 2:
                        float slipperiness = buf.readFloat();
                        setAllBlocksSlipperiness(slipperiness);
                        return;
                    case 3:
                        slipperiness = buf.readFloat();
                        String blocks = buf.readString();
                        String[] blocksArray = blocks.split(",");
                        setBlocksSlipperiness(Arrays.asList(blocksArray), slipperiness);
                        return;
                    case 4:
                        boolean fallDamage = buf.readBoolean();
                        setFallDamage(fallDamage);
                        return;
                    case 5:
                        boolean waterElevation = buf.readBoolean();
                        setWaterElevation(waterElevation);
                        return;
                    case 6:
                        boolean airControl = buf.readBoolean();
                        setAirControl(airControl);
                        return;
                    case 7:
                        float jumpForce = buf.readFloat();
                        setJumpForce(jumpForce);
                        return;
                    case 8:
                        short mode = buf.readShort();
                        Modes.setMode(Modes.values()[mode]);
                        return;
                }
            } catch (Exception E) {
                LOG.error("Error when handling clientbound openboatutils packet: ");
                for (StackTraceElement e : E.getStackTrace()){
                    LOG.error(e.toString());
                }
            }
        });

        ClientPlayConnectionEvents.INIT.register((handler, client) -> {
            resetSettings();
        });

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            sendVersionPacket();
        });

        SingleplayerCommands.registerCommands();
    }

    public static final Logger LOG = LoggerFactory.getLogger("OpenBoatUtils");

    public static final int VERSION = 0;

    public static final Identifier settingsChannel = new Identifier("openboatutils","settings");


    public static boolean enabled = false;
    public static boolean fallDamage = true;
    public static boolean waterElevation = false;
    public static boolean airControl = false;
    public static float defaultSlipperiness = 0.6f;
    public static float jumpForce = 0f;

    public static float stepSize = 0f;

    public static HashMap<String, Float> slipperinessMap = new HashMap<>(){{
        put("minecraft:slime",0.8f);
        put("minecraft:ice",0.98f);
        put("minecraft:packed_ice",0.98f);
        put("minecraft:blue_ice",0.989f);
        put("minecraft:frosted_ice",0.98f);
    }};


    public static void resetSettings(){
        enabled = false;
        stepSize = 0f;
        fallDamage = true;
        waterElevation = false;
        defaultSlipperiness = 0.6f;
        airControl = false;
        jumpForce = 0f;
        slipperinessMap = new HashMap<>(){{
            put("minecraft:slime",0.8f);
            put("minecraft:ice",0.98f);
            put("minecraft:packed_ice",0.98f);
            put("minecraft:blue_ice",0.989f);
            put("minecraft:frosted_ice",0.98f);
        }};
    }

    public static void setStepSize(float stepsize){
        enabled = true;
        stepSize = stepsize;
    }

    public static void setBlocksSlipperiness(List<String> blocks, float slipperiness){
        enabled = true;
        for (String block : blocks) {
            setBlockSlipperiness(block, slipperiness);
        }
    }

    public static void setAllBlocksSlipperiness(float slipperiness){
        enabled = true;
        defaultSlipperiness = slipperiness;
    }

    static void setBlockSlipperiness(String block, float slipperiness){
        slipperinessMap.put(block, slipperiness);
    }

    public static float getBlockSlipperiness(String block){
        if (slipperinessMap.containsKey(block)) return slipperinessMap.get(block);
        return defaultSlipperiness;
    }

    public static float getStepSize() {
        return stepSize;
    }

    public static void setFallDamage(boolean newValue) {
        enabled = true;
        fallDamage = newValue;
    }

    public static void setWaterElevation(boolean newValue) {
        enabled = true;
        waterElevation = newValue;
    }

    public static void setAirControl(boolean newValue) {
        enabled = true;
        airControl = newValue;
    }

    public static void setJumpForce(float newValue) {
        enabled = true;
        jumpForce = newValue;
    }

    public static void sendVersionPacket(){
        PacketByteBuf packet = PacketByteBufs.create();
        packet.writeShort(ServerboundPackets.VERSION.ordinal());
        packet.writeInt(VERSION);
        ClientPlayNetworking.send(settingsChannel, packet);
    }
}

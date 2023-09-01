package dev.o7moon.openboatutils;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class SingleplayerCommands {
    public static void registerCommands(){
        CommandRegistrationCallback.EVENT.register((dispatcher, registry, environment) -> {
            dispatcher.register(
                    literal("stepsize").then(
                            argument("size", FloatArgumentType.floatArg()).executes(ctx ->
                            {
                                ServerPlayerEntity player = ctx.getSource().getPlayer();
                                if (player == null) return 0;
                                PacketByteBuf packet = PacketByteBufs.create();
                                packet.writeShort(ClientboundPackets.SET_STEP_HEIGHT.ordinal());
                                packet.writeFloat(FloatArgumentType.getFloat(ctx, "size"));
                                ServerPlayNetworking.send(player, OpenBoatUtils.settingsChannel, packet);
                                return 1;
                            })
                    )
            );

            dispatcher.register(
                    literal("reset").executes(ctx -> {
                        ServerPlayerEntity player = ctx.getSource().getPlayer();
                        if (player == null) return 0;
                        PacketByteBuf packet = PacketByteBufs.create();
                        packet.writeShort(ClientboundPackets.RESET.ordinal());
                        ServerPlayNetworking.send(player, OpenBoatUtils.settingsChannel, packet);
                        return 1;
                    })
            );

            dispatcher.register(
                    literal("defaultslipperiness").then(argument("slipperiness", FloatArgumentType.floatArg()).executes(ctx -> {
                                ServerPlayerEntity player = ctx.getSource().getPlayer();
                                if (player == null) return 0;
                                PacketByteBuf packet = PacketByteBufs.create();
                                packet.writeShort(ClientboundPackets.SET_DEFAULT_SLIPPERINESS.ordinal());
                                packet.writeFloat(FloatArgumentType.getFloat(ctx, "slipperiness"));
                                ServerPlayNetworking.send(player, OpenBoatUtils.settingsChannel, packet);
                                return 1;
                            })
                    )
            );

            dispatcher.register(
                    literal("blockslipperiness").then(argument("slipperiness", FloatArgumentType.floatArg()).then(argument("blocks", StringArgumentType.greedyString()).executes(ctx->{
                        ServerPlayerEntity player = ctx.getSource().getPlayer();
                        if (player == null) return 0;
                        PacketByteBuf packet = PacketByteBufs.create();
                        packet.writeShort(ClientboundPackets.SET_BLOCKS_SLIPPERINESS.ordinal());
                        packet.writeFloat(FloatArgumentType.getFloat(ctx,"slipperiness"));
                        packet.writeString(StringArgumentType.getString(ctx,"blocks").trim());
                        ServerPlayNetworking.send(player, OpenBoatUtils.settingsChannel, packet);
                        return 1;
                    })))
            );

            dispatcher.register(
                    literal("aircontrol").then(argument("enabled", BoolArgumentType.bool()).executes(ctx-> {
                        ServerPlayerEntity player = ctx.getSource().getPlayer();
                        if (player == null) return 0;
                        PacketByteBuf packet = PacketByteBufs.create();
                        packet.writeShort(ClientboundPackets.SET_AIR_CONTROL.ordinal());
                        packet.writeBoolean(BoolArgumentType.getBool(ctx, "enabled"));
                        ServerPlayNetworking.send(player, OpenBoatUtils.settingsChannel, packet);
                        return 1;
                    }))
            );

            dispatcher.register(
                    literal("waterelevation").then(argument("enabled", BoolArgumentType.bool()).executes(ctx -> {
                        ServerPlayerEntity player = ctx.getSource().getPlayer();
                        if (player == null) return 0;
                        PacketByteBuf packet = PacketByteBufs.create();
                        packet.writeShort(ClientboundPackets.SET_BOAT_WATER_ELEVATION.ordinal());
                        packet.writeBoolean(BoolArgumentType.getBool(ctx, "enabled"));
                        ServerPlayNetworking.send(player, OpenBoatUtils.settingsChannel, packet);
                        return 1;
                    }))
            );

            dispatcher.register(
                    literal("falldamage").then(argument("enabled", BoolArgumentType.bool()).executes(ctx -> {
                        ServerPlayerEntity player = ctx.getSource().getPlayer();
                        if (player == null) return 0;
                        PacketByteBuf packet = PacketByteBufs.create();
                        packet.writeShort(ClientboundPackets.SET_BOAT_FALL_DAMAGE.ordinal());
                        packet.writeBoolean(BoolArgumentType.getBool(ctx, "enabled"));
                        ServerPlayNetworking.send(player, OpenBoatUtils.settingsChannel, packet);
                        return 1;
                    }))
            );

            dispatcher.register(
                    literal("jumpforce").then(argument("force", FloatArgumentType.floatArg()).executes(ctx -> {
                                ServerPlayerEntity player = ctx.getSource().getPlayer();
                                if (player == null) return 0;
                                PacketByteBuf packet = PacketByteBufs.create();
                                packet.writeShort(ClientboundPackets.SET_BOAT_JUMP_FORCE.ordinal());
                                packet.writeFloat(FloatArgumentType.getFloat(ctx, "force"));
                                ServerPlayNetworking.send(player, OpenBoatUtils.settingsChannel, packet);
                                return 1;
                            })
                    )
            );

            dispatcher.register(
                    literal("boatmode").then(argument("mode", StringArgumentType.string()).executes(ctx-> {
                        ServerPlayerEntity player = ctx.getSource().getPlayer();
                        if (player == null) return 0;
                        Modes mode;
                        try {
                            mode = Modes.valueOf(StringArgumentType.getString(ctx, "mode"));
                        } catch (Exception e) {
                            String valid_modes = "";
                            for (Modes m : Modes.values()) {
                                valid_modes += m.toString() + " ";
                            }
                            ctx.getSource().sendMessage(Text.literal("Invalid mode! Valid modes are: "+valid_modes));
                            return 0;
                        }
                        PacketByteBuf packet = PacketByteBufs.create();
                        packet.writeShort(ClientboundPackets.SET_MODE.ordinal());
                        packet.writeShort(mode.ordinal());
                        ServerPlayNetworking.send(player, OpenBoatUtils.settingsChannel, packet);
                        return 1;
                    }))

            );

            dispatcher.register(
                    literal("boatgravity").then(argument("gravity", DoubleArgumentType.doubleArg()).executes(ctx->{
                        ServerPlayerEntity player = ctx.getSource().getPlayer();
                        if (player == null) return 0;
                        PacketByteBuf packet = PacketByteBufs.create();
                        packet.writeShort(ClientboundPackets.SET_GRAVITY.ordinal());
                        packet.writeDouble(DoubleArgumentType.getDouble(ctx, "gravity"));
                        ServerPlayNetworking.send(player, OpenBoatUtils.settingsChannel, packet);
                        return 1;
                    }))
            );

            dispatcher.register(
                    literal("setyawaccel").then(argument("accel", FloatArgumentType.floatArg()).executes(ctx -> {
                                ServerPlayerEntity player = ctx.getSource().getPlayer();
                                if (player == null) return 0;
                                PacketByteBuf packet = PacketByteBufs.create();
                                packet.writeShort(ClientboundPackets.SET_YAW_ACCEL.ordinal());
                                packet.writeFloat(FloatArgumentType.getFloat(ctx, "accel"));
                                ServerPlayNetworking.send(player, OpenBoatUtils.settingsChannel, packet);
                                return 1;
                            })
                    )
            );

            dispatcher.register(
                    literal("setforwardaccel").then(argument("accel", FloatArgumentType.floatArg()).executes(ctx -> {
                                ServerPlayerEntity player = ctx.getSource().getPlayer();
                                if (player == null) return 0;
                                PacketByteBuf packet = PacketByteBufs.create();
                                packet.writeShort(ClientboundPackets.SET_FORWARD_ACCEL.ordinal());
                                packet.writeFloat(FloatArgumentType.getFloat(ctx, "accel"));
                                ServerPlayNetworking.send(player, OpenBoatUtils.settingsChannel, packet);
                                return 1;
                            })
                    )
            );

            dispatcher.register(
                    literal("setbackwardaccel").then(argument("accel", FloatArgumentType.floatArg()).executes(ctx -> {
                                ServerPlayerEntity player = ctx.getSource().getPlayer();
                                if (player == null) return 0;
                                PacketByteBuf packet = PacketByteBufs.create();
                                packet.writeShort(ClientboundPackets.SET_BACKWARD_ACCEL.ordinal());
                                packet.writeFloat(FloatArgumentType.getFloat(ctx, "accel"));
                                ServerPlayNetworking.send(player, OpenBoatUtils.settingsChannel, packet);
                                return 1;
                            })
                    )
            );

            dispatcher.register(
                    literal("setturnforwardaccel").then(argument("accel", FloatArgumentType.floatArg()).executes(ctx -> {
                                ServerPlayerEntity player = ctx.getSource().getPlayer();
                                if (player == null) return 0;
                                PacketByteBuf packet = PacketByteBufs.create();
                                packet.writeShort(ClientboundPackets.SET_TURN_ACCEL.ordinal());
                                packet.writeFloat(FloatArgumentType.getFloat(ctx, "accel"));
                                ServerPlayNetworking.send(player, OpenBoatUtils.settingsChannel, packet);
                                return 1;
                            })
                    )
            );

            dispatcher.register(
                    literal("allowaccelstacking").then(argument("allow", BoolArgumentType.bool()).executes(ctx -> {
                        ServerPlayerEntity player = ctx.getSource().getPlayer();
                        if (player == null) return 0;
                        PacketByteBuf packet = PacketByteBufs.create();
                        packet.writeShort(ClientboundPackets.ALLOW_ACCEL_STACKING.ordinal());
                        packet.writeBoolean(BoolArgumentType.getBool(ctx, "allow"));
                        ServerPlayNetworking.send(player, OpenBoatUtils.settingsChannel, packet);
                        return 1;
                    }))
            );
        });
    }
}

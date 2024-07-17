package dev.o7moon.openboatutils;

import com.mojang.brigadier.arguments.*;
import dev.o7moon.openboatutils.packet.s2c.*;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Arrays;
import java.util.List;

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
                                ServerPlayNetworking.send(player, new PacketStepHeight(FloatArgumentType.getFloat(ctx, "size")));
                                return 1;
                            })
                    )
            );

            dispatcher.register(
                    literal("reset").executes(ctx -> {
                        ServerPlayerEntity player = ctx.getSource().getPlayer();
                        if (player == null) return 0;
                        ServerPlayNetworking.send(player, new PacketReset());
                        return 1;
                    })
            );

            dispatcher.register(
                    literal("defaultslipperiness").then(argument("slipperiness", FloatArgumentType.floatArg()).executes(ctx -> {
                                ServerPlayerEntity player = ctx.getSource().getPlayer();
                                if (player == null) return 0;
                                PacketByteBuf packet = PacketByteBufs.create();
                                ServerPlayNetworking.send(player, new PacketDefaultSlipperiness(FloatArgumentType.getFloat(ctx, "slipperiness")));
                                return 1;
                            })
                    )
            );

            dispatcher.register(
                    literal("blockslipperiness").then(argument("slipperiness", FloatArgumentType.floatArg()).then(argument("blocks", StringArgumentType.greedyString()).executes(ctx->{
                        ServerPlayerEntity player = ctx.getSource().getPlayer();
                        if (player == null) return 0;
                        String blocksArg = StringArgumentType.getString(ctx,"blocks").trim();
                        List<String> blocksAsList = Arrays.stream(blocksArg.split(",")).toList();
                        ServerPlayNetworking.send(player, new PacketBlockSlipperiness(FloatArgumentType.getFloat(ctx,"slipperiness"), blocksAsList));
                        return 1;
                    })))
            );

            dispatcher.register(
                    literal("aircontrol").then(argument("enabled", BoolArgumentType.bool()).executes(ctx-> {
                        ServerPlayerEntity player = ctx.getSource().getPlayer();
                        if (player == null) return 0;
                        ServerPlayNetworking.send(player, new PacketAirControl(BoolArgumentType.getBool(ctx, "enabled")));
                        return 1;
                    }))
            );

            dispatcher.register(
                    literal("waterelevation").then(argument("enabled", BoolArgumentType.bool()).executes(ctx -> {
                        ServerPlayerEntity player = ctx.getSource().getPlayer();
                        if (player == null) return 0;
                        ServerPlayNetworking.send(player, new PacketWaterElevation(BoolArgumentType.getBool(ctx, "enabled")));
                        return 1;
                    }))
            );

            dispatcher.register(
                    literal("falldamage").then(argument("enabled", BoolArgumentType.bool()).executes(ctx -> {
                        ServerPlayerEntity player = ctx.getSource().getPlayer();
                        if (player == null) return 0;
                        PacketByteBuf packet = PacketByteBufs.create();
                        ServerPlayNetworking.send(player, new PacketFallDamage(BoolArgumentType.getBool(ctx, "enabled")));
                        return 1;
                    }))
            );

            dispatcher.register(
                    literal("jumpforce").then(argument("force", FloatArgumentType.floatArg()).executes(ctx -> {
                                ServerPlayerEntity player = ctx.getSource().getPlayer();
                                if (player == null) return 0;
                                ServerPlayNetworking.send(player, new PacketJumpForce(FloatArgumentType.getFloat(ctx, "force")));
                                return 1;
                            })
                    )
            );

            dispatcher.register(
                    literal("boatmode").then(argument("modeId", StringArgumentType.string()).executes(ctx-> {
                        ServerPlayerEntity player = ctx.getSource().getPlayer();
                        if (player == null) return 0;
                        Modes mode;
                        try {
                            mode = Modes.valueOf(StringArgumentType.getString(ctx, "modeId"));
                        } catch (Exception e) {
                            String valid_modes = "";
                            for (Modes m : Modes.values()) {
                                valid_modes += m.toString() + " ";
                            }
                            ctx.getSource().sendMessage(Text.literal("Invalid modeId! Valid modes are: "+valid_modes));
                            return 0;
                        }
                        ServerPlayNetworking.send(player, new PacketMode((short) mode.ordinal()));
                        return 1;
                    }))
            );

            dispatcher.register(
                    literal("boatgravity").then(argument("gravity", DoubleArgumentType.doubleArg()).executes(ctx->{
                        ServerPlayerEntity player = ctx.getSource().getPlayer();
                        if (player == null) return 0;
                        ServerPlayNetworking.send(player, new PacketGravity(DoubleArgumentType.getDouble(ctx, "gravity")));
                        return 1;
                    }))
            );

            dispatcher.register(
                    literal("setyawaccel").then(argument("accel", FloatArgumentType.floatArg()).executes(ctx -> {
                                ServerPlayerEntity player = ctx.getSource().getPlayer();
                                if (player == null) return 0;
                                ServerPlayNetworking.send(player, new PacketYawAcceleration(FloatArgumentType.getFloat(ctx, "accel")));
                                return 1;
                            })
                    )
            );

            dispatcher.register(
                    literal("setforwardaccel").then(argument("accel", FloatArgumentType.floatArg()).executes(ctx -> {
                                ServerPlayerEntity player = ctx.getSource().getPlayer();
                                if (player == null) return 0;
                                ServerPlayNetworking.send(player, new PacketForwardAcceleration(FloatArgumentType.getFloat(ctx, "accel")));
                                return 1;
                            })
                    )
            );

            dispatcher.register(
                    literal("setbackwardaccel").then(argument("accel", FloatArgumentType.floatArg()).executes(ctx -> {
                                ServerPlayerEntity player = ctx.getSource().getPlayer();
                                if (player == null) return 0;
                                ServerPlayNetworking.send(player, new PacketBackwardsAcceleration(FloatArgumentType.getFloat(ctx, "accel")));
                                return 1;
                            })
                    )
            );

            dispatcher.register(
                    literal("setturnforwardaccel").then(argument("accel", FloatArgumentType.floatArg()).executes(ctx -> {
                                ServerPlayerEntity player = ctx.getSource().getPlayer();
                                if (player == null) return 0;
                                ServerPlayNetworking.send(player, new PacketTurningForwardAcceleration(FloatArgumentType.getFloat(ctx, "accel")));
                                return 1;
                            })
                    )
            );

            dispatcher.register(
                    literal("allowaccelstacking").then(argument("allow", BoolArgumentType.bool()).executes(ctx -> {
                        ServerPlayerEntity player = ctx.getSource().getPlayer();
                        if (player == null) return 0;
                        ServerPlayNetworking.send(player, new PacketAccelerationStacking(BoolArgumentType.getBool(ctx, "allow")));
                        return 1;
                    }))
            );

            dispatcher.register(literal("sendversionpacket").executes(ctx->{
                ServerPlayerEntity player = ctx.getSource().getPlayer();
                if (player == null) return 0;
                ServerPlayNetworking.send(player, new PacketResendVersion());
                return 1;
            }));

            dispatcher.register(
                literal("underwatercontrol").then(argument("enabled",BoolArgumentType.bool()).executes(ctx -> {
                    ServerPlayerEntity player = ctx.getSource().getPlayer();
                    if (player == null) return 0;
                    ServerPlayNetworking.send(player, new PacketUnderwaterControl(BoolArgumentType.getBool(ctx, "enabled")));
                    return 1;
                }))
            );

            dispatcher.register(
                    literal("surfacewatercontrol").then(argument("enabled", BoolArgumentType.bool()).executes(ctx -> {
                        ServerPlayerEntity player = ctx.getSource().getPlayer();
                        if (player == null) return 0;
                        ServerPlayNetworking.send(player, new PacketSurfaceWaterControl(BoolArgumentType.getBool(ctx, "enabled")));
                        return 1;
                    }))
            );

            dispatcher.register(
                    literal("exclusiveboatmode").then(argument("modeId",StringArgumentType.string()).executes(ctx->{
                        ServerPlayerEntity player = ctx.getSource().getPlayer();
                        if (player == null) return 0;
                        Modes mode;
                        try {
                            mode = Modes.valueOf(StringArgumentType.getString(ctx, "modeId"));
                        } catch (Exception e) {
                            String valid_modes = "";
                            for (Modes m : Modes.values()) {
                                valid_modes += m.toString() + " ";
                            }
                            ctx.getSource().sendMessage(Text.literal("Invalid modeId! Valid modes are: "+valid_modes));
                            return 0;
                        }
                        ServerPlayNetworking.send(player, new PacketExclusiveMode((short) mode.ordinal()));
                        return 1;
                    }))
            );

            dispatcher.register(
                    literal("coyotetime").then(argument("ticks", IntegerArgumentType.integer()).executes(ctx->{
                        ServerPlayerEntity player = ctx.getSource().getPlayer();
                        if (player == null) return 0;
                        int time = IntegerArgumentType.getInteger(ctx,"ticks");
                        ServerPlayNetworking.send(player, new PacketCoyoteTime(time));
                        return 1;
                    }))
            );

            dispatcher.register(
                    literal("waterjumping").then(argument("enabled", BoolArgumentType.bool()).executes(ctx -> {
                        ServerPlayerEntity player = ctx.getSource().getPlayer();
                        if (player == null) return 0;
                        ServerPlayNetworking.send(player, new PacketWaterJumping(BoolArgumentType.getBool(ctx, "enabled")));
                        return 1;
                    }))
            );

            dispatcher.register(
                    literal("swimforce").then(argument("force", FloatArgumentType.floatArg()).executes(ctx -> {
                                ServerPlayerEntity player = ctx.getSource().getPlayer();
                                if (player == null) return 0;
                                ServerPlayNetworking.send(player, new PacketSwimForce(FloatArgumentType.getFloat(ctx, "force")));
                                return 1;
                            })
                    )
            );

            dispatcher.register(
                    literal("removeblockslipperiness").then(argument("blocks", StringArgumentType.greedyString()).executes(ctx->{
                        ServerPlayerEntity player = ctx.getSource().getPlayer();
                        if (player == null) return 0;
                        String blocks = StringArgumentType.getString(ctx,"blocks").trim();
                        List<String> blocksAsList = Arrays.stream(blocks.split(",")).toList();
                        ServerPlayNetworking.send(player, new PacketClearBlocksSlipperiness(blocksAsList));
                        return 1;
                    }))
            );

            dispatcher.register(
                    literal("clearslipperiness").executes(ctx->{
                        ServerPlayerEntity player = ctx.getSource().getPlayer();
                        if (player == null) return 0;
                        ServerPlayNetworking.send(player, new PacketClearAllSlipperiness());
                        return 1;
                    })
            );

            dispatcher.register(
                    literal("modeseries").then(argument("modes", StringArgumentType.greedyString()).executes(ctx-> {
                        ServerPlayerEntity player = ctx.getSource().getPlayer();
                        if (player == null) return 0;
                        Modes mode;
                        String[] strs = StringArgumentType.getString(ctx, "modes").split(",");
                        short[] modes = new short[strs.length];
                        for (int i = 0; i < strs.length; i++) {
                            try {
                                mode = Modes.valueOf(strs[i].trim());
                            } catch (Exception e) {
                                String valid_modes = "";
                                for (Modes m : Modes.values()) {
                                    valid_modes += m.toString() + " ";
                                }
                                ctx.getSource().sendMessage(Text.literal("Invalid modeId! Valid modes are: "+valid_modes));
                                return 0;
                            }
                            modes[i] = (short) mode.ordinal();
                        }
                        ServerPlayNetworking.send(player, new PacketModeSeries((short) strs.length, modes));
                        return 1;
                    }))
            );

            dispatcher.register(
                    literal("exclusivemodeseries").then(argument("modes", StringArgumentType.greedyString()).executes(ctx-> {
                        ServerPlayerEntity player = ctx.getSource().getPlayer();
                        if (player == null) return 0;
                        Modes mode;
                        String[] strs = StringArgumentType.getString(ctx, "modes").split(",");
                        short[] modes = new short[strs.length];
                        for (int i = 0; i < strs.length; i++) {
                            try {
                                mode = Modes.valueOf(strs[i].trim());
                            } catch (Exception e) {
                                String valid_modes = "";
                                for (Modes m : Modes.values()) {
                                    valid_modes += m.toString() + " ";
                                }
                                ctx.getSource().sendMessage(Text.literal("Invalid modeId! Valid modes are: "+valid_modes));
                                return 0;
                            }
                            modes[i] = (short) mode.ordinal();
                        }
                        ServerPlayNetworking.send(player, new PacketExclusiveModeSeries((short) strs.length, modes));
                        return 1;
                    }))
            );

            dispatcher.register(
                    literal("setblocksetting").then(argument("setting", StringArgumentType.string()).then(argument("value", FloatArgumentType.floatArg()).then(argument("blocks", StringArgumentType.greedyString()).executes(ctx -> {
                        ServerPlayerEntity player = ctx.getSource().getPlayer();
                        if (player == null) return 0;
                        OpenBoatUtils.PerBlockSettingType setting;
                        try {
                            setting = OpenBoatUtils.PerBlockSettingType.valueOf(StringArgumentType.getString(ctx, "setting"));
                        } catch (Exception e) {
                            String valid_settings = "";
                            for (OpenBoatUtils.PerBlockSettingType s : OpenBoatUtils.PerBlockSettingType.values()) {
                                valid_settings += s.toString() + " ";
                            }
                            ctx.getSource().sendMessage(Text.literal("Invalid setting! Valid settings are: "+valid_settings));
                            return 0;
                        }
                        float value = FloatArgumentType.getFloat(ctx, "value");
                        String blocks = StringArgumentType.getString(ctx, "blocks");
                        List<String> blocksAsList = Arrays.stream(blocks.split(",")).toList();
                        ServerPlayNetworking.send(player, new PacketPerBlockSetting((short) setting.ordinal(), value, blocksAsList));
                        return 1;
                    }))))
            );
        });
    }
}

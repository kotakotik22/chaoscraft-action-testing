package com.kotakotik.chaoscraft.commands;

import com.kotakotik.chaoscraft.config.Config;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class CommandChaosCraftConfig extends ChaosCraftCommand {
    @Override
    public ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> command = Commands.literal("config");
        command.requires(cs -> cs.hasPermissionLevel(2));
        command.then(new GeneralConfig().register(dispatcher));
        return command;
    }

    public static class GeneralConfig extends ChaosCraftCommand {
        @Override
        public ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
            LiteralArgumentBuilder<CommandSource> command = Commands.literal("general");
            command.requires(cs -> cs.hasPermissionLevel(2));
            for(Config.ConfigInfo info : Config.registeredConfig) {
                command.then(new EachConfig(info).register(dispatcher));
            }
            return command;
        }

        public static class EachConfig extends ChaosCraftCommand {
            public final Config.ConfigInfo info;

            public EachConfig(Config.ConfigInfo info) {
                this.info = info;
            }

            public int setValue(CommandContext<?> ctx) {
                Object val;
                switch (info.type) {
                    case INT:
                        val = IntegerArgumentType.getInteger(ctx, "value");
                        break;
                    case BOOL:
                        val = BoolArgumentType.getBool(ctx, "value");
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + info.type);
                }
                info.configValue.set(val);
                return 1;
            }

            @Override
            public ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
                LiteralArgumentBuilder<CommandSource> command = Commands.literal(info.name);
                command.requires(cs -> cs.hasPermissionLevel(2));
                ArgumentType type;
                switch (info.type) {
                    case BOOL:
                        type = BoolArgumentType.bool();
                        break;
                    case INT:
                        type = IntegerArgumentType.integer();
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + info.type);
                }
                command.then(Commands.argument("value", type).executes(this::setValue));
                return command;
            }
        }
    }
}

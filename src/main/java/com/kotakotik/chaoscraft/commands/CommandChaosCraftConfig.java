package com.kotakotik.chaoscraft.commands;

import com.kotakotik.chaoscraft.config.CType;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.ForgeConfigSpec;

import java.text.MessageFormat;

public class CommandChaosCraftConfig extends ChaosCraftCommand {
    @Override
    public ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> command = Commands.literal("config");
        command.requires(cs -> cs.hasPermissionLevel(2));
        command.then(new GeneralConfig().register(dispatcher));
        return command;
    }

    public abstract static class ConfigSetter<T> extends ChaosCraftCommand {
        public final T info;

        public ConfigSetter(T info) {
            this.info = info;
        }

        public abstract String getDescription();
        public abstract String getName();
        public abstract CType getType();
        public abstract ForgeConfigSpec.ConfigValue getConfigValue();
        public abstract String getDefault();

        public ITextComponent getCompleteSetValueMessage(CommandContext<CommandSource> context, Object val) {
            return new StringTextComponent(MessageFormat.format("value of {0} set to {1}", getName(), val));
        }

        public ITextComponent getInfoMessage(CommandContext<CommandSource> context, Object val) {
            return new StringTextComponent("value of " + getName() + " is " + val + "\ndefault value: " + getDefault()
                    + "\ndescription: " + getDescription());
        }

        @Override
        public ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
            LiteralArgumentBuilder<CommandSource> command = Commands.literal(getName());

            command.requires(cs -> cs.hasPermissionLevel(2));

            ArgumentType type;
            switch (getType()) {
                case BOOL:
                    type = BoolArgumentType.bool();
                    break;
                case INT:
                    type = IntegerArgumentType.integer();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + getType());
            }

            command.then(Commands.argument("value", type).executes(this::setValue));

            return command;
        }

        public int setValue(CommandContext<CommandSource> ctx) {
            Object val;
            switch (getType()) {
                case INT:
                    val = IntegerArgumentType.getInteger(ctx, "value");
                    break;
                case BOOL:
                    val = BoolArgumentType.getBool(ctx, "value");
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + getType());
            }
            getConfigValue().set(val);
            ctx.getSource().sendFeedback(getCompleteSetValueMessage(ctx, val), false);
            return 1;
        }

        @Override // show info
        public int run(CommandContext<CommandSource> context) {
            context.getSource().sendFeedback(getInfoMessage(context, getConfigValue().get()), false);
            return 1;
        }
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

        public static class EachConfig extends ConfigSetter<Config.ConfigInfo> {
            public EachConfig(Config.ConfigInfo info) {
                super(info);
            }

            @Override
            public String getDescription() {
                return info.desc;
            }

            @Override
            public String getName() {
                return info.name;
            }

            @Override
            public CType getType() {
                return info.type;
            }

            @Override
            public ForgeConfigSpec.ConfigValue getConfigValue() {
                return info.configValue;
            }

            @Override
            public String getDefault() {
                return (String) info.defauld;
            }
        }
    }
}

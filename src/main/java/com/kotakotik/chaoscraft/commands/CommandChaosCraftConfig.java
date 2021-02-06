package com.kotakotik.chaoscraft.commands;

import com.kotakotik.chaoscraft.chaos_handlers.ChaosEvent;
import com.kotakotik.chaoscraft.chaos_handlers.ChaosEventHandler;
import com.kotakotik.chaoscraft.chaos_handlers.ChaosEvents;
import com.kotakotik.chaoscraft.config.CType;
import com.kotakotik.chaoscraft.config.Config;
import com.kotakotik.chaoscraft.config.ConfigInfo;
import com.kotakotik.chaoscraft.config.ExtraEventConfig;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
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
        command.then(new EventConfig().register(dispatcher));
        return command;
    }

    public abstract static class ConfigSetter<T> extends ChaosCraftCommand {
        public final T info;

        public ConfigSetter(T info) {
            this.info = info;
        }

        public abstract String getDescription();
        public abstract String getName();
        public String getCommandName() {
            return getName();
        }
        public abstract CType getType();
        public abstract ForgeConfigSpec.ConfigValue getConfigValue();
        public abstract String getDefault();

        public ITextComponent getCompleteSetValueMessage(CommandContext<CommandSource> context, Object val) {
            return new StringTextComponent(MessageFormat.format("value of {0} set to {1}", getName(), val));
        }

        public ITextComponent getInfoMessage(CommandContext<CommandSource> context, Object val) {
            try {
                return new StringTextComponent(String.format("value of %s is %s\ndefault value: %s\ndescription: %s", getName(), String.valueOf(val), getDefault(), getDescription()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new StringTextComponent("error");
        }

        @Override
        public ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
            LiteralArgumentBuilder<CommandSource> command = Commands.literal(getCommandName());

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

            command.then(Commands.argument("value", type).executes(this::setValue)).executes(this);

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
            System.out.println("hi :(");
            context.getSource().sendFeedback(getInfoMessage(context, getConfigValue().get()), false);
            return 1;
        }
    }

    public static class EventConfig extends ChaosCraftCommand {
        @Override
        public ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
            LiteralArgumentBuilder<CommandSource> command = Commands.literal("events");
            command.requires(cs -> cs.hasPermissionLevel(2));
            for(String key : Config.eventBooleans.keySet()) {
                ForgeConfigSpec.BooleanValue val = Config.eventBooleans.get(key);
                ChaosEvent event = ChaosEvents.getAsMap().get(key);
                LiteralArgumentBuilder<CommandSource> eventCommand = Commands.literal(event.getId());
                eventCommand.then(new EventEnabledConfig(event).register(dispatcher));
                for(ExtraEventConfig extraEventConfig : event.getExtraConfig()) {
                    eventCommand.then(new EventExtraConfig(extraEventConfig).register(dispatcher));
                }
                command.then(eventCommand);
            }
            return command;
        }

        public static class EventExtraConfig extends ConfigSetter<ExtraEventConfig> {
            public EventExtraConfig(ExtraEventConfig info) {
                super(info);
            }

            @Override
            public String getDescription() {
                return info.desc;
            }

            @Override
            public String getName() {
                return info.event.getId() + "." + getCommandName();
            }

            @Override
            public String getCommandName() {
                return info.id;
            }

            @Override
            public CType getType() {
                return info.type;
            }

            @Override
            public ForgeConfigSpec.ConfigValue getConfigValue() {
                return info.getConfigValue();
            }

            @Override
            public String getDefault() {
                return String.valueOf(info.defauld);
            }
        }

        public static class EventEnabledConfig extends ConfigSetter<ChaosEvent> {
            public EventEnabledConfig(ChaosEvent info) {
                super(info);
            }

            @Override
            public String getDescription() {
                return MessageFormat.format("Whether event {0} is enabled", info.getEnglish());
            }

            @Override
            public String getName() {
                return info.getId() + "." + getCommandName();
            }

            @Override
            public String getCommandName() {
                return "is_enabled";
            }

            @Override
            public CType getType() {
                return CType.BOOL;
            }

            @Override
            public ForgeConfigSpec.ConfigValue getConfigValue() {
                return info.getEnabledConfig();
            }

            @Override
            public String getDefault() {
                return String.valueOf(info.isEnabledOnDefault());
            }

            @Override
            public int setValue(CommandContext<CommandSource> ctx) {
                int toRet =  super.setValue(ctx);
                ChaosEventHandler.updateEnabledEvents();
                return toRet;
            }
        }
    }

    public static class GeneralConfig extends ChaosCraftCommand {
        @Override
        public ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
            LiteralArgumentBuilder<CommandSource> command = Commands.literal("general");
            command.requires(cs -> cs.hasPermissionLevel(2));
            for(ConfigInfo.Info<?> info : Config.registeredConfig) {
                command.then(new GeneralEachConfig(info).register(dispatcher));
            }
            return command;
        }

        public static class GeneralEachConfig extends ConfigSetter<ConfigInfo.Info<?>> {
            public GeneralEachConfig(ConfigInfo.Info<?> info) {
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
                return info.getType();
            }

            @Override
            public ForgeConfigSpec.ConfigValue getConfigValue() {
                return info.configValue;
            }

            @Override
            public String getDefault() {
                return String.valueOf(info.defauld);
            }
        }
    }
}

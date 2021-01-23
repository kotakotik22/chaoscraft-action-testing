package com.kotakotik.chaoscraft.commands;

import com.kotakotik.chaoscraft.Chaos;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class CommandHandler {
    private static List<Class<? extends ChaosCraftCommand>> commands = new ArrayList<>();

    public static void register(CommandDispatcher<CommandSource> dispatcher) throws IllegalAccessException, InstantiationException {
        LiteralArgumentBuilder<CommandSource> main = Commands.literal(Chaos.MODID);

        for(Class<? extends ChaosCraftCommand> clazz : commands) {
            main.then(clazz.newInstance().register(dispatcher));
        }

        LiteralCommandNode<CommandSource> cmdChaos= dispatcher.register(main);
    }

    public static void registerCommand(Class<? extends ChaosCraftCommand> command) {
        commands.add(command);
    }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) throws InstantiationException, IllegalAccessException {
        register(event.getDispatcher());
    }

    static {
        // register commands
        registerCommand(CommandChaosCraftConfig.class);
    }
}

package com.kotakotik.chaoscraft.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;

public abstract class ChaosCraftCommand implements Command<CommandSource> {
    public abstract ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher);

    @Override
    public int run(CommandContext<CommandSource> context) {
        return 0; // method overriden because some commands can't just be ran but their arguments can
    }
}

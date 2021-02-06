package com.kotakotik.chaoscraft.chaos_events;

import com.kotakotik.chaoscraft.chaos_handlers.ChaosEvent;
import com.kotakotik.chaoscraft.chaos_handlers.ChaosEventRegister;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

@ChaosEventRegister
public class EventCursedCraft extends ChaosEvent {
    @Override
    public String getEnglish() {
        return "CursedCraft";
    }

    @Override
    public String getEnglishDescription() {
        return "Sets the amount of items in the current held stack to the max stack size of it + 1\n" +
                "\n" +
                "So, with most of the items, the max stack size is 64, so the amount changes to 65, with some items like the snowballs and ender pearls, the max stack size is 16 so the amount changes to 17, and 2 with items with durability like swords";
    }

    @Override
    public String getId() {
        return "cursed_craft";
    }

    @Override
    public void start(MinecraftServer server) {
        for(ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
            if(!player.getHeldItemMainhand().isEmpty()) {
                ItemStack mhstack = player.getHeldItemMainhand();
                mhstack.setCount(mhstack.getMaxStackSize() + 1);
            }
        }
    }
}

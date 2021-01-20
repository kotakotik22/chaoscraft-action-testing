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

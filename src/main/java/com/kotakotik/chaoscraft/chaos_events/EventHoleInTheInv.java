package com.kotakotik.chaoscraft.chaos_events;

import com.kotakotik.chaoscraft.ChaosEvent;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;

public class EventHoleInTheInv extends ChaosEvent {
    @Override
    public String getEnglish() {
        return "Hole in the inventory";
    }

    @Override
    public String getId() {
        return "hole_in_the_inv";
    }

    @Override
    public void start(MinecraftServer server) {
        for(ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
            player.inventory.dropAllItems();
        }
    }
}

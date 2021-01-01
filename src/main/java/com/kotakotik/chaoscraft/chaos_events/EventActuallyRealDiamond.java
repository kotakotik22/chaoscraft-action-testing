package com.kotakotik.chaoscraft.chaos_events;

import com.kotakotik.chaoscraft.ChaosEvent;
import com.kotakotik.chaoscraft.ModItems;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;

public class EventActuallyRealDiamond extends ChaosEvent {
    @Override
    public String getEnglish() {
        return "Actually real diamond";
    }

    @Override
    public String getId() {
        return "actually_real_diamond";
    }

    @Override
    public void start(MinecraftServer server) {
        for(ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
            player.inventory.addItemStackToInventory(new ItemStack(Items.DIAMOND, 1));
        }
    }
}

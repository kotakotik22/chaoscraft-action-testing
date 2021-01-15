package com.kotakotik.chaoscraft.chaos_events;

import com.kotakotik.chaoscraft.ChaosEvent;
import com.kotakotik.chaoscraft.ChaosEventRegister;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

@ChaosEventRegister
public class EventTenHourMiningSession extends ChaosEvent {
    @Override
    public String getEnglish() {
        return "Ten hour mining session";
    }

    @Override
    public String getId() {
        return "ten_hour_mining_session";
    }

    @Override
    public void start(MinecraftServer server) {
        for(ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
            int todr = 50;
            for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
                if(player.inventory.getStackInSlot(i).isEmpty() && todr > 0) {
                    todr--;
                    player.inventory.setInventorySlotContents(i, new ItemStack(Blocks.COBBLESTONE, 64));
                }
            }
            player.dropItem(new ItemStack(Blocks.COBBLESTONE, 64 * todr), false);
        }
    }
}

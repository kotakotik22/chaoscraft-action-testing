package com.kotakotik.chaoscraft.chaos_events;

import com.kotakotik.chaoscraft.chaos_handlers.ChaosEvent;
import com.kotakotik.chaoscraft.chaos_handlers.ChaosEventRegister;
import com.kotakotik.chaoscraft.ModItems;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

import java.util.HashMap;

@ChaosEventRegister
public class EventTotallyRealDiamond extends ChaosEvent {
    @Override
    public String getEnglish() {
        return "Totally real diamonds";
    }

    @Override
    public String getId() {
        return "totally_real_diamond";
    }

    @Override
    public void start(MinecraftServer server) {
        for(ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
            player.inventory.addItemStackToInventory(new ItemStack(ModItems.TOTALLY_REAL_DIAMOND.get(), 64));
        }
    }

    @Override
    public HashMap<String, String> getExtraTranslations() {
        HashMap<String, String> map = new HashMap<>();

        map.put("item.chaoscraft.totally_real_diamond", "Totally real diamond");

        return map;
    }
}

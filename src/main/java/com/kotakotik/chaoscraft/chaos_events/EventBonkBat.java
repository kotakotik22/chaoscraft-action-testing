package com.kotakotik.chaoscraft.chaos_events;

import com.kotakotik.chaoscraft.chaos_handlers.ChaosEvent;
import com.kotakotik.chaoscraft.chaos_handlers.ChaosEventRegister;
import com.kotakotik.chaoscraft.chaos_handlers.Credit;
import com.kotakotik.chaoscraft.chaos_handlers.Credits;
import com.kotakotik.chaoscraft.items.BonkBat;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@ChaosEventRegister
public class EventBonkBat extends ChaosEvent {
    @Override
    public String getEnglish() {
        return "Bonk bat!";
    }

    @Override
    public String getEnglishDescription() {
        return "Every player gets a [[Bonk Bat (item)]]";
    }

    @Override
    public String getId() {
        return "bonk_bat";
    }

    @Override
    public void start(MinecraftServer server) {
        for(ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
            player.inventory.addItemStackToInventory(BonkBat.create());
        }
    }

    @Override
    public List<Credit> getCredits() {
        return Collections.singletonList(Credits.HUSKER.credit);
    }

    @Override
    public HashMap<String, String> getExtraTranslations() {
        HashMap<String, String> map = new HashMap<>();

        map.put("item.chaoscraft.bonk_bat", "Bonk bat");

        return map;
    }
}

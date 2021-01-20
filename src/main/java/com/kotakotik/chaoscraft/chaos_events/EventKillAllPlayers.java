package com.kotakotik.chaoscraft.chaos_events;

import com.kotakotik.chaoscraft.chaos_handlers.ChaosEvent;
import com.kotakotik.chaoscraft.chaos_handlers.ChaosEventRegister;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;

import java.util.HashMap;

@ChaosEventRegister
public class EventKillAllPlayers extends ChaosEvent {
    @Override
    public String getEnglish() {
        return "Kill all players";
    }

    @Override
    public String getId() {
        return "kill_all_players";
    }

    @Override
    public void start(MinecraftServer server) {
        for (ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
            player.attackEntityFrom(new DamageSource("chaoscraft.kill_all_players"), Integer.MAX_VALUE);
        }
    }

    @Override
    public HashMap<String, String> getExtraTranslations() {
        HashMap<String, String> map = new HashMap<>();

        map.put("death.attack.chaoscraft.kill_all_players", "%s got killed by chaos");

        return map;
    }

    @Override
    public boolean isEnabledOnDefault() {
        return false;
    }
}

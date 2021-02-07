package com.kotakotik.chaoscraft.chaos_events.temp;

import com.kotakotik.chaoscraft.chaos_handlers.ChaosEventRegister;
import com.kotakotik.chaoscraft.chaos_handlers.ChaosEventTemp;
import com.kotakotik.chaoscraft.chaos_handlers.ChaosEvents;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;

@ChaosEventRegister
public class TEventFlight extends ChaosEventTemp {
    @Override
    public String getEnglish() {
        return "I believe I can fly";
    }

    @Override
    public String getEnglishDescription() {
        return "All of the players are forced to fly up";
    }

    @Override
    public String getId() {
        return "flight";
    }

    @Override
    public int getDuration() {
        return 40;
    }

    @Override
    public void tick(MinecraftServer server) {
        super.tick(server);
        for(ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
            new TEventCantStopJumping.Jump().sendToClient(player);
        }
    }

    @Override
    public List<ChaosEventTemp> incompatibleWith() {
        List<ChaosEventTemp> list = new ArrayList<>();
        list.add(ChaosEvents.getTempsAsMap().get("cant_stop_jumping"));
        return list;
    }
}

package com.kotakotik.chaoscraft.chaos_events;

import com.kotakotik.chaoscraft.ChaosEvent;
import com.kotakotik.chaoscraft.ChaosEventRegister;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;

@ChaosEventRegister
public class EventTeleportAllPlayersUp extends ChaosEvent {
    @Override
    public String getEnglish() {
        return "Teleport all players up";
    }

    @Override
    public String getId() {
        return "teleport_all_players_up";
    }

    @Override
    public void start(MinecraftServer server) {
        for (ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
//                player.setPosition(player.getPosX(), 300, player.getPosZ());
                player.teleport((ServerWorld) player.world, player.getPosX(), 300, player.getPosZ(), 0f, player.rotationPitch);
        }
    }
}

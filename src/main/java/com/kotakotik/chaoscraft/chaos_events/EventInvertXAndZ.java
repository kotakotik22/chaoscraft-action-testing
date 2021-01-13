package com.kotakotik.chaoscraft.chaos_events;

import com.kotakotik.chaoscraft.ChaosEvent;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;

public class EventInvertXAndZ extends ChaosEvent {
    @Override
    public String getEnglish() {
        return "Swap X and Z";
    }

    @Override
    public String getId() {
        return "invert_x_and_z";
    }

    @Override
    public void start(MinecraftServer server) {
        for(ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
            player.teleport((ServerWorld) player.world, player.getPosZ(), player.getPosY(), player.getPosX(), 0, player.rotationPitch);
        }
    }
}

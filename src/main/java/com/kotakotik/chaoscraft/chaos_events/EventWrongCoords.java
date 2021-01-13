package com.kotakotik.chaoscraft.chaos_events;

import com.kotakotik.chaoscraft.ChaosEvent;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;

public class EventWrongCoords extends ChaosEvent {
    @Override
    public String getEnglish() {
        return "I told you positive X, not negative!";
    }

    @Override
    public String getId() {
        return "wrong_coords";
    }

    @Override
    public void start(MinecraftServer server) {
        for(ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
            player.teleport((ServerWorld) player.world, player.getPosX() * -1, player.getPosY(), player.getPosZ() * -1, 0, player.rotationPitch);
        }
    }
}

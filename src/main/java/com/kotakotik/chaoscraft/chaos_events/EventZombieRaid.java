package com.kotakotik.chaoscraft.chaos_events;

import com.kotakotik.chaoscraft.ChaosEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;

public class EventZombieRaid extends ChaosEvent {
    @Override
    public String getEnglish() {
        return "Zombie raid!";
    }

    @Override
    public String getId() {
        return "zombie_raid";
    }

    public static final int zombiesToSpawn = 15;

    @Override
    public void start(MinecraftServer server) {
        for(ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
            for(int i = 0; i < zombiesToSpawn; i++) {
                ZombieEntity zombieEntity = new ZombieEntity(player.world);
                zombieEntity.setPosition(player.getPosX(), player.getPosY(), player.getPosZ());
                player.world.addEntity(zombieEntity);
            }
        }
    }
}

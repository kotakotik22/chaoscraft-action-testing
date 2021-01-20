package com.kotakotik.chaoscraft.chaos_events;

import com.kotakotik.chaoscraft.chaos_handlers.ChaosEvent;
import com.kotakotik.chaoscraft.chaos_handlers.ChaosEventRegister;
import com.kotakotik.chaoscraft.config.ExtraEventConfig;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.List;

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
        int Y = Y_LEVEL.getIntConfigValue().get();
        for (ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
//                player.setPosition(player.getPosX(), 300, player.getPosZ());
                player.teleport((ServerWorld) player.world, player.getPosX(), Y, player.getPosZ(), 0f, player.rotationPitch);
        }
    }

    public static ExtraEventConfig Y_LEVEL;

    @Override
    public List<ExtraEventConfig> getExtraConfig() {
        List<ExtraEventConfig> list = new ArrayList<>();
        Y_LEVEL = new ExtraEventConfig(this, "yLevel", "The Y level to teleport to", 300, Integer.MIN_VALUE);
        list.add(Y_LEVEL);
        return list;
    }
}

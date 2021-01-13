package com.kotakotik.chaoscraft.chaos_events;

import com.kotakotik.chaoscraft.ChaosEvent;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class EventLavaCeiling extends ChaosEvent {
    @Override
    public String getEnglish() {
        return "Lava ceiling";
    }

    @Override
    public String getId() {
        return "lava_ceiling";
    }

    @Override
    public void start(MinecraftServer server) {
        for(ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
            player.world.setBlockState(new BlockPos(player.getPosX(), player.getPosY() + 2, player.getPosZ()),
                    Blocks.LAVA.getDefaultState());
        }
    }
}

package com.kotakotik.chaoscraft.chaos_events;

import com.kotakotik.chaoscraft.ChaosEvent;
import com.kotakotik.chaoscraft.ChaosEventRegister;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@ChaosEventRegister
public class EventWaterCeiling extends ChaosEvent {
    @Override
    public String getEnglish() {
        return "Water ceiling";
    }

    @Override
    public String getId() {
        return "water_ceiling";
    }

    @Override
    public void start(MinecraftServer server) {
        for(ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
            if (player.world.equals(server.getWorld(World.THE_NETHER))) return;
            player.world.setBlockState(new BlockPos(player.getPosX(), player.getPosY() + 2, player.getPosZ()),
                    Blocks.WATER.getDefaultState());
        }
    }
}

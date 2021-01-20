package com.kotakotik.chaoscraft.chaos_events;

import com.kotakotik.chaoscraft.chaos_handlers.ChaosEvent;
import com.kotakotik.chaoscraft.chaos_handlers.ChaosEventRegister;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ChaosEventRegister
public class EventOuttaThisWorld extends ChaosEvent {
    @Override
    public String getEnglish() {
        return "Outta this world";
    }

    @Override
    public String getId() {
        return "outta_this_world";
    }

    @Override
    public void start(MinecraftServer server) {
        for(ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
            List<World> worlds = new ArrayList<>();

            worlds.add(server.getWorld(World.OVERWORLD));
            worlds.add(server.getWorld(World.THE_END));
            worlds.add(server.getWorld(World.THE_NETHER));

            worlds.remove(player.getServerWorld());

            ServerWorld world = (ServerWorld) Util.getRandomObject(worlds.toArray(), new Random());

            player.teleport(world, player.getPosX(), player.getPosY(), player.getPosZ(), 0, player.rotationPitch);

            world.setBlockState(new BlockPos(player.getPosX(), player.getPosY(), player.getPosZ()),
                    Blocks.AIR.getDefaultState());
        }
    }
}

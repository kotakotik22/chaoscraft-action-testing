package com.kotakotik.chaoscraft.chaos_events;

import com.kotakotik.chaoscraft.chaos_handlers.ChaosEvent;
import com.kotakotik.chaoscraft.chaos_handlers.ChaosEventRegister;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;

@ChaosEventRegister
public class EventWrongCoords extends ChaosEvent {
    @Override
    public String getEnglish() {
        return "I told you positive X, not negative!";
    }

    @Override
    public String getEnglishDescription() {
        return "Note: This is also called \"Wrong coords\" in some places\n" +
                "\n" +
                "Multiplies your X and Z by -1, so if they are positive, they turn negative and the other way\n";
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

package com.kotakotik.chaoscraft.chaos_events;

import com.kotakotik.chaoscraft.ChaosEvent;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.server.MinecraftServer;

public class EventHyperspeed extends ChaosEvent {
    @Override
    public String getEnglish() {
        return "Hyperspeed";
    }

    @Override
    public String getId() {
        return "hyperspeed";
    }

    @Override
    public void start(MinecraftServer server) {
        for(ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
            player.addPotionEffect(new EffectInstance(Effects.SPEED, 20 * 20, 50));
        }
    }
}

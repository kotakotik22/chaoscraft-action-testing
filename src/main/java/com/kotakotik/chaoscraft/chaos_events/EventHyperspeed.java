package com.kotakotik.chaoscraft.chaos_events;

import com.kotakotik.chaoscraft.chaos_handlers.ChaosEvent;
import com.kotakotik.chaoscraft.chaos_handlers.ChaosEventRegister;
import com.kotakotik.chaoscraft.config.ExtraEventConfig;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;

@ChaosEventRegister
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
        int amplifier = AMPLIFIER.getIntConfigValue().get();
        int duration = DURATION.getIntConfigValue().get();
        for(ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
            player.addPotionEffect(new EffectInstance(Effects.SPEED, duration, amplifier));
        }
    }

    public static ExtraEventConfig AMPLIFIER;
    public static ExtraEventConfig DURATION;

    @Override
    public List<ExtraEventConfig> getExtraConfig() {
        List<ExtraEventConfig> list = new ArrayList<>();
        AMPLIFIER = new ExtraEventConfig(this, "amplifier", "The amplifier of the speed effect", 50);
        list.add(AMPLIFIER);
        DURATION = new ExtraEventConfig(this, "duration", "The duration of the speed effect (in ticks, 1 sec = 20 ticks)", 20 * 20);
        list.add(DURATION);
        return list;
    }
}

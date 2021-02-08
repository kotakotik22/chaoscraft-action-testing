package com.kotakotik.chaoscraft.chaos_events;

import com.kotakotik.chaoscraft.chaos_handlers.ChaosEvent;
import com.kotakotik.chaoscraft.chaos_handlers.ChaosEventRegister;
import com.kotakotik.chaoscraft.chaos_handlers.Credit;
import com.kotakotik.chaoscraft.chaos_handlers.Credits;
import com.kotakotik.chaoscraft.config.ExtraEventConfig;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.server.MinecraftServer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ChaosEventRegister
public class EventStun extends ChaosEvent {
    @Override
    public String getEnglish() {
        return "Stun";
    }

    @Override
    public String getEnglishDescription() {
        return "Gives you slowness 10 for 5 seconds";
    }

    @Override
    public String getId() {
        return "stun";
    }

    public ExtraEventConfig AMPLIFIER = new ExtraEventConfig(this, "amplifier", "The amplifier of the slowness effect", 10);
    public ExtraEventConfig DURATION = new ExtraEventConfig(this, "duration", "The duration of the slowness effect (in ticks, 1 sec = 20 ticks)", 5 * 20);

    @Override
    public void start(MinecraftServer server) {
        int amplifier = AMPLIFIER.getIntConfigValue().get();
        int duration = DURATION.getIntConfigValue().get();
        for(ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
            player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, duration, amplifier));
        }
    }

    @Override
    public List<ExtraEventConfig> getExtraConfig() {
        return Arrays.asList(AMPLIFIER, DURATION);
    }

    @Override
    public List<Credit> getCredits() {
        return Collections.singletonList(Credits.HUSKER.credit);
    }
}

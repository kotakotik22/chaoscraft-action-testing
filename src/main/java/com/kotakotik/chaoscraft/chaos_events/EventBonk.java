package com.kotakotik.chaoscraft.chaos_events;

import com.kotakotik.chaoscraft.chaos_handlers.ChaosEvent;
import com.kotakotik.chaoscraft.chaos_handlers.ChaosEventRegister;
import com.kotakotik.chaoscraft.chaos_handlers.Credit;
import com.kotakotik.chaoscraft.chaos_handlers.Credits;
import com.kotakotik.chaoscraft.items.BonkBat;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.server.MinecraftServer;

import java.util.Collections;
import java.util.List;

@ChaosEventRegister
public class EventBonk extends ChaosEvent {
    @Override
    public String getEnglish() {
        return "Bonk!";
    }

    @Override
    public String getEnglishDescription() {
        return "Deals 4 hearts of damage to all players and gives them Slowness 1 and Nausea 1";
    }

    @Override
    public String getId() {
        return "bonk";
    }

    @Override
    public void start(MinecraftServer server) {
        for(ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
            player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 10 * 20, 1));
            player.addPotionEffect(new EffectInstance(Effects.NAUSEA, 10 * 20, 1));
        }
    }

    @Override
    public List<Credit> getCredits() {
        return Collections.singletonList(Credits.HUSKER.credit);
    }
}

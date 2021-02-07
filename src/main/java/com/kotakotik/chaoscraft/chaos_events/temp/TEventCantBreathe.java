package com.kotakotik.chaoscraft.chaos_events.temp;

import com.kotakotik.chaoscraft.chaos_handlers.ChaosEventRegister;
import com.kotakotik.chaoscraft.chaos_handlers.ChaosEventTemp;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;
import java.util.Random;

@ChaosEventRegister
public class TEventCantBreathe extends ChaosEventTemp {
    @Override
    public String getEnglish() {
        return "I can't breathe!";
    }

    @Override
    public String getEnglishDescription() {
        return "Players start suffocating";
    }

    @Override
    public String getId() {
        return "cant_breathe";
    }

    @Override
    public int getDuration() {
        return 5;
    }

    public static Field refl = ObfuscationReflectionHelper.findField(Entity.class, "field_70171_ac");

    int ticks = 0;

    @Override
    public void tick(MinecraftServer server) {
        super.tick(server);
        boolean take = ticks >= 20;
        for(ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
            if(take) {
                player.attackEntityFrom(DamageSource.DROWN, 2.0F);
            }
        }
        if(take) ticks = 0;
        ticks++;
    }

}

package com.kotakotik.chaoscraft.chaos_events;

import com.kotakotik.chaoscraft.ChaosEvent;
import com.kotakotik.chaoscraft.ChaosEventRegister;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;

@ChaosEventRegister
public class EventChargedAttack extends ChaosEvent {
    @Override
    public String getEnglish() {
        return "Charged attack!";
    }

    @Override
    public String getId() {
        return "charged_attack";
    }

    public static final int creepersToSpawn = 15;

    public static Field refl = ObfuscationReflectionHelper.findField(CreeperEntity.class, "field_184714_b"); // CreeperEntity.POWERED

    @Override
    public void start(MinecraftServer server) {
        for(ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
            for(int i = 0; i < creepersToSpawn; i++) {
                CreeperEntity creeperEntity = new CreeperEntity(EntityType.CREEPER, player.world);
                creeperEntity.setPosition(player.getPosX(), player.getPosY(), player.getPosZ());
                try {
                    DataParameter<Boolean> powered = (DataParameter<Boolean>) refl.get(creeperEntity);
                    creeperEntity.getDataManager().set(powered, true);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                player.world.addEntity(creeperEntity);
            }

        }
    }
}

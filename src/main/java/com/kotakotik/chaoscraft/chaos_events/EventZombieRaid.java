package com.kotakotik.chaoscraft.chaos_events;

import com.kotakotik.chaoscraft.ChaosEvent;
import com.kotakotik.chaoscraft.ChaosEventRegister;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;
import java.util.Random;

@ChaosEventRegister
public class EventZombieRaid extends ChaosEvent {
    @Override
    public String getEnglish() {
        return "Zombie raid!";
    }

    @Override
    public String getId() {
        return "zombie_raid";
    }

    public static final int zombiesToSpawn = 15;

    public static Field refl = ObfuscationReflectionHelper.findField(ZombieEntity.class, "field_184737_bv"); // ZombieEntity.IS_CHILD
    @Override
    public void start(MinecraftServer server) {
        for(ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
            for(int i = 0; i < zombiesToSpawn; i++) {
                boolean spawnBaby = new Random().nextBoolean();
                ZombieEntity zombieEntity = new ZombieEntity(player.world);
                zombieEntity.setPosition(player.getPosX(), player.getPosY(), player.getPosZ());
                if(spawnBaby) {
                    try {
                        DataParameter<Boolean> is_child = (DataParameter<Boolean>) refl.get(zombieEntity);
                        zombieEntity.getDataManager().set(is_child, true);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                player.world.addEntity(zombieEntity);
            }
        }
    }
}

package com.kotakotik.chaoscraft.chaos_events;

import com.kotakotik.chaoscraft.ChaosEvent;
import com.kotakotik.chaoscraft.ChaosEventRegister;
import com.kotakotik.chaoscraft.config.Config;
import com.kotakotik.chaoscraft.config.ExtraEventConfig;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
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

//    public static final int zombiesToSpawn = 15;

    public static Field refl = ObfuscationReflectionHelper.findField(ZombieEntity.class, "field_184737_bv"); // ZombieEntity.IS_CHILD
    @Override
    public void start(MinecraftServer server) {
        for(ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
            int toSpawn = ((ForgeConfigSpec.IntValue) ZOMBIES_TO_SPAWN.getConfigValue()).get();
            for(int i = 0; i < toSpawn; i++) {
                boolean spawnBaby = new Random().nextBoolean();
                if(!((ForgeConfigSpec.BooleanValue) SPAWN_BABY_ZOMBIES.getConfigValue()).get()) {
                    spawnBaby = false;
                }
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

    public static ExtraEventConfig ZOMBIES_TO_SPAWN;
    public static ExtraEventConfig SPAWN_BABY_ZOMBIES;

    @Override
    public List<ExtraEventConfig> getExtraConfig() {
        List<ExtraEventConfig> list = new ArrayList<>();
        ZOMBIES_TO_SPAWN = new ExtraEventConfig(this, "zombiesToSpawn", "How many zombies to spawn", 15);
        list.add(ZOMBIES_TO_SPAWN);
        SPAWN_BABY_ZOMBIES = new ExtraEventConfig(this, "spawnBabyZombies", "Whether to spawn baby zombies", true);
        list.add(SPAWN_BABY_ZOMBIES);
        return list;
    }
}

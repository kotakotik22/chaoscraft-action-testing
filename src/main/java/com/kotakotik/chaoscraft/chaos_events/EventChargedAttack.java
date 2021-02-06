package com.kotakotik.chaoscraft.chaos_events;

import com.kotakotik.chaoscraft.chaos_handlers.ChaosEvent;
import com.kotakotik.chaoscraft.chaos_handlers.ChaosEventRegister;
import com.kotakotik.chaoscraft.config.ExtraEventConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@ChaosEventRegister
public class EventChargedAttack extends ChaosEvent {
    @Override
    public String getEnglish() {
        return "Charged attack!";
    }

    @Override
    public String getEnglishDescription() {
        return "Spawns 15 charged creepers at every player";
    }

    @Override
    public String getId() {
        return "charged_attack";
    }

    public static Field refl = ObfuscationReflectionHelper.findField(CreeperEntity.class, "field_184714_b"); // CreeperEntity.POWERED

    @Override
    public void start(MinecraftServer server) {
        int toSpawn = CREEPERS_TO_SPAWN.getIntConfigValue().get();
        for(ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
            for(int i = 0; i < toSpawn; i++) {
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

    public static ExtraEventConfig CREEPERS_TO_SPAWN;

    @Override
    public List<ExtraEventConfig> getExtraConfig() {
        List<ExtraEventConfig> list = new ArrayList<>();
        CREEPERS_TO_SPAWN = new ExtraEventConfig(this, "creepersToSpawn", "How many creepers to spawn", 15);
        list.add(CREEPERS_TO_SPAWN);
        return list;
    }
}

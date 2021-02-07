package com.kotakotik.chaoscraft.chaos_events;

import com.kotakotik.chaoscraft.chaos_handlers.ChaosEvent;
import com.kotakotik.chaoscraft.chaos_handlers.ChaosEventRegister;
import com.kotakotik.chaoscraft.chaos_handlers.Credit;
import com.kotakotik.chaoscraft.chaos_handlers.Credits;
import com.kotakotik.chaoscraft.config.ExtraEventConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ChaosEventRegister
public class EventTntRain extends ChaosEvent {
    @Override
    public String getEnglish() {
        return "TNT Rain";
    }

    @Override
    public String getEnglishDescription() {
        return "Spawns 15 TNT in random positions near players";
    }

    @Override
    public String getId() {
        return "tnt_rain";
    }

    @Override
    public void start(MinecraftServer server) {
        int toSpawn = tntToSpawn.getIntConfigValue().get();
        int y = YToAdd.getIntConfigValue().get();
        Random random = new Random();
        for(ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
            for(int i = 0; i < toSpawn; i++) {
                int x = (int) (player.getPosX() + (random.nextInt(30) - 15));
                int z = (int) (player.getPosZ() + (random.nextInt(30) - 15));
                TNTEntity tnt = new TNTEntity(player.world, x, player.getPosY() + y, z, player);
                player.world.addEntity(tnt);
            }
        }
    }

    public ExtraEventConfig tntToSpawn = new ExtraEventConfig(this, "tntToSpawn", "How many TNT's to spawn", 15);
    public ExtraEventConfig YToAdd = new ExtraEventConfig(this, "YToAdd", "At what Y level to spawn the TNT's at", 50, Integer.MIN_VALUE);

    @Override
    public List<ExtraEventConfig> getExtraConfig() {
        List<ExtraEventConfig> list = new ArrayList<>();
        list.add(tntToSpawn);
        list.add(YToAdd);
        return list;
    }

    @Override
    public List<Credit> getCredits() {
       List<Credit> list = new ArrayList<>();
       list.add(Credits.HUSKER.credit);
       return list;
    }
}

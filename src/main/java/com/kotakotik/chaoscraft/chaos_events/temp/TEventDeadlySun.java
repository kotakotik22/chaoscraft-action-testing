package com.kotakotik.chaoscraft.chaos_events.temp;

import com.kotakotik.chaoscraft.chaos_handlers.ChaosEventRegister;
import com.kotakotik.chaoscraft.chaos_handlers.ChaosEventTemp;
import com.kotakotik.chaoscraft.chaos_handlers.Credit;
import com.kotakotik.chaoscraft.chaos_handlers.Credits;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.DaylightDetectorBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ChaosEventRegister
public class TEventDeadlySun extends ChaosEventTemp {
    @Override
    public String getEnglish() {
        return "The sun is a deadly lazer";
    }

    @Override
    public String getEnglishDescription() {
        return "If there is no block above you, you get hurt";
    }

    @Override
    public String getId() {
        return "deadly_sun";
    }

    @Override
    public int getDuration() {
        return 20;
    }

    @Override
    public void tick(MinecraftServer server) {
        super.tick(server);
        for(ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
            if(player.world.canBlockSeeSky(player.getPosition())) {
                player.attackEntityFrom(new DamageSource("chaoscraft.deadly_sun"), 1);
            }
        }
    }

    @Override
    public List<Credit> getCredits() {
        List<Credit> list = new ArrayList<>();
        list.add(Credits.PHYSICALLY.credit);
        return list;
    }

    @Override
    public HashMap<String, String> getExtraTranslations() {
        HashMap<String, String> map = new HashMap<>();
        map.put("death.attack.chaoscraft.deadly_sun", "%s shouldn't have gone on land");
        return map;
    }
}

package com.kotakotik.chaoscraft;

import com.kotakotik.chaoscraft.chaos_events.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ChaosEvents {
    public static final ChaosEvent[] Events = new ChaosEvent[]{
            new EventKillAllPlayers(),
            new EventTeleportAllPlayersUp(),
            new EventZombieRaid(),
            new EventTotallyRealDiamond(),
            new EventChargedAttack(),
            new EventInvertXAndZ(),
            new EventLavaCelling(),
            new EventWaterCelling(),
            new EventActuallyRealDiamond(),
            new EventTenHourMiningSession(),
            new EventHyperspeed(),
            new EventWrongCoords(),
            new EventHoleInTheInv(),
            new EventOuttaThisWorld()
    };

    public static HashMap<String, ChaosEvent> getAsMap() {
        HashMap<String, ChaosEvent> map = new HashMap<>();

        for(ChaosEvent chaosEvent : Events) {
            map.put(chaosEvent.getId(), chaosEvent);
        }

        return map;
    }

    public static List<ChaosEvent> getAsList() {
        return Arrays.asList(Events.clone());
    }

    public static ChaosEvent getRandom() {
        int rnd = new Random().nextInt(Events.length);
        return Events[rnd];
    }
}

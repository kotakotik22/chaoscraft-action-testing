package com.kotakotik.chaoscraft;

import com.kotakotik.chaoscraft.chaos_events.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ChaosEvents {
    // not used anymore!
    public static final ChaosEvent[] Events = new ChaosEvent[]{
//            new EventKillAllPlayers(),
//            new EventTeleportAllPlayersUp(),
//            new EventZombieRaid(),
//            new EventTotallyRealDiamond(),
//            new EventChargedAttack(),
//            new EventInvertXAndZ(),
//            new EventLavaCeiling(),
//            new EventWaterCeiling(),
//            new EventActuallyRealDiamond(),
//            new EventTenHourMiningSession(),
//            new EventHyperspeed(),
//            new EventWrongCoords(),
//            new EventHoleInTheInv(),
//            new EventOuttaThisWorld(),
//            new EventCursedCraft()
    };

    public static HashMap<String, ChaosEvent> getAsMap() {
        HashMap<String, ChaosEvent> map = new HashMap<>();

        for(ChaosEvent chaosEvent : getAsList()) {
            map.put(chaosEvent.getId(), chaosEvent);
        }

        return map;
    }

    public static List<ChaosEvent> getAsList() {
//        return Arrays.asList(Events.clone());
        return Chaos.eventInstances;
    }

//    public static ChaosEvent[] getAsArray() {
//        return getAsList().toArray(new ChaosEvent[getAsList().size()]);
//    }

    public static List<Class<? extends ChaosEvent>> getClasses() {
        return Chaos.eventz;
    }

    public static ChaosEvent getRandom() {
        System.out.println(getAsList().size());
        int rnd = new Random().nextInt(getAsList().size());
        return getAsList().get(rnd);
    }
}

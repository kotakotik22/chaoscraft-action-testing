package com.kotakotik.chaoscraft.chaos_handlers;

import com.kotakotik.chaoscraft.Chaos;

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
       return getRandom(getAsList());
    }

    public static ChaosEvent getRandom(List<ChaosEvent> events) {
        int rnd = new Random().nextInt(events.size());
        return events.get(rnd);
    }
}

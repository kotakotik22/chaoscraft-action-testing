package com.kotakotik.chaoscraftfabric;

import com.kotakotik.chaoscraftfabric.chaos_event_stuff.ChaosEventRegister;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChaoscraftFabric implements ModInitializer {
    public static final Logger log = LogManager.getLogger("chaoscraft");

    @Override
    public void onInitialize() {
        new ChaosEventRegister();
    }
}

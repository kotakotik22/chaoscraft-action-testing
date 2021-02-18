package com.kotakotik.chaoscraft;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;

public class Chaoscraft implements ModInitializer {
    @Override
    public void onInitialize() {
        ChaoscraftKotlin.Companion.init();
        AutoConfig.register(ChaosConfig.class, GsonConfigSerializer::new);
    }
}

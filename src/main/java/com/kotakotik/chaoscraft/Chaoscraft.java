package com.kotakotik.chaoscraft;

import net.fabricmc.api.ModInitializer;

public class Chaoscraft implements ModInitializer {
    @Override
    public void onInitialize() {
        ChaoscraftKotlin.Companion.init();
    }
}

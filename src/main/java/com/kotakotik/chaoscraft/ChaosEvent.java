package com.kotakotik.chaoscraft;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import java.util.HashMap;

public abstract class ChaosEvent {
    public abstract String getEnglish();
    public abstract String getId();
    public abstract void start(MinecraftServer server);

    public String getTranslation(String... args) {
        return Translation.getTranslation(getTranslationKey(), args);
    }

    public String getTranslationKey() {
        return Chaos.ChaosNamePrefix + getId();
    }

    public HashMap<String, String> getExtraTranslations() {
        return new HashMap<>();
    }
}

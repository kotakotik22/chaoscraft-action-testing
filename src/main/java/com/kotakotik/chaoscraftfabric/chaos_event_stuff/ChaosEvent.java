package com.kotakotik.chaoscraftfabric.chaos_event_stuff;

import net.minecraft.server.MinecraftServer;

public interface ChaosEvent {
    String getEnglish();
    String getEnglishDescription();
    String getId();
    void start(MinecraftServer server);
}

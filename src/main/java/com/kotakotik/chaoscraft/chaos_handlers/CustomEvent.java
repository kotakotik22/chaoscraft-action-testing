package com.kotakotik.chaoscraft.chaos_handlers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import net.minecraft.server.MinecraftServer;

import java.util.List;

public class CustomEvent {
    public ChaosEvent getEvent(MinecraftServer server) {
        return new ChaosEvent() {
            @Override
            public String getEnglish() {
                return english;
            }

            @Override
            public String getTranslation(String... args) {
                return getEnglish();
            }

            @Override
            public String getEnglishDescription() {
                return null;
            }

            @Override
            public String getId() {
                return id;
            }

            @Override
            public void start(MinecraftServer server) {
                for(String command : commands) {
                    server.getCommandManager().handleCommand(server.getCommandSource(), command);
                }
            }
        };
    }

    @SerializedName("english")
    private String english;

    @SerializedName("id")
    private String id;

    @SerializedName("commands")
    private List<String> commands;

    public String getEnglish() {
        return english;
    }

    public String getId() {
        return id;
    }

    public List<String> getCommands() {
        return commands;
    }

    public static CustomEvent getCustom(String json, Gson gson) {
        return gson.fromJson(json, CustomEvent.class);
    }
}

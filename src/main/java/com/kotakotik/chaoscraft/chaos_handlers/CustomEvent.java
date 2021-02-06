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
                return name;
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
                return "custom_event";
            }

            @Override
            public void start(MinecraftServer server) {
                for(String command : commands) {
                    server.getCommandManager().handleCommand(server.getCommandSource(), command);
                }
            }
        };
    }

    @SerializedName("name")
    private String name;

    @SerializedName("commands")
    private List<String> commands;

    public String getName() {
        return name;
    }

    public List<String> getCommands() {
        return commands;
    }

    public String toJson(Gson gson) {
        return getJson(this, gson);
    }

    public static String getJson(CustomEvent event, Gson gson) {
        return gson.toJson(event);
    }
    public static CustomEvent getCustom(String json, Gson gson) {
        return gson.fromJson(json, CustomEvent.class);
    }
}

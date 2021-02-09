package com.kotakotik.chaoscraft.chaos_handlers;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;

public class CustomEventTemp extends CustomEvent {
    @SerializedName("tickCommands")
    private List<String> tickCommands = new ArrayList<>();

    public List<String> getTickCommands() {
        return tickCommands;
    }

    @SerializedName("endCommands")
    private List<String> endCommands = new ArrayList<>();

    public List<String> getEndCommands() {
        return endCommands;
    }

    @SerializedName("duration")
    private int duration;

    public int getDuration() {
        return duration;
    }

//    @SerializedName("commands")
//    private List<String> commands = new ArrayList<>();

    @Override
    public ChaosEventTemp getEvent(MinecraftServer server) {
        return new ChaosEventTemp() {
            @Override
            public String getEnglish() {
                return getName();
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

            public void runCommands(MinecraftServer server, List<String> commands) {
                for (String command : commands) {
                    server.getCommandManager().handleCommand(server.getCommandSource(), command);
                }
            }

            @Override
            public int getDuration() {
                return duration;
            }

            @Override
            public void start(MinecraftServer server) {
                runCommands(server, getCommands());
            }

            @Override
            public void tick(MinecraftServer server) {
                super.tick(server);
                runCommands(server, tickCommands);
            }

            @Override
            public void end(MinecraftServer server) {
                super.end(server);
                runCommands(server, endCommands);
            }
        };
    }

    public static CustomEventTemp getCustom(String json, Gson gson) {
        return gson.fromJson(json, CustomEventTemp.class);
    }

    public static String getJson(CustomEventTemp event, Gson gson) {
        return gson.toJson(event);
    }

    @Override
    public String toJson(Gson gson) {
        return CustomEventTemp.getJson(this, gson);
    }
}

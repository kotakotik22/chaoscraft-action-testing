package com.kotakotik.chaoscraft;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static final String CATEGORY_GENERAL = "general";

    public static ForgeConfigSpec SERVER_CONFIG;

    public static ForgeConfigSpec.IntValue SECONDS_FOR_EVENT;
    public static ForgeConfigSpec.IntValue SECONDS_FOR_SYNC;
    public static ForgeConfigSpec.BooleanValue AUTO_SYNC;

    public static ForgeConfigSpec.IntValue register(ForgeConfigSpec.Builder BUILDER, String name,
                                                    String description, int defauld /* clazz but for default lol */, int min, int max) {
        return BUILDER.comment(description, "default: " + defauld, "type: int")
                .defineInRange(name, defauld, min, max);
    }

    public static ForgeConfigSpec.IntValue register(ForgeConfigSpec.Builder BUILDER, String name,
                                                    String description, int defauld, int min) {
        return register(BUILDER, name, description, defauld, min, Integer.MAX_VALUE);
    }

    public static ForgeConfigSpec.IntValue register(ForgeConfigSpec.Builder BUILDER, String name,
                                                    String description, int defauld) {
        return register(BUILDER, name, description, defauld, 1);
    }

    public static ForgeConfigSpec.BooleanValue register(ForgeConfigSpec.Builder BUILDER, String name,
                                                        String description, boolean defauld) {
        return BUILDER.comment(description, "default: " + defauld, "type: bool")
                .define(name, defauld);
    }

    static {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

        SERVER_BUILDER.comment("General settings").push(CATEGORY_GENERAL);

        SECONDS_FOR_EVENT = register(
                SERVER_BUILDER,
                "secondsForEvent",
                "How many seconds to wait before starting a new event",
                30
        );

        SECONDS_FOR_SYNC = register(
                SERVER_BUILDER,
                "secondsForSync",
                "How many seconds to wait before syncing the timer on client (only used if autoSync is enabled)",
                20
        );

        AUTO_SYNC = register(
                SERVER_BUILDER,
                "autoSync",
                "Whether to sync the timer on client, the seconds to sync is secondsForSync",
                true
        );

        SERVER_BUILDER.pop();

        SERVER_CONFIG = SERVER_BUILDER.build();
    }
}

package com.kotakotik.chaoscraft;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static final String CATEGORY_GENERAL = "general";

    public static ForgeConfigSpec SERVER_CONFIG;

    public static ForgeConfigSpec.IntValue SECONDS_FOR_EVENT;

    static {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

        SERVER_BUILDER.comment("General settings").push(CATEGORY_GENERAL);

        SECONDS_FOR_EVENT = SERVER_BUILDER.comment("How many seconds to wait before starting a new event", "default: " + 30)
                .defineInRange("secondsForEvent", 30, 1, Integer.MAX_VALUE);

        SERVER_BUILDER.pop();

        SERVER_CONFIG = SERVER_BUILDER.build();
    }
}

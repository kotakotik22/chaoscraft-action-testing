package com.kotakotik.chaoscraft.config;

import com.kotakotik.chaoscraft.chaos_handlers.ChaosEvent;
import com.kotakotik.chaoscraft.chaos_handlers.ChaosEventHandler;
import com.kotakotik.chaoscraft.chaos_handlers.ChaosEvents;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mod.EventBusSubscriber
public class Config {
    // TODO: check that if the config chances while on a server, the clients will update the info.

    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_EVENTS = "events";

    public static ForgeConfigSpec COMMON_CONFIG;

    public static ForgeConfigSpec.IntValue SECONDS_FOR_EVENT;
    public static ForgeConfigSpec.IntValue SECONDS_FOR_SYNC;
    public static ForgeConfigSpec.BooleanValue AUTO_SYNC;
    public static ForgeConfigSpec.IntValue SECONDS_FOR_SAVE;
    public static ForgeConfigSpec.BooleanValue SAVE_TIMER;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> CUSTOM_EVENTS;

    @Deprecated
    public static class ConfigInfo {
        public final Object defauld;
        private int min;
        private int max;
        public final String name;
        public final String desc;
        public final ForgeConfigSpec.Builder builder;
        public final ForgeConfigSpec.ConfigValue configValue;
        public final CType type;

        public ConfigInfo(ForgeConfigSpec.Builder BUILDER, String name,
                          String description, int defauld, int min, int max, ForgeConfigSpec.IntValue configValue, CType type) {
            this.min = min;
            this.max = max;
            this.defauld = defauld;
            this.name = name;
            desc = description;
            this.builder = BUILDER;
            this.configValue = configValue;
            this.type = type;
        }

        public ConfigInfo(ForgeConfigSpec.Builder BUILDER, String name,
                          String description, boolean defauld, ForgeConfigSpec.BooleanValue configValue, CType type) {
            this.defauld = defauld;
            this.name = name;
            desc = description;
            this.builder = BUILDER;
            this.configValue = configValue;
            this.type = type;
        }

        public ConfigInfo(ForgeConfigSpec.Builder BUILDER, String name,
                          String description, List<? extends String> defauld,
                          ForgeConfigSpec.ConfigValue<List<? extends String>> configValue,
                          CType type) {
            this.defauld = defauld;
            this.name = name;
            desc = description;
            this.builder = BUILDER;
            this.configValue = configValue;
            this.type = type;
        }

        public void register() {
            // nothing
        }

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }
    }

    public static List<com.kotakotik.chaoscraft.config.ConfigInfo.Info<?>> registeredConfig = new ArrayList<>();

    public static ForgeConfigSpec.IntValue register(ForgeConfigSpec.Builder BUILDER, String name,
                                                    String description, int defauld /* clazz but for default lol */, int min, int max,
                                                    boolean addCommand) {
        ForgeConfigSpec.IntValue configValue = BUILDER.comment(description, "default: " + defauld, "type: int")
                .defineInRange(name, defauld, min, max);
        if(addCommand) {
            new com.kotakotik.chaoscraft.config.ConfigInfo.IntInfo(BUILDER, name, description, defauld, configValue, min, max).register();
        }
        return configValue;
    }

    public static ForgeConfigSpec.IntValue register(ForgeConfigSpec.Builder BUILDER, String name,
                                                    String description, int defauld, int min, boolean addCommand) {
        return register(BUILDER, name, description, defauld, min, Integer.MAX_VALUE, addCommand);
    }

    public static ForgeConfigSpec.IntValue register(ForgeConfigSpec.Builder BUILDER, String name,
                                                    String description, int defauld, boolean addCommand) {
        return register(BUILDER, name, description, defauld, 1, addCommand);
    }

    public static ForgeConfigSpec.BooleanValue register(ForgeConfigSpec.Builder BUILDER, String name,
                                                        String description, boolean defauld, boolean addCommand) {
        ForgeConfigSpec.BooleanValue configValue = BUILDER.comment(description, "default: " + defauld, "type: bool (true/false)")
                .define(name, defauld);
        if(addCommand) {
            new com.kotakotik.chaoscraft.config.ConfigInfo.BoolInfo(BUILDER, name, description, defauld, configValue).register();
        }
        return configValue;
    }

    public ForgeConfigSpec.ConfigValue<List<? extends String>> register(ForgeConfigSpec.Builder BUILDER, String name,
                                                                        String description, List<String> defauld, boolean addCommand) {

        ForgeConfigSpec.ConfigValue<List<? extends String>> configValue = BUILDER.comment(description, "default: " + defauld, "type: string list")
                .defineList(name, defauld, (obj) -> obj instanceof String);
        if(addCommand) {
            new com.kotakotik.chaoscraft.config.ConfigInfo.ListInfo(BUILDER, name, description, defauld, configValue).register();
        }
        return configValue;
    }

    public ForgeConfigSpec.ConfigValue<List<? extends String>> register(ForgeConfigSpec.Builder BUILDER, String name,
                                                                        String description, boolean addCommand) {
        return register(BUILDER, name, description, new ArrayList<>(), addCommand);
    }


    public static HashMap<String, ForgeConfigSpec.BooleanValue> eventBooleans = new HashMap<>();
    public static HashMap<String, ForgeConfigSpec.ConfigValue<?>> extraConfigs = new HashMap<>();


    {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

        COMMON_BUILDER.comment("General settings").push(CATEGORY_GENERAL);

        SECONDS_FOR_EVENT = register(
                COMMON_BUILDER,
                "secondsForEvent",
                "How many seconds to wait before starting a new event",
                30,
                true
        );

        SECONDS_FOR_SYNC = register(
                COMMON_BUILDER,
                "secondsForSync",
                "How many seconds to wait before syncing the timer on client (only used if autoSync is enabled)",
                20,
                true
        );

        AUTO_SYNC = register(
                COMMON_BUILDER,
                "autoSync",
                "Whether to sync the timer on client, the seconds to sync is secondsForSync",
                true,
                true
        );

       SECONDS_FOR_SAVE = register(
               COMMON_BUILDER,
               "secondsForSave",
               "How many seconds to wait before saving the time left until the next event (only used if saveTimer is enabled)",
               5,
               true
       );

       SAVE_TIMER = register(
               COMMON_BUILDER,
               "saveTimer",
               "Whether to save the time left until the next event",
               true,
               true
       );

       CUSTOM_EVENTS = register(
               COMMON_BUILDER,
               "customEvents",
               "A list of custom events. Each string must be a JSON object with the info about the event. This info should be available to read on the wiki.",
               false
       );

        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Event settings").push(CATEGORY_EVENTS);

        for(ChaosEvent event : ChaosEvents.getAll()) {
            COMMON_BUILDER.comment(event.getEnglish(), event.getWikiPage()).push(event.getId());

            if(event.hasOnOffConfig()) {
                eventBooleans.put(event.getId(), register(
                        COMMON_BUILDER,
                        "enabled",
                        "Whether " + event.getId() + " is enabled",
                        event.isEnabledOnDefault(),
                        false
                ));
            }

            COMMON_BUILDER.comment("Extra config for the event").push("extra_config");

            List<ExtraEventConfig> extraConfig = event.getExtraConfig();

            for(ExtraEventConfig conf : extraConfig) {
                String confId = conf.getMapId();
                switch (conf.type) {
                    case INT:
                        extraConfigs.put(confId,
                                register(
                                        COMMON_BUILDER,
                                        conf.id,
                                        conf.desc,
                                        (int) conf.defauld,
                                        conf.getMin(),
                                        conf.getMax(),
                                        false
                                ));
                        break;
                    case BOOL:
                        extraConfigs.put(confId,
                                register(
                                        COMMON_BUILDER,
                                        conf.id,
                                        conf.desc,
                                        (boolean) conf.defauld,
                                        false
                                ));
                        break;
                }
            }

            COMMON_BUILDER.pop();

            COMMON_BUILDER.pop();
        }

        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    public static ForgeConfigSpec.BooleanValue getEventBool(String id) {
        return eventBooleans.get(id);
    }

    public static HashMap<String, ForgeConfigSpec.BooleanValue> getEventBooleans() {
        return eventBooleans;
    }

    public static boolean getIsEventEnabled(String id) {
        return getEventBool(id).get();
    }

    public static boolean getIsEventEnabled(ChaosEvent event) {
        return getIsEventEnabled(event.getId());
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {
        ChaosEventHandler.updateEnabledEvents();
    }

    @SubscribeEvent
    public static void onReload(final ModConfig.Reloading configEvent) {
        ChaosEventHandler.updateEnabledEvents();

    }
}

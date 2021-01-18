package com.kotakotik.chaoscraft.config;

import com.kotakotik.chaoscraft.ChaosEvent;
import net.minecraftforge.common.ForgeConfigSpec;

public class ExtraEventConfig {
    public static enum CType {
        INT,
        BOOL
    }
    public final CType type;
    public final Object defauld;
    private int min;
    private int max;
    public final String id;
    public final String desc;
    public final ChaosEvent event;

    public ExtraEventConfig(ChaosEvent event, String id, String description,int defauld, int min, int max) {
        type = CType.INT;
        this.defauld = defauld;
        this.min = min;
        this.max = max;
        this.id = id;
        desc = description;
        this.event = event;
    }

    public ExtraEventConfig(ChaosEvent event, String id, String description,int defauld, int min) {
        type = CType.INT;
        this.defauld = defauld;
        this.min = min;
        this.max = Integer.MAX_VALUE;
        this.id = id;
        desc = description;
        this.event = event;
    }

    public ExtraEventConfig(ChaosEvent event, String id, String description,int defauld) {
        type = CType.INT;
        this.defauld = defauld;
        this.min = 1;
        this.max = Integer.MAX_VALUE;
        this.id = id;
        desc = description;
        this.event = event;
    }

    public ExtraEventConfig(ChaosEvent event, String id, String description, boolean defauld) {
        type = CType.BOOL;
        this.defauld = defauld;
        this.id = id;
        desc = description;
        this.event = event;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    public ForgeConfigSpec.ConfigValue<?> getConfigValue() {
        return Config.extraConfigs.get(getMapId());
    }

    public String getMapId() {
        return getIdFor(id, event.getId());
    }

    public static String getIdFor(String configId, String eventId) {
        return eventId + ":" + configId;
    }
}

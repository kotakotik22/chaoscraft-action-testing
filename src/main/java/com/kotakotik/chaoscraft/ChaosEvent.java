package com.kotakotik.chaoscraft;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import javax.annotation.Nullable;
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

    public boolean hasOnOffConfig() {
        return true;
    }

    public boolean isEnabledOnDefault() {
        return true;
    }

    @Nullable
    public String getWikiPage() {
        // https://github.com/kotakotik22/chaoscraft/wiki/Actually-real-diamond
        // ^ example of a link
        return getWikiFirst() + getEnglish().replaceAll(" ", "-").replaceAll(",", "%2C");
    }

    public String getWikiFirst() {
        return "https://github.com/kotakotik22/chaoscraft/wiki/";
    }

    public HashMap<String, String> getExtraTranslations() {
        return new HashMap<>();
    }
}

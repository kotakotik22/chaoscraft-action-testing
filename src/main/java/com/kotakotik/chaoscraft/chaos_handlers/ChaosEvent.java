package com.kotakotik.chaoscraft.chaos_handlers;

import com.kotakotik.chaoscraft.Chaos;
import com.kotakotik.chaoscraft.Translation;
import com.kotakotik.chaoscraft.config.ExtraEventConfig;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    } // TODO: currently, if the return value is false, the config will ALWAYS be disabled, to be fixed!

    public boolean isEnabledOnDefault() {
        return true;
    }

    @Nullable
    public String getWikiPage() {
        // https://github.com/kotakotik22/chaoscraft/wiki/Actually-real-diamond
        // ^ example of a link
        return getWikiFirst() + wikiReplace(getEnglish());
    }

    public List<ExtraEventConfig> getExtraConfig() {
        return new ArrayList<>();
    }

    public String getWikiFirst() {
        return "https://github.com/kotakotik22/chaoscraft/wiki/";
    }

    public HashMap<String, String> getExtraTranslations() {
        return new HashMap<>();
    }

    /**
     * what to replace in the second part of the wiki link, the argument is only there to make it easier
     * to override, so the subclass can just override this method, put the things in the map that they want
     * and then return super.getWikiReplace(map) which will add all of the default strings and
     * all of the string they added, for cleaner code i recommend using {@link #getWikiPageReplace() getWikiPageReplace(no arguments)}
    */
    public HashMap<String, String> getWikiPageReplace(HashMap<String, String> map) {
        map.put(" ", "-");
        map.put(",", "%2C");
        map.put("!", "");
        return map;
    }

    public HashMap<String, String> getWikiPageReplace() {
        return getWikiPageReplace(new HashMap<>());
    }

    private String wikiReplace(String original) {
        String temp = original;
        HashMap<String, String> toReplace = getWikiPageReplace();
        for(String key : toReplace.keySet()) {
            String val = toReplace.get(key);
            temp = temp.replaceAll(key, val);
        }
        return temp;
    }
}

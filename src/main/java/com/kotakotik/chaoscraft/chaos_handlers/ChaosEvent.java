package com.kotakotik.chaoscraft.chaos_handlers;

import com.kotakotik.chaoscraft.Chaos;
import com.kotakotik.chaoscraft.Translation;
import com.kotakotik.chaoscraft.config.Config;
import com.kotakotik.chaoscraft.config.ExtraEventConfig;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class ChaosEvent implements Creditable {
    public abstract String getEnglish();
    public abstract String getEnglishDescription();
    public abstract String getId();
    public abstract void start(MinecraftServer server);

    public String getTranslation(String... args) {
        return Translation.getTranslation(getTranslationKey(), args);
    }
    public String getTranslationKey() {
        return Chaos.ChaosNamePrefix + getId();
    }

    // desc translation coming soon!
    public String getDescTranslation(String... args) {return Translation.getTranslation(getDescTranslationKey(), args);}
    public String getDescTranslationKey() {return Chaos.ChaosNamePrefix + getId() + ".description";}

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
        return getWikiFirst() + wikiReplace(getEnglish());
    }

    // EXTRA CONFIG

    public String generateWikiPage() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(getEnglishDescription());

        HashMap<String, String> extra = new HashMap<>();

        if(!getExtraConfig().isEmpty()) {
            stringBuilder.append("\n\n\n## Config (``").append("events.").append(getId()).append(".extra_config``):");

        /*
        Sample:
        Config (``events.ten_hour_mining_session.extra_config``):
        * Whether to fill armor slots (``fillArmor``)
        * How much cobblestone in a stack (``cobbleInSlot``)
         * How many stacks to give (``stacksOfCobble``)
         */

            for(ExtraEventConfig config : getExtraConfig()) {
                stringBuilder.
                        append("\n* ").
                        append(config.desc).
                        append(". Default: ").
                        append(String.valueOf(config.defauld)).
                        append(" (``").append(config.id).append("``)");
            }
        }

        // CREDIT

        if(!getCredits().isEmpty()) {
            stringBuilder.append("\n\n## Credits");
            for(Credit credit : getCredits()) {
                stringBuilder.append("\n* ").append(credit.getText());
            }
        }

        // EXTRA INFO

        extra.put("has extra config", String.valueOf(!getExtraConfig().isEmpty()));
        extra.put("enabled by default", String.valueOf(isEnabledOnDefault()));
        extra.put("id", getId());
        extra.put("english name", getEnglish());

        stringBuilder.append("\n\n## Extra info");

        for(String key : extra.keySet()) {
            String info = extra.get(key);
            stringBuilder.append("\n* ").append(key).append(": ").append(info);
        }

        return stringBuilder.toString();
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

    public ForgeConfigSpec.BooleanValue getEnabledConfig() {
        return Config.eventBooleans.get(getId());
    }

    /**
     * what to replace in the second part of the wiki link, the argument is only there to make it easier
     * to override, so the subclass can just override this method, put the things in the map that they want
     * and then return super.getWikiReplace(map) which will add all of the default strings and
     * all of the string they added, for cleaner code i recommend using {@link #getWikiPageReplace() getWikiPageReplace(no arguments)}
    */
    public HashMap<String, String> getWikiPageReplace(HashMap<String, String> map) {
        map.put(" ", "-");
        map.put(",", "");
        map.put("!", "");
        map.put("'", "");
        return map;
    }

    public HashMap<String, String> getWikiPageReplace() {
        return getWikiPageReplace(new HashMap<>());
    }

    public String wikiReplace(String original) {
        String temp = original;
        HashMap<String, String> toReplace = getWikiPageReplace();
        for(String key : toReplace.keySet()) {
            String val = toReplace.get(key);
            temp = temp.replaceAll(key, val);
        }
        return temp;
    }

    @Override
    public List<Credit> getCredits() {
        return new ArrayList<>();
    }
}

package com.kotakotik.chaoscraft;

import com.kotakotik.chaoscraft.items.TotallyRealDiamond;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public enum TranslationKeys {
    EventStarted("event_started", TextFormatting.GOLD + TextFormatting.BOLD.toString() + "Started event: " + "%s"),
    TimedEventStarted("timed_event_started", TranslationKeys.EventStarted.englishTranslation + " (%d seconds)"),
    TotallyRealDiamondDeath("death.attack.chaoscraft.totally_real_diamond_death", "%s found out that the diamond was actually fake", false),
    Timer("timer", "Next event in %s seconds");

    public final String key;
    public final String englishTranslation;

    TranslationKeys(String key, String englishTranslation) {
        this.key = "chaoscraft." + key;
        this.englishTranslation = englishTranslation;
    }

    TranslationKeys(String key, String englishTranslation, boolean prefix) {
        if(!prefix) {
            this.key = key;
        } else {
            this.key = "chaoscraft." + key;
        }
        this.englishTranslation = englishTranslation;
    }

    public String translate(String... args) {
        return Translation.getTranslation(key, args);
    }

    public TranslationTextComponent getComponent(String... args) {
        return new TranslationTextComponent(key, args);
    }
}

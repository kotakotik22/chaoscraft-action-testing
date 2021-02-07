package com.kotakotik.chaoscraft.datagen;

import com.kotakotik.chaoscraft.chaos_handlers.ChaosEvent;
import com.kotakotik.chaoscraft.chaos_handlers.ChaosEvents;
import com.kotakotik.chaoscraft.TranslationKeys;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.HashMap;

public class ChaosEventTranslationGeneration extends LanguageProvider {
    public ChaosEventTranslationGeneration(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
    }

    @Override
    protected void addTranslations() {
        for(ChaosEvent event : ChaosEvents.getAsList()) {
            add(event.getTranslationKey(), event.getEnglish());
            HashMap<String, String> extraTranslations = event.getExtraTranslations();
            for(String key : extraTranslations.keySet()) {
                add(key, extraTranslations.get(key));
            }
        }
        TranslationKeys[] translationKeys = TranslationKeys.values();
        for(TranslationKeys key : translationKeys) {
            add(key.key, key.englishTranslation);
        }
    }
}

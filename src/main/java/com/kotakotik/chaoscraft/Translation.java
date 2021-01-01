package com.kotakotik.chaoscraft;

import net.minecraft.util.text.TranslationTextComponent;

public class Translation {
    public static String getTranslation(String key, String... args) {
        return new TranslationTextComponent(key, args).getString();
    }
}

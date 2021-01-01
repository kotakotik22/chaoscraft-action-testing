package com.kotakotik.chaoscraft.datagen;

import com.kotakotik.chaoscraft.Chaos;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
//            generator.addProvider(new BlockStates(generator, event.getExistingFileHelper()));
//            generator.addProvider(new Models(generator, event.getExistingFileHelper()));
//        LangGen.register();
        generator.addProvider(new ChaosEventTranslationGeneration(generator, Chaos.MODID, "en_us"));
//        generator.addProvider(new RussianTranslations(generator, XYKey.MODID, "ru_ru"));
        // the generator doesnt work at all with cyrillic
    }
}
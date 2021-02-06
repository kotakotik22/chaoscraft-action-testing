package com.kotakotik.chaoscraft;

import com.kotakotik.chaoscraft.chaos_handlers.ChaosEvent;
import com.kotakotik.chaoscraft.chaos_handlers.ChaosEventRegister;
import com.kotakotik.chaoscraft.chaos_handlers.ChaosEventTemp;
import com.kotakotik.chaoscraft.config.Config;
import com.kotakotik.chaoscraft.networking.ModPacketHandler;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Chaos.MODID)
public class Chaos {

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public static final String MODID = "chaoscraft";
    public static final String ChaosNamePrefix = "chaoscraft.event.";

    public Chaos() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        registerEvents();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG, "chaoscraft.toml");

        Registration.register();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        ModPacketHandler.registerMessages();
    }

    public static void registerEvents() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Type ann = Type.getType(ChaosEventRegister.class);
        LOGGER.info("finding events");
        List<ModFileScanData.AnnotationData> events = new ArrayList<>();
        ModList.get().getAllScanData().forEach((mod) -> mod.getAnnotations().stream().filter((an) -> (an.getAnnotationType().equals(ann))).forEach(events::add));
        eventz.clear();
        eventInstances.clear();
        LOGGER.info("found " + events.size() + " events");
        for (ModFileScanData.AnnotationData ev : events) {
            Class<?> clazz = Class.forName(ev.getClassType().getClassName());
            // i never liked the idea of getting classes using their name or file location or something like that,
            // but i guess i have to do this :/
//            Class<? extends ChaosEvent> eventClass = (Class<? extends ChaosEvent>) ;
//            System.out.println(clazz.isAssignableFrom(ChaosEvent.class));
            if(ChaosEvent.class.isAssignableFrom(clazz)) {
                Class<? extends ChaosEvent> eventClass = (Class<? extends ChaosEvent>) clazz;
                ChaosEvent eventInstance = eventClass.newInstance();
                if(eventInstance instanceof ChaosEventTemp) {
                    LOGGER.info("event " + eventInstance.getId() + " is a temp event. registering as a temp event");
                    tempEventz.add((Class<? extends ChaosEventTemp>) eventClass);
                    tempEventInstances.add((ChaosEventTemp) eventInstance);
                } else {
                    eventz.add(eventClass);
                    eventInstances.add(eventInstance);
                }
                LOGGER.info("registered event: " + eventInstance.getId() + " ("+eventClass.getName()+ ")");
            } else {
                LOGGER.warn(ev.getClassType().getClassName() + " has event annotation but does not extend ChaosEvent");
            }
        }
        LOGGER.info("registered " + eventz.size() + " events");
        if(eventz.size() == 0) {
            LOGGER.info("uh oh! we have 0 events, expect a crash soon!");
        }
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo(MODID, "helloworld", () -> {
            LOGGER.info("Hello world from the MDK");
            return "Hello world";
        });
    }

    private void processIMC(final InterModProcessEvent event) {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }

    public static final List<Class<? extends ChaosEvent>> eventz = new ArrayList<>();
    public static final List<ChaosEvent> eventInstances = new ArrayList<>();
    public static final List<Class<? extends ChaosEventTemp>> tempEventz = new ArrayList<>();
    public static final List<ChaosEventTemp> tempEventInstances = new ArrayList<>();

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }
}

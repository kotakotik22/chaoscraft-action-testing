package com.kotakotik.chaoscraft;


import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;

@Mod.EventBusSubscriber()
public class ChaosEventHandler {
    private static int ticksUntilNext = 0; // i know the name is wrong but im a lazy person yknow

    private static MinecraftServer Server;

    @SubscribeEvent
    public static void onTick(TickEvent.ServerTickEvent event) {
        if(event.phase == TickEvent.Phase.START) return; // only do this once per tick
        if(Server == null) return;
        // why am i making so many comments rn lol

        int ticksToNext = Config.SECONDS_FOR_EVENT.get() * 20;

        ticksUntilNext++;
        if(ticksUntilNext >= ticksToNext) {
            ChaosEvent randomEvent = ChaosEvents.getRandom();

            for (ServerPlayerEntity player : Server.getPlayerList().getPlayers()) {
                if(randomEvent instanceof ChaosEventTimed) {
                    // Timed events are not done
                    player.sendStatusMessage(TranslationKeys.TimedEventStarted.getComponent(randomEvent.getTranslation(), String.valueOf(((ChaosEventTimed) randomEvent).getDuration())), false);
                } else {
                    player.sendStatusMessage(TranslationKeys.EventStarted.getComponent(randomEvent.getTranslation()), false);
                }
            }

            randomEvent.start(Server);

            ticksUntilNext = 0;
        }
    }

//    @SubscribeEvent
//    public static void onClientTick(TickEvent.ClientTickEvent event) {
//        if(event.phase == TickEvent.Phase.START) return;
//
//        int ticksToNext = Config.SECONDS_FOR_EVENT.get() * 20;
//
//        ticksUntilNext++;
//        if(ticksUntilNext >= ticksToNext) {
//            ticksUntilNext = 0;
//        }
//    }

    @SubscribeEvent
    public static void onServerStarted(FMLServerStartedEvent event) {
        Server = event.getServer();
    }

    public static int getTicksUntilNext() {
        return ticksUntilNext;
    }
}

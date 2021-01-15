package com.kotakotik.chaoscraft;


import com.kotakotik.chaoscraft.networking.ModPacketHandler;
import com.kotakotik.chaoscraft.networking.packets.PacketTimerRestart;
import com.kotakotik.chaoscraft.networking.packets.PacketTimerSync;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;

@Mod.EventBusSubscriber()
public class ChaosEventHandler {
    private static int ticks = 0;
    public static int ticksClient = 0;

    private static MinecraftServer Server;

    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        new PacketTimerSync(ticks).sendToClient((ServerPlayerEntity) event.getPlayer());
    }

    @SubscribeEvent
    public static void onTick(TickEvent.ServerTickEvent event) {
        if(event.phase == TickEvent.Phase.START) return;
        if(Server == null) return;
        // why am i making so many comments rn lol

        int ticksToNext = Config.SECONDS_FOR_EVENT.get() * 20;

        ticks++;
        if(ticks >= ticksToNext) {
            ChaosEvent randomEvent = ChaosEvents.getRandom();

            for (ServerPlayerEntity player : Server.getPlayerList().getPlayers()) {
                if(randomEvent instanceof ChaosEventTimed) {
                    // Timed events are not done
                    player.sendStatusMessage(TranslationKeys.TimedEventStarted.getComponent(randomEvent.getTranslation(), String.valueOf(((ChaosEventTimed) randomEvent).getDuration())), false);
                } else {
                    player.sendStatusMessage(TranslationKeys.EventStarted.getComponent(randomEvent.getTranslation()), false);
                }
            }
            // send packet to restart timer to all players
            new PacketTimerRestart().sendToAllClients();

            randomEvent.start(Server);

            ticks = 0;
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if(event.phase == TickEvent.Phase.START) return;
        if(Minecraft.getInstance().isGamePaused()) return;
        // who would have thought making a simple timer would be such a pain my god
        ticksClient++;
    }

    @SubscribeEvent
    public static void onServerStarted(FMLServerStartedEvent event) {
        Server = event.getServer();
    }

    public static int getTicks() {
        return ticks;
    }
}

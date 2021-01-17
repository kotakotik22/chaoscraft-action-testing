package com.kotakotik.chaoscraft;


import com.kotakotik.chaoscraft.networking.packets.PacketTimerRestart;
import com.kotakotik.chaoscraft.networking.packets.PacketTimerSet;
import com.kotakotik.chaoscraft.networking.packets.PacketTimerSync;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;

@Mod.EventBusSubscriber()
public class ChaosEventHandler {
    private static int ticks = 0;
    public static int ticksClient = 0;

    private static MinecraftServer Server;

    @SubscribeEvent // i am literally so stupid i forgot to put the @SubscribeEvent here lmao
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        new PacketTimerSet(ticks).sendToClient((ServerPlayerEntity) event.getPlayer());
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

    private static int ticksSinceLastUpdate = 0;
    private final static int ticksToUpdate = 20 * 20; // sync ticks every 20 seconds

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if(event.phase == TickEvent.Phase.START) return;
        Minecraft mc = Minecraft.getInstance();
        if(mc.isGamePaused()) return;
        if(mc.world == null) {ticksClient = 0; ticks = 0; return;}
        // dont count if not loaded, set client ticks to 0 so it resets when you leave,
        // and set server ticks to 0 so it doesnt save them when leaving a single player world
        // (server ticks are handled on client when playing in singleplayer)

        // who would have thought making a simple timer would be such a pain my god
        ticksClient++;
        if(ticksClient < 0) {
            new PacketTimerSync().sendToServer();
        }
        ticksSinceLastUpdate++;
        if(ticksSinceLastUpdate >= ticksToUpdate) {
            ticksSinceLastUpdate = 0;
            new PacketTimerSync().sendToServer();
        }
    }

    @SubscribeEvent
    public static void onServerStarted(FMLServerStartedEvent event) {
        Server = event.getServer();
    }

    public static int getTicks() {
        return ticks;
    }

}

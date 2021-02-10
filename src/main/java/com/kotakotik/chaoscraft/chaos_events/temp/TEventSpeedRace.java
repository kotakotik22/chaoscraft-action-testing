package com.kotakotik.chaoscraft.chaos_events.temp;

import com.kotakotik.chaoscraft.TranslationKeys;
import com.kotakotik.chaoscraft.chaos_handlers.*;
import com.kotakotik.chaoscraft.config.Config;
import com.kotakotik.chaoscraft.config.ExtraEventConfig;
import com.kotakotik.chaoscraft.networking.ModPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkEvent;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber
//@ChaosEventRegister
// event no finished, so dont register it
public class TEventSpeedRace extends ChaosEventTemp {
    @Override
    public String getEnglish() {
        return "Speed race";
    }

    @Override
    public String getEnglishDescription() {
        return "You must run 100 blocks in 30 seconds or you **die**";
    }

    @Override
    public String getId() {
        return "speed_race";
    }

    @Override
    public int getDuration() {
        return 30;
    }

    public ExtraEventConfig blocksToRun = new ExtraEventConfig(this, "blocksToRun", "How many blocks each player has to run not to die", 100);

    @Override
    public List<ExtraEventConfig> getExtraConfig() {
        return Collections.singletonList(blocksToRun);
    }

    @Override
    public List<Credit> getCredits() {
        return Collections.singletonList(Credits.HUSKER.credit);
    }

    @Override
    public HashMap<String, String> getExtraTranslations() {
        HashMap<String, String> map = new HashMap<>();
        map.put("chaoscraft.speed_race_text", "You have %s seconds to run %d blocks");
        return map;
    }

    @Override
    public void reset() {
        super.reset();
        ticks = 0;
    }

    @Override
    public void start(MinecraftServer server) {
        super.start(server);
        new Started().sendToAllClients();
    }

    @Override
    public void end(MinecraftServer server) {
        super.end(server);
        new Ended().sendToAllClients();
    }

    @Override
    public void tick(MinecraftServer server) {
        super.tick(server);
        ticks++;
    }

    int ticks = 0;

    private static HashMap<PlayerEntity, BlockPos> posHashMap = new HashMap<>();

    private static HashMap<PlayerEntity, Double> playerDistances = new HashMap<>();

    @SubscribeEvent
    public static void playerMove(LivingEvent.LivingUpdateEvent event) {
        if(!(event.getEntity() instanceof PlayerEntity)) return;
        PlayerEntity playerEntity = (PlayerEntity) event.getEntity();
        if(playerEntity.world.isRemote) {
            if(Minecraft.getInstance().player.equals(playerEntity)) {
                return;
            }
        }
        posHashMap.putIfAbsent(playerEntity, playerEntity.getPosition());
        double distance = Math.sqrt(
                (Math.pow(playerEntity.getPosX() - posHashMap.get(playerEntity).getX(), 2))
                        +
                        Math.pow(playerEntity.getPosZ() - posHashMap.get(playerEntity).getZ(), 2));
        if(!playerEntity.world.isRemote) {
            playerDistances.putIfAbsent(playerEntity, 0.0);
            playerDistances.put(playerEntity, playerDistances.get(playerEntity) + distance);
        } else {
            ClientHandler.myDistance = ClientHandler.myDistance + distance;
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Mod.EventBusSubscriber
    public static class ClientHandler {
        public static boolean hasEventStarted = false;

        public static double myDistance = 0;

        public static int ticksSinceStart = 0;

        private static int prevTick = -1;

        @SubscribeEvent
        public static void tick(TickEvent.ClientTickEvent event) {
            // i really dont want to do the things i had to do with the timer so ill just use this
            // weird way of copying it
            if(ChaosEventHandler.ticksClient != prevTick && hasEventStarted) {
                prevTick = ChaosEventHandler.ticksClient;
                ticksSinceStart++;
            }
        }

        @SubscribeEvent
        public static void render(RenderGameOverlayEvent event) {
            MainWindow window = event.getWindow();
            if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT && hasEventStarted) {
                TEventSpeedRace instance = (TEventSpeedRace) ChaosEvents.getAsMap().get("speed_race");

                GL11.glPushMatrix();

                int size = 2;
                GL11.glScaled(size, size, size);
                Minecraft MC = Minecraft.getInstance();
                RenderSystem.enableBlend();
                String text = new TranslationTextComponent("chaoscraft.speed_race_text", instance.getDuration() - ticksSinceStart, instance.blocksToRun.getIntConfigValue().get() - myDistance).getString();
//                int gui_position_x = window.getWidth() / 2 /* - MC.fontRenderer.getStringWidth(text) / 2 */;
//                System.out.println(gui_position_x);
                int gui_position_x = 50;
                int gui_position_y = 3;
                System.out.println(text);
                MC.fontRenderer.drawString(event.getMatrixStack(),
                        text,
                        gui_position_x, gui_position_y,
                        0x454545);
                GL11.glPopMatrix();
            }
        }
    }

    public static class Started extends ModPacket {
        @Override
        public boolean onReceived(Supplier<NetworkEvent.Context> ctx) {
            ClientHandler.hasEventStarted = true;
            ClientHandler.ticksSinceStart = 0;
            ClientHandler.myDistance = 0;
            return true;
        }

        @Override
        public boolean canBeReceivedOnServer() {
            return false;
        }
    }

    public static class Ended extends ModPacket {
        @Override
        public boolean onReceived(Supplier<NetworkEvent.Context> ctx) {
            ClientHandler.hasEventStarted = false;
            return true;
        }

        @Override
        public boolean canBeReceivedOnServer() {
            return false;
        }
    }
}

package com.kotakotik.chaoscraft.chaos_events.temp;

import com.kotakotik.chaoscraft.TranslationKeys;
import com.kotakotik.chaoscraft.chaos_handlers.ChaosEventHandler;
import com.kotakotik.chaoscraft.chaos_handlers.ChaosEventTemp;
import com.kotakotik.chaoscraft.config.Config;
import com.kotakotik.chaoscraft.networking.ModPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkEvent;
import org.lwjgl.opengl.GL11;

import java.util.function.Supplier;

public class TEventBlindness extends ChaosEventTemp {
    @Override
    public String getEnglish() {
        return "Blindness";
    }

    @Override
    public String getEnglishDescription() {
        return "";
    }

    @Override
    public String getId() {
        return "temp_blindness";
    }

    @Override
    public int getDuration() {
        return 10;
    }

    @Override
    public void start(MinecraftServer server) {
        super.start(server);
    }

    @Mod.EventBusSubscriber
    public static class ClientHandler {
        public static boolean activated = false;
//        @SubscribeEvent
//        public static void render(RenderGameOverlayEvent event) {
//            if(activated) {
//                MainWindow window = event.getWindow();
//                Minecraft MC = Minecraft.getInstance();
//                RenderSystem.enableBlend();
//                int gui_position_x = 3;
//                int gui_position_y = 3;
//                MC.ingameGUI.blit(event.getMatrixStack(),0,0,0,0,50,50);
//                GL11.glPopMatrix();
//            }
//        }
        public static class StartEvent extends ModPacket {
            @Override
            public boolean onReceived(Supplier<NetworkEvent.Context> ctx) {
                activated = true;
                return true;
            }

            @Override
            public boolean canBeReceivedOnServer() {
                return false;
            }
        }

        public static class EndEvent extends ModPacket {
            @Override
            public boolean onReceived(Supplier<NetworkEvent.Context> ctx) {
                activated = false;
                return true;
            }

            @Override
            public boolean canBeReceivedOnServer() {
                return false;
            }
        }
    }
}

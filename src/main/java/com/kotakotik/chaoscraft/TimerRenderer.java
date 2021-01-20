package com.kotakotik.chaoscraft;

import com.kotakotik.chaoscraft.chaos_handlers.ChaosEventHandler;
import com.kotakotik.chaoscraft.config.Config;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class TimerRenderer {
    @SubscribeEvent
    public static void render(RenderGameOverlayEvent event) {
        MainWindow window = event.getWindow();
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            GL11.glPushMatrix();

            int size = 2;
            GL11.glScaled(size, size, size);
            Minecraft MC = Minecraft.getInstance();
            RenderSystem.enableBlend();
            int gui_position_x = 3;
            int gui_position_y = 3;
            MC.fontRenderer.drawString(event.getMatrixStack(),
                    TranslationKeys.Timer.translate(String.valueOf(Config.SECONDS_FOR_EVENT.get() - ChaosEventHandler.ticksClient / 20)),
                    gui_position_x, gui_position_y,
                    0x454545);
            GL11.glPopMatrix();
        }
    }
}

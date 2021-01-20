package com.kotakotik.chaoscraft.timer_handlers;

import com.kotakotik.chaoscraft.Chaos;
import com.kotakotik.chaoscraft.chaos_handlers.ChaosEvent;
import com.kotakotik.chaoscraft.chaos_handlers.ChaosEventHandler;
import com.kotakotik.chaoscraft.config.Config;
import com.kotakotik.chaoscraft.world_data.ChaoscraftWorldData;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class TimerSaveHandler {
    private static MinecraftServer Server;

    private static int ticks = 0;

    @SubscribeEvent
    public static void onTick(TickEvent.ServerTickEvent event) {
        if(!Config.SAVE_TIMER.get()) return;
        if(event.phase == TickEvent.Phase.START) return;
        if(Server == null) return;
        ticks++;
        if(ticks >= Config.SECONDS_FOR_SAVE.get() * 20) {
            ChaoscraftWorldData data = getData();
            ticks = 0;
            setTimer(data, ChaosEventHandler.getTicks());
        }
    }

    private static ChaoscraftWorldData getData() {
        return ChaoscraftWorldData.get(Server);
    }

    private static int getTimer(ChaoscraftWorldData data) {
        CompoundNBT nbt = data.serializeNBT();
        return nbt.getInt(ChaoscraftWorldData.chaosTicksKey);
    }

    private static void setTimer(ChaoscraftWorldData data, int toSet) {
        CompoundNBT nbt = data.serializeNBT();
        nbt.putInt(ChaoscraftWorldData.chaosTicksKey, toSet);
        data.deserializeNBT(nbt);
        data.markDirty();
    }

    @SubscribeEvent
    public static void onServerStarted(FMLServerStartedEvent event) {
        Server = event.getServer();
        ChaoscraftWorldData data = getData();
        int ticks2 = getTimer(data);
        if(ticks2 > Config.SECONDS_FOR_EVENT.get() * 20 || !Config.SAVE_TIMER.get()) return;
        ChaosEventHandler.ticks = ticks2;
    }

    public static int getTicks() {
        return ticks;
    }
}

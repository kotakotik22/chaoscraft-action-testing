package com.kotakotik.chaoscraft.networking.packets;

import com.kotakotik.chaoscraft.chaos_handlers.ChaosEventHandler;
import com.kotakotik.chaoscraft.networking.ModPacket;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketTimerRestart extends ModPacket {
    @Override
    public boolean onReceived(Supplier<NetworkEvent.Context> ctx) {
        ChaosEventHandler.ticksClient = 0;
        return true;
    }

    @Override
    public boolean canBeReceivedOnServer() {
        return false;
    }
}

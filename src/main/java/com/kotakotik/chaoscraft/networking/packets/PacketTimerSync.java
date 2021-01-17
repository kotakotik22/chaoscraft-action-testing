package com.kotakotik.chaoscraft.networking.packets;

import com.kotakotik.chaoscraft.ChaosEventHandler;
import com.kotakotik.chaoscraft.networking.ModPacket;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

// the client can request to recieve the timer set packet so it can be synced
public class PacketTimerSync extends ModPacket {
    @Override
    public boolean onReceived(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> new PacketTimerSet(ChaosEventHandler.getTicks()).sendToClient(ctx.get().getSender()));
        return true;
    }

    @Override
    public boolean canBeReceivedOnClient() {
        return false;
    }
}

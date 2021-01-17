package com.kotakotik.chaoscraft.networking.packets;

import com.kotakotik.chaoscraft.ChaosEventHandler;
import com.kotakotik.chaoscraft.networking.ArgumentModPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketTimerSet extends ArgumentModPacket {
    private final int timer;
    private boolean hasTimer = true;

    public PacketTimerSet(int timer) {
        super();
        this.timer = timer;
    }

    public PacketTimerSet() {
        super();
        timer = 1;
        hasTimer = false;
    }

    public PacketTimerSet(PacketBuffer buffer) {
        super(buffer);
        timer = buffer.readInt();
    }

    @Override
    public boolean onReceived(Supplier<NetworkEvent.Context> ctx) {
        ChaosEventHandler.ticksClient = timer;
        return true;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeInt(timer);
    }

    @Override
    public void readBuf(PacketBuffer buf) {

    }

    @Override
    public boolean canBeReceivedOnServer() {
        return false;
    }
}

package com.kotakotik.chaoscraft.networking;

import net.minecraft.network.PacketBuffer;

public abstract class ArgumentModPacket extends ModPacket {
    public ArgumentModPacket() {

    }

    public abstract void toBytes(PacketBuffer buf);
    public abstract void readBuf(PacketBuffer buf);

    public ArgumentModPacket(PacketBuffer buf) {
        readBuf(buf);
    }
}

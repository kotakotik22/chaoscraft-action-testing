package com.kotakotik.chaoscraft.networking;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

public abstract class ModPacket {
    public abstract boolean onReceived(Supplier<NetworkEvent.Context> ctx);

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ServerPlayerEntity sender = ctx.get().getSender();
        if(sender == null && canBeReceivedOnClient()) {
            return onReceived(ctx);
        }
        if(sender != null && canBeReceivedOnServer()) {
            return onReceived(ctx);
        }
        return false;
    }

    public boolean canBeReceivedOnServer() {return true;}
    public boolean canBeReceivedOnClient() {return true;}

    public void sendToClient(ServerPlayerEntity player) {
        ModPacketHandler.sendToClient(this, player);
    }
    public void sendToServer() {
        ModPacketHandler.sendToServer(this);
    }
    public void sendToAllClients() { ModPacketHandler.sendToAllClients(this); }
}

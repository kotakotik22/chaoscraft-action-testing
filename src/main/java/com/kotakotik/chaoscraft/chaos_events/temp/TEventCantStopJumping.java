package com.kotakotik.chaoscraft.chaos_events.temp;

import com.kotakotik.chaoscraft.chaos_handlers.ChaosEventRegister;
import com.kotakotik.chaoscraft.chaos_handlers.ChaosEventTemp;
import com.kotakotik.chaoscraft.networking.ModPacket;
import net.minecraft.block.Blocks;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.network.NetworkEvent;

import java.awt.*;
import java.util.function.Supplier;

@ChaosEventRegister
public class TEventCantStopJumping extends ChaosEventTemp {
    @Override
    public String getEnglish() {
        return "Can't stop jumping";
    }

    @Override
    public String getEnglishDescription() {
        return "All players are forced to constantly jump (Event currently online in dev. You will not encounter this event in a release.)";
    }

    @Override
    public String getId() {
        return "cant_stop_jumping";
    }

    @Override
    public int getDuration() {
        return 30;
    }

    @Override
    public void tick(MinecraftServer server) {
        super.tick(server);
        for(ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
            if(player.isOnGround() || player.isInWater()) {
                new Jump().sendToClient(player);
            }
        }
    }

    public static class Jump extends ModPacket {
        @Override
        public boolean onReceived(Supplier<NetworkEvent.Context> ctx) {
            Minecraft.getInstance().player.jump();
            return true;
        }

        @Override
        public boolean canBeReceivedOnServer() {
            return false;
        }
    }
}

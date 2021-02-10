package com.kotakotik.chaoscraft.networking;

import com.kotakotik.chaoscraft.Chaos;
import com.kotakotik.chaoscraft.chaos_events.temp.TEventCantStopJumping;
import com.kotakotik.chaoscraft.chaos_events.temp.TEventSpeedRace;
import com.kotakotik.chaoscraft.networking.packets.PacketTimerRestart;
import com.kotakotik.chaoscraft.networking.packets.PacketTimerSet;
import com.kotakotik.chaoscraft.networking.packets.PacketTimerSync;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;

import java.util.function.Function;

public class ModPacketHandler {
    private static SimpleChannel INSTANCE;
    private static int ID = 0;

    private static int nextID() {
        return ID++;
    }

    private static <T extends ModPacket> void register(Class<T> clazz, Function<PacketBuffer, T> supplier) {
        LogManager.getLogger().info("registering packet " + clazz.getSimpleName() + " ("+clazz.getName() + ")");
        INSTANCE.messageBuilder(clazz, nextID())
                .encoder((packetTimerRestart, packetBuffer) -> {})
                .decoder(supplier)
                .consumer(T::handle)
                .add();
    }

    private static <T extends ArgumentModPacket> void registerArg(Class<T> clazz, Function<PacketBuffer, T> supplier) {
        LogManager.getLogger().info("registering arg packet " + clazz.getSimpleName() + " ("+clazz.getName() + ")");
        INSTANCE.messageBuilder(clazz, nextID())
                .encoder(T::toBytes)
                .decoder(supplier)
                .consumer(T::handle)
                .add();
    }

    private static <T extends ModPacket> void register(Class<T> clazz) {
        register(clazz, (packetBuffer -> {
            try {
                return clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                LogManager.getLogger().info("exception while registering packet " + clazz.getName());
                e.printStackTrace();
            }
            return null;
        }));
    }

    private static <T extends ArgumentModPacket> void registerArg(Class<T> clazz) {
        registerArg(clazz, (packetBuffer -> {
            try {
                return clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                LogManager.getLogger().info("exception while registering arg packet " + clazz.getName());
                e.printStackTrace();
            }
            return null;
        }));
    }

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(Chaos.MODID, "main"),
                () -> "1.0",
                s -> true,
                s -> true);


        register(PacketTimerRestart.class);

        registerArg(PacketTimerSet.class, (buf) -> new PacketTimerSet(buf.readInt()));
//        INSTANCE.messageBuilder(PacketTimerRestart.class, nextID())
//                .encoder((packetTimerRestart, packetBuffer) -> {})
//                .decoder(buf -> new PacketTimerRestart())
//                .consumer(PacketTimerRestart::handle)
//                .add();

        register(PacketTimerSync.class);

        register(TEventCantStopJumping.Jump.class);

        register(TEventSpeedRace.Started.class);
        register(TEventSpeedRace.Ended.class);
    }

    public static void sendToClient(Object packet, ServerPlayerEntity player) {
        INSTANCE.sendTo(packet, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToAllClients(Object packet) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), packet);
    }

    public static void sendToServer(Object packet) {
        INSTANCE.sendToServer(packet);
    }
}

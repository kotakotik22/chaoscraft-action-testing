package com.kotakotik.chaoscraft.world_data;

import com.kotakotik.chaoscraft.Chaos;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

public class ChaoscraftWorldData extends WorldSavedData {
    public static final String DATA_NAME = Chaos.MODID + "_GeneralData";

    int chaosTicks = 0;
    public static final String chaosTicksKey = "chaosTicks";

    public ChaoscraftWorldData() {
        super(DATA_NAME);
    }

    public ChaoscraftWorldData(String name) {
        super(name);
    }

    @Override
    public void read(CompoundNBT nbt) {
        chaosTicks = nbt.getInt(chaosTicksKey);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt(chaosTicksKey, chaosTicks);
        return compound;
    }

    public static ChaoscraftWorldData get(World world) {
        ServerWorld overworld = world.getServer().getWorld(World.OVERWORLD);

        DimensionSavedDataManager storage = overworld.getSavedData();
        return storage.getOrCreate(ChaoscraftWorldData::new, DATA_NAME);
    }

    public static ChaoscraftWorldData get(MinecraftServer server) {
        return get(server.getWorld(World.OVERWORLD));
    }
}

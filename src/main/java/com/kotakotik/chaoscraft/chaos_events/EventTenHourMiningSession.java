package com.kotakotik.chaoscraft.chaos_events;

import com.kotakotik.chaoscraft.ChaosEvent;
import com.kotakotik.chaoscraft.ChaosEventRegister;
import com.kotakotik.chaoscraft.config.ExtraEventConfig;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;

@ChaosEventRegister
public class EventTenHourMiningSession extends ChaosEvent {
    @Override
    public String getEnglish() {
        return "Ten hour mining session";
    }

    @Override
    public String getId() {
        return "ten_hour_mining_session";
    }

    public static boolean isArmorSlot(int slot) {
        return slot > 35 & slot < 40;
    }

    @Override
    public void start(MinecraftServer server) {
        boolean fillArmor = FILL_ARMOR.getBoolConfigValue().get();
        int cobbleInSlot = COBBLE_IN_SLOT.getIntConfigValue().get();
        int stacksOfCobble = STACKS_OF_COBBLE.getIntConfigValue().get();

        for(ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
            int todr = stacksOfCobble;
            for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
                if(isArmorSlot(i) & !fillArmor) continue;
                if(player.inventory.getStackInSlot(i).isEmpty() && todr > 0) {
                    todr--;
                    player.inventory.setInventorySlotContents(i, new ItemStack(Blocks.COBBLESTONE, cobbleInSlot));
                }
            }
            player.dropItem(new ItemStack(Blocks.COBBLESTONE, cobbleInSlot * todr), false);
        }
    }

    public static ExtraEventConfig FILL_ARMOR;
    public static ExtraEventConfig COBBLE_IN_SLOT;
    public static ExtraEventConfig STACKS_OF_COBBLE;

    @Override
    public List<ExtraEventConfig> getExtraConfig() {
        List<ExtraEventConfig> list = new ArrayList<>();
        FILL_ARMOR = new ExtraEventConfig(this, "fillArmor", "Whether to fill the armor slots with cobblestone", true);
        list.add(FILL_ARMOR);
        COBBLE_IN_SLOT = new ExtraEventConfig(this, "cobbleInSlot", "How much cobblestone to put in each slot", 64);
        list.add(COBBLE_IN_SLOT);
        STACKS_OF_COBBLE = new ExtraEventConfig(this, "stacksOfCobble", "How may stacks of cobblestone to give", 50);
        list.add(STACKS_OF_COBBLE);
        return list;
    }
}

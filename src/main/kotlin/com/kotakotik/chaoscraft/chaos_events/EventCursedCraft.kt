package com.kotakotik.chaoscraft.chaos_events

import com.kotakotik.chaoscraft.chaos_event_stuff.ChaosEvent
import net.minecraft.item.ItemStack
import net.minecraft.server.MinecraftServer


class EventCursedCraft : ChaosEvent {
    override fun getEnglish(): String = "CursedCraft"
    override fun getEnglishDescription(): String {
        return "Sets the amount of items in the current held stack to the max stack size of it + 1\n" +
                "\n" +
                "So, with most of the items, the max stack size is 64, so the amount changes to 65, with some items like the snowballs and ender pearls, the max stack size is 16 so the amount changes to 17, and 2 with items with durability like swords";
    }

    override fun getId(): String = "cursed_craft"

    override fun start(server: MinecraftServer) {
        for (player in server.playerManager.playerList) {
            if (!player.mainHandStack.isEmpty) {
                val mhstack: ItemStack = player.mainHandStack
                mhstack.count = mhstack.maxCount + 1
            }
        }
    }
}
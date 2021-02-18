package com.kotakotik.chaoscraft.chaos_event_stuff

import com.kotakotik.chaoscraft.ChaosConfig
import com.kotakotik.chaoscraft.ChaoscraftKotlin
import net.minecraft.server.MinecraftServer

class ChaosEventHandler {
    var ticks = 0;

    fun tick(server: MinecraftServer) {
        val timeForEvent = ChaosConfig.getTimeForEvent()
        ticks++
        if(ticks >= timeForEvent) {
            ticks = 0;
            val event = getRandomEvent();
            event?.start(server)
        }
    }

    companion object {
        fun getRandomEvent(): ChaosEvent? {
            return ChaoscraftKotlin.register?.events?.random()
        }
    }

    fun init() {

    }
}
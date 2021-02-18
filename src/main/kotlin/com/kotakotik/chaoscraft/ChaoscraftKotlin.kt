package com.kotakotik.chaoscraft

import com.kotakotik.chaoscraft.chaos_event_stuff.ChaosEventRegister
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


class ChaoscraftKotlin {
    companion object {
        val log: Logger = LogManager.getLogger("chaoscraft")

        var register: ChaosEventRegister? = null;

        fun init() {
            ServerLifecycleEvents.SERVER_STARTED.register { event ->
                register = ChaosEventRegister();
                register!!.reg()
            }
            ServerTickEvents.END_SERVER_TICK.register { server ->
                register?.tick(server)
            }
        }
    }
}
package com.kotakotik.chaoscraft

import com.kotakotik.chaoscraft.chaos_event_stuff.ChaosEventReg
import com.kotakotik.chaoscraft.chaos_event_stuff.ChaosEventRegister
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class ChaoscraftKotlin {
    companion object {
        val log: Logger = LogManager.getLogger("chaoscraft")

        fun init() {
            ChaosEventRegister.reg();
        }
    }
}
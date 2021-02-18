package com.kotakotik.chaoscraft.chaos_event_stuff

import net.minecraft.server.MinecraftServer




interface ChaosEvent {
    fun getEnglish(): String
    fun getEnglishDescription(): String
    fun getId(): String
    fun start(server: MinecraftServer)
}
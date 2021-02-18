package com.kotakotik.chaoscraft

import com.kotakotik.chaoscraft.keybinds.ChaosKeybindings
import net.minecraft.text.TranslatableText

import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config

import me.shedaniel.clothconfig2.api.ConfigBuilder
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import me.shedaniel.autoconfig.AutoConfig
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents


@Config(name = "chaoscraft")
class ChaosConfig : ConfigData {
    val timeForEvent: Int = 30;
//    fun getTimeForEvent(): Int = 30 * 20

    companion object {
        val title: Text = TranslatableText("text.autoconfig.chaoscraft.title")
        class ConfigScreen : Screen(title) {
            override fun tick() {
                onClose()
            }
        }

        fun getTimeForEvent(): Int {
            return (get()?.timeForEvent ?: 30) * 20
        }

        fun get(): ChaosConfig? {
            return AutoConfig.getConfigHolder(ChaosConfig::class.java).config
        }

        fun open(parent: Screen) {
            val screen = AutoConfig.getConfigScreen(ChaosConfig::class.java, parent).get()
            MinecraftClient.getInstance().openScreen(screen)
        }

        fun open() {
            open(ConfigScreen())
        }

        init {
            ClientTickEvents.END_CLIENT_TICK.register {
                if(ChaosKeybindings.openConfig?.wasPressed() == true) {
                    open()
                }
            }
        }
    }

//    fun makeBuilder(): ConfigBuilder {
//        val builder = ConfigBuilder.create()
//            .setParentScreen(ConfigScreen())
//            .setTitle(title)
//        builder.savingRunnable = Runnable {
//            builder.
//        }
//        return builder
//    }
//
//    fun makeScreen(): Screen {
//        return makeBuilder().build();
//    }
//
//    fun openConfig() {
//        MinecraftClient.getInstance().openScreen(makeScreen())
//    }
}
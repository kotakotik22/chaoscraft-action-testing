package com.kotakotik.chaoscraft.keybinds

import net.minecraft.client.util.InputUtil

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.options.KeyBinding
import org.lwjgl.glfw.GLFW


object ChaosKeybindings {
    fun add(name: String, key: Int, type: InputUtil.Type): KeyBinding? {
        return KeyBindingHelper.registerKeyBinding(
            KeyBinding(
                "KEY.chaoscraft.$name",  // The translation key of the keybinding's name
                type,  // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                key,  // The keycode of the key
                "CATEGORY.chaoscraft.main" // The translation key of the keybinding's category.
            )
        )
    }

    fun add(name: String, key: Int): KeyBinding? {
       return add(name, key, InputUtil.Type.KEYSYM)
    }

    val openConfig = add("open_config", GLFW.GLFW_KEY_O)
}
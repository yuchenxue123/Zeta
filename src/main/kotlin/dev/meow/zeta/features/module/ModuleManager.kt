package dev.meow.zeta.features.module

import com.mojang.blaze3d.platform.InputConstants
import dev.meow.zeta.event.EventListener
import dev.meow.zeta.event.handler
import dev.meow.zeta.events.game.KeyboardEvent
import dev.meow.zeta.features.module.misc.ModuleEmpty
import dev.meow.zeta.features.module.movement.ModuleSprint
import dev.meow.zeta.features.module.render.ModuleClickScreen
import dev.meow.zeta.utils.client.mc
import net.minecraft.client.gui.screens.ChatScreen
import org.lwjgl.glfw.GLFW

object ModuleManager : EventListener {

    internal val modules = mutableListOf<ClientModule>()

    init {
        register(

            ModuleSprint,

            ModuleClickScreen,

            ModuleEmpty,

        )
    }

    fun register(module: ClientModule) {
        this.modules.add(module)
        module.initialize()
    }

    fun register(vararg modules: ClientModule) {
        modules.forEach(this::register)
    }

    @Suppress("unused")
    private val onKeyPress = handler<KeyboardEvent> { event ->
        if (mc.screen is ChatScreen) return@handler

        when (event.action) {
            GLFW.GLFW_PRESS -> {
                modules.filter {
                    it.bind.key == event.key
                }.forEach { module ->
                    module.toggle()
                }
            }
        }
    }
}
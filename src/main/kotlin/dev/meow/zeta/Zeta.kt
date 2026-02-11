package dev.meow.zeta

import dev.meow.zeta.config.ConfigSystem
import dev.meow.zeta.event.EventManager
import dev.meow.zeta.features.module.ModuleManager
import dev.meow.zeta.render.nano.NanoRenderer
import dev.meow.zeta.utils.client.FileSystem
import dev.meow.zeta.utils.logger.Logger
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.SpecialGuiElementRegistry

object Zeta : ClientModInitializer {

    const val NAME = "Zeta"
    const val NAMESPACE = "zeta"

    val logger = Logger(NAME)

    override fun onInitializeClient() {
        SpecialGuiElementRegistry.register(::NanoRenderer)
    }

    fun initialize() {
        FileSystem

        EventManager

        ModuleManager

        ConfigSystem.load("default")
    }

    fun shutdown() {
        ConfigSystem.save("default")
    }
}
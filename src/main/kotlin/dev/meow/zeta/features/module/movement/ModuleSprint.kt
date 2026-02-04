package dev.meow.zeta.features.module.movement

import dev.meow.zeta.config.Configurable
import dev.meow.zeta.core.Mode
import dev.meow.zeta.event.handler
import dev.meow.zeta.events.game.KeyboardEvent
import dev.meow.zeta.features.module.ClientModule
import dev.meow.zeta.features.module.Category
import org.lwjgl.glfw.GLFW

object ModuleSprint : ClientModule(
    name = "Sprint",
    category = Category.MOVEMENT,
    key = GLFW.GLFW_KEY_V
) {

    private var left by boolean("Left", false)
    private val always = tree(Always)

    private val mode by enum("Mode", PrintMode.Safe)

    object Always : Configurable("Always") {

        private val safe by boolean("Safe", false)

    }

    private val handler = handler<KeyboardEvent> {
        left = true
    }

    enum class PrintMode(override val showName: String) : Mode {
        Safe("Safe"),
        Task("Task"),
    }

}
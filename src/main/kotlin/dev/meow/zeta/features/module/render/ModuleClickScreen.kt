package dev.meow.zeta.features.module.render

import dev.meow.zeta.features.module.ClientModule
import dev.meow.zeta.features.module.Category
import dev.meow.zeta.ui.screen.ScreenManager
import dev.meow.zeta.ui.screen.clickgui.dropdown.DropdownClickScreen
import org.lwjgl.glfw.GLFW

object ModuleClickScreen : ClientModule(
    name = "ClickScreen",
    category = Category.RENDER,
    key = GLFW.GLFW_KEY_RIGHT_SHIFT
) {

    override fun onEnable() {
        ScreenManager.displayScreen(DropdownClickScreen)
        toggle()
    }

}
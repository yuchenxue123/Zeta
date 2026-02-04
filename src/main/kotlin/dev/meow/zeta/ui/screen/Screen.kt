package dev.meow.zeta.ui.screen

import dev.meow.zeta.ui.engine.Element
import org.lwjgl.glfw.GLFW

abstract class Screen : Element {

    private var mustRemove = false

    fun init() {
        mustRemove = false
    }

    open fun onOpen() {}

    open fun onClose() {}

    open fun close() {
        mustRemove = true
    }

    fun shouldRemove() = mustRemove

    override fun keyPressed(keycode: Int, scancode: Int, modifiers: Int): Boolean {
        if (keycode == GLFW.GLFW_KEY_ESCAPE) {
            close()
            return true
        }

        return false
    }

}
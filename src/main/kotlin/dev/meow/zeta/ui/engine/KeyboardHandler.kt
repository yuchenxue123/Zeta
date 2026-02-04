package dev.meow.zeta.ui.engine

interface KeyboardHandler {

    fun keyPressed(keycode: Int, scancode: Int, modifiers: Int): Boolean = false

    fun keyReleased(keycode: Int, scancode: Int, modifiers: Int): Boolean = false

}
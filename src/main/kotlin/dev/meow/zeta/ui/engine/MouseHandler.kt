package dev.meow.zeta.ui.engine

interface MouseHandler {

    fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean = false

    fun mouseReleased(mouseX: Double, mouseY: Double, button: Int): Boolean = false

    fun mouseScrolled(mouseX: Double, mouseY: Double, horizontalAmount: Double, verticalAmount: Double): Boolean = false

}
package dev.meow.zeta.ui.component

import dev.meow.zeta.render.engine.Rect

fun Rect.isHovered(mouseX: Double, mouseY: Double): Boolean {
    return mouseX >= x() && mouseX <= x() + width() && mouseY >= y() && mouseY <= y() + height()
}
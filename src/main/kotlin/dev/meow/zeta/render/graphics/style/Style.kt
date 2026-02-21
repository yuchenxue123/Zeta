package dev.meow.zeta.render.graphics.style

import dev.meow.zeta.render.graphics.Orientation
import dev.meow.zeta.render.graphics.PaintMode
import java.awt.Color

val NONE_COLOR = Color(0, 0, 0, 0)

data class RenderStyle(
    val color: Color,
    val mode: PaintMode = PaintMode.FILL,
    val thickness: Float = 1f
)

data class GradientStyle(
    val first: Color,
    val second: Color,
    val mode: PaintMode = PaintMode.FILL,
    val thickness: Float = 1f,
    val orientation: Orientation = Orientation.HORIZONTAL
)
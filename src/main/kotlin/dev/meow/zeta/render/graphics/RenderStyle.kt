package dev.meow.zeta.render.graphics

import java.awt.Color

data class RenderStyle(
    val color: Color,
    val mode: PaintMode = PaintMode.FILL,
    val thickness: Float = 1f
)

package dev.meow.zeta.render.graphics

import dev.meow.zeta.render.engine.Positionable
import dev.meow.zeta.render.graphics.font.FontRenderer
import net.minecraft.client.gui.GuiGraphics
import java.awt.Color

interface RenderContext {

    fun drawing(graphics: GuiGraphics, block: RenderCallback)

    fun drawRect(x: Float, y: Float, width: Float, height: Float, style: RenderStyle)

    fun drawRoundRect(x: Float, y: Float, width: Float, height: Float, radius: Float, style: RenderStyle)

    fun drawCircle(x: Float, y: Float, radius: Float, style: RenderStyle)

    fun drawText(font: FontRenderer, text: String, x: Float, y: Float, color: Color)

    fun drawLineAbsolute(x: Float, y: Float, m: Float, n: Float, style: RenderStyle)

    fun drawLine(x: Float, y: Float, long: Float, style: RenderStyle, orientation: Orientation)

    fun drawPolygon(x: Float, y: Float, points: Array<Positionable>, style: RenderStyle)

    fun drawLines(
        x: Float,
        y: Float,
        points: Array<Positionable>,
        thickness: Float = 1f,
        color: Color,
        close: Boolean = true
    )

    fun drawArrow(x: Float, y: Float, size: Float, angle: Float, color: Color)

    fun drawIsland(x: Float, y: Float, width: Float, height: Float, color: Color, radius: Float = 12f)

    fun clipRect(x: Float, y: Float, width: Float, height: Float, block: () -> Unit)

    fun alpha(alpha: Float)

    fun save()

    fun restore()

    fun translate(x: Float, y: Float)

    fun scale(x: Float, y: Float = x)

}
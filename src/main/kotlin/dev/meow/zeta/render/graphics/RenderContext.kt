package dev.meow.zeta.render.graphics

import dev.meow.zeta.render.graphics.font.FontRenderer
import net.minecraft.client.gui.GuiGraphics
import org.joml.Vector2f
import java.awt.Color

interface RenderContext {

    fun drawing(graphics: GuiGraphics, block: RenderCallback)

    fun drawRect(x: Float, y: Float, width: Float, height: Float, color: Color)

    fun drawOutlineRect(x: Float, y: Float, width: Float, height: Float, thickness: Float = 1f, color: Color)

    fun drawRoundRect(x: Float, y: Float, width: Float, height: Float, radius: Float, color: Color)

    fun drawOutlineRoundRect(x: Float, y: Float, width: Float, height: Float, radius: Float, thickness: Float = 1f, color: Color)

    fun drawCircle(x: Float, y: Float, radius: Float, color: Color)

    fun drawText(font: FontRenderer, text: String, x: Float, y: Float, color: Color)

    fun drawLine(x: Float, y: Float, m: Float, n: Float, thickness: Float = 1f, color: Color)

    fun drawVerticalLine(x: Float, y: Float, long: Float, thickness: Float = 1f, color: Color)

    fun drawHorizontalLine(x: Float, y: Float, long: Float, thickness: Float = 1f, color: Color)

    fun drawPolygon(x: Float, y: Float, points: Array<Vector2f>, color: Color)

    fun drawPolyline(
        x: Float,
        y: Float,
        points: Array<Vector2f>,
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
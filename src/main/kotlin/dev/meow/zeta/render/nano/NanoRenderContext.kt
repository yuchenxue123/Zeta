package dev.meow.zeta.render.nano

import dev.meow.zeta.render.FontManager
import dev.meow.zeta.render.createBounds
import dev.meow.zeta.render.graphics.font.FontRenderer
import dev.meow.zeta.render.graphics.RenderCallback
import dev.meow.zeta.render.graphics.RenderContext
import dev.meow.zeta.utils.extension.toRadians
import net.minecraft.client.gui.GuiGraphics
import org.joml.Vector2f
import org.lwjgl.nanovg.NanoVG
import org.lwjgl.nanovg.NanoVGGL3
import java.awt.Color

object NanoRenderContext : RenderContext {

    var context = -1L

    init {
        context = NanoVGGL3.nvgCreate(NanoVGGL3.NVG_ANTIALIAS or NanoVGGL3.NVG_STENCIL_STROKES)

        if (context == -1L) {
            throw RuntimeException("Failed to initialize NanoVG")
        }

        FontManager.initialize(NanoFontRendererProvider)
    }

    override fun save() {
        NanoVG.nvgSave(context)
    }

    override fun restore() {
        NanoVG.nvgRestore(context)
    }

    override fun translate(x: Float, y: Float) {
        NanoVG.nvgTranslate(context, x, y)
    }

    override fun scale(x: Float, y: Float) {
        NanoVG.nvgScale(context, x, y)
    }

    fun beginFrame(width: Float, height: Float) {
        NanoVG.nvgBeginFrame(context, width, height, 1f)
        NanoVG.nvgTextAlign(context, NanoVG.NVG_ALIGN_LEFT or NanoVG.NVG_ALIGN_TOP)
    }

    fun endFrame() {
        NanoVG.nvgEndFrame(context)
    }

    override fun drawing(graphics: GuiGraphics, block: RenderCallback) {
        graphics.guiRenderState.submitPicturesInPictureState(
            NanoRenderState(
                0, 0, graphics.guiWidth(), graphics.guiHeight(),
                graphics.scissorStack.peek(),
                graphics.createBounds(
                    0f, 0f,
                    graphics.guiWidth().toFloat(),
                    graphics.guiHeight().toFloat()
                )
            ) {
                block.render()
            }
        )
    }

    override fun drawRect(x: Float, y: Float, width: Float, height: Float, color: Color) {
        withColor(color) { color ->
            NanoVG.nvgBeginPath(context)
            NanoVG.nvgRect(context, x, y, width, height)
            NanoVG.nvgFillColor(context, color)
            NanoVG.nvgFill(context)
        }
    }

    override fun drawOutlineRect(x: Float, y: Float, width: Float, height: Float, thickness: Float, color: Color) {
        withColor(color) { color ->
            NanoVG.nvgBeginPath(context)
            NanoVG.nvgRect(context, x, y, width, height)
            NanoVG.nvgStrokeColor(context, color)
            NanoVG.nvgStrokeWidth(context, thickness)
            NanoVG.nvgStroke(context)
        }
    }

    override fun drawRoundRect(x: Float, y: Float, width: Float, height: Float, radius: Float, color: Color) {
        withColor(color) { color ->
            NanoVG.nvgBeginPath(context)
            NanoVG.nvgRoundedRect(context, x, y, width, height, radius)
            NanoVG.nvgFillColor(context, color)
            NanoVG.nvgFill(context)
        }
    }

    override fun drawOutlineRoundRect(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        radius: Float,
        thickness: Float,
        color: Color
    ) {
        withColor(color) { color ->
            NanoVG.nvgBeginPath(context)
            NanoVG.nvgRoundedRect(context, x, y, width, height, radius)
            NanoVG.nvgStrokeColor(context, color)
            NanoVG.nvgStrokeWidth(context, thickness)
            NanoVG.nvgStroke(context)
        }
    }

    override fun drawCircle(x: Float, y: Float, radius: Float, color: Color) {
        withColor(color) { color ->
            NanoVG.nvgBeginPath(context)
            NanoVG.nvgCircle(context, x, y, radius)
            NanoVG.nvgFillColor(context, color)
            NanoVG.nvgFill(context)
        }
    }

    override fun drawLine(x: Float, y: Float, m: Float, n: Float, thickness: Float, color: Color) {
        withColor(color) { color ->
            NanoVG.nvgBeginPath(context)
            NanoVG.nvgMoveTo(context, x, y)
            NanoVG.nvgLineTo(context, m, n)
            NanoVG.nvgStrokeColor(context, color)
            NanoVG.nvgStrokeWidth(context, thickness)
            NanoVG.nvgStroke(context)
        }
    }

    override fun drawHorizontalLine(x: Float, y: Float, long: Float, thickness: Float, color: Color) {
        drawLine(x, y, x + long, y, thickness, color)
    }

    override fun drawVerticalLine(x: Float, y: Float, long: Float, thickness: Float, color: Color) {
        drawLine(x, y, x, y + long, thickness, color)
    }

    override fun drawPolygon(x: Float, y: Float, points: Array<Vector2f>, color: Color) {
        if (points.size < 2) return

        withColor(color) { color ->
            NanoVG.nvgBeginPath(context)

            NanoVG.nvgMoveTo(context, x, y)

            points.forEach { p ->
                NanoVG.nvgLineTo(context, p.x, p.y)
            }

            NanoVG.nvgClosePath(context)
            NanoVG.nvgFillColor(context, color)
            NanoVG.nvgFill(context)
        }

    }

    override fun drawPolyline(
        x: Float,
        y: Float,
        points: Array<Vector2f>,
        thickness: Float,
        color: Color,
        close: Boolean
    ) {
        if (points.isEmpty()) return

        withColor(color) { color ->
            NanoVG.nvgBeginPath(context)

            NanoVG.nvgMoveTo(context, x, y)

            points.forEach { p ->
                NanoVG.nvgLineTo(context, p.x, p.y)
            }

            NanoVG.nvgClosePath(context)
            NanoVG.nvgStrokeColor(context, color)
            NanoVG.nvgStrokeWidth(context, thickness)
            NanoVG.nvgStroke(context)
        }
    }

    override fun drawText(font: FontRenderer, text: String, x: Float, y: Float, color: Color) {
        font.drawText(text, x, y, color)
    }

    override fun drawArrow(x: Float, y: Float, size: Float, angle: Float, color: Color) {
        save()

        NanoVG.nvgTranslate(context, x + size / 2f, y + size / 2f)
        NanoVG.nvgRotate(context, angle.toRadians())

        NanoVG.nvgTranslate(context, -size / 2f, -size / 2f)

        drawPolyline(
            0f,
            0f,
            arrayOf(
                Vector2f(size, size / 2),
                Vector2f(0f, size),
            ),
            1f,
            color
        )

        restore()
    }

    override fun drawIsland(x: Float, y: Float, width: Float, height: Float, color: Color, radius: Float) {
        withColor(color) { color ->
            NanoVG.nvgBeginPath(context)

            NanoVG.nvgMoveTo(context, x, y)

            NanoVG.nvgArcTo(context, x - radius, y, x - radius, y + radius, radius)
            NanoVG.nvgLineTo(context, x - radius, y + height - radius)

            NanoVG.nvgArcTo(context, x - radius, y + height, x + radius, y + height, radius)
            NanoVG.nvgLineTo(context, x + width, y + height)

            NanoVG.nvgArcTo(context, x + width + radius, y + height, x + width + radius, y + height - radius, radius)
            NanoVG.nvgLineTo(context, x + width + radius, y + radius)

            NanoVG.nvgArcTo(context, x + width + radius, y, x + width, y, radius)

            NanoVG.nvgClosePath(context)

            NanoVG.nvgFillColor(context, color)
            NanoVG.nvgFill(context)
        }
    }

    override fun clipRect(x: Float, y: Float, width: Float, height: Float, block: () -> Unit) {
        save()

        NanoVG.nvgIntersectScissor(context, x, y, width, height)

        block.invoke()

        restore()
    }

    override fun alpha(alpha: Float) {
        NanoVG.nvgGlobalAlpha(context, alpha)
    }

}
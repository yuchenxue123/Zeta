package dev.meow.zeta.render.nano

import dev.meow.zeta.render.graphics.font.FontRendererVisitor
import dev.meow.zeta.render.graphics.font.FontRenderer
import net.minecraft.network.chat.Component
import org.lwjgl.nanovg.NanoVG
import java.awt.Color

class NanoFontRenderer(
    val name: String,
    val font: Int,
    val size: Float
) : FontRenderer {

    private val context: Long
        get() = NanoRenderContext.context

    override fun width(text: Component): Float {
        val sb = StringBuilder()
        text.visualOrderText.accept { _, _, code ->
            sb.append(Char(code))
            true
        }

        return width(sb.toString())
    }

    override fun width(text: String): Float {
        val bounds = FloatArray(4)

        NanoVG.nvgFontSize(context, size)
        NanoVG.nvgFontFaceId(context, font)

        NanoVG.nvgTextBounds(context, 0f, 0f, text, bounds)

        return bounds[2] - bounds[0]
    }

    override fun height(text: Component): Float {
        val sb = StringBuilder()
        text.visualOrderText.accept { _, _, code ->
            sb.append(Char(code))
            true
        }

        return height(sb.toString())
    }

    override fun height(text: String): Float {
        val bounds = FloatArray(4)

        NanoVG.nvgFontSize(context, size)
        NanoVG.nvgFontFaceId(context, font)

        NanoVG.nvgTextBounds(context, 0f, 0f, text, bounds)

        return bounds[3] - bounds[1]
    }

    override fun drawText(text: Component, x: Float, y: Float, color: Color): Float {
        val visitor = FontRendererVisitor(this, x, y, color)
        text.visualOrderText.accept(visitor)
        return visitor.final()
    }

    override fun drawCenteredText(text: Component, x: Float, y: Float, width: Float, height: Float, color: Color): Float {
        return drawText(text, x + (width - width(text)) / 2f, y + (height - height(text)) / 2f, color)
    }

    override fun drawText(text: String, x: Float, y: Float, color: Color): Float {
        return withColor(color) { color ->
            NanoVG.nvgFontSize(context, size)
            NanoVG.nvgFontFaceId(context, font)
            NanoVG.nvgFillColor(context, color)

            NanoVG.nvgText(context, x, y, text)
        }
    }

    override fun drawCenteredText(text: String, x: Float, y: Float, width: Float, height: Float, color: Color): Float {
        return drawText(text, x + (width - width(text)) / 2f, y + (height - height(text)) / 2f, color)
    }
}
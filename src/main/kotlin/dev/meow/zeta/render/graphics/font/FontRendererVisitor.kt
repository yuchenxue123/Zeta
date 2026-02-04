package dev.meow.zeta.render.graphics.font

import dev.meow.zeta.utils.extension.alpha
import net.minecraft.network.chat.Style
import net.minecraft.util.FormattedCharSink
import java.awt.Color

class FontRendererVisitor(
    private val font: FontRenderer,
    private val x: Float,
    private val y: Float,
    private val color: Color,
) : FormattedCharSink {

    private var current = x

    override fun accept(index: Int, style: Style, code: Int): Boolean {
        val color = style.color?.let {
            Color(it.value).alpha(color.alpha)
        } ?: color

        val text = String(Character.toChars(code))

        current = font.drawText(text, x, y, color)

        return true
    }

    fun final() = current
}
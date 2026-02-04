package dev.meow.zeta.ui.component

import dev.meow.zeta.render.RenderUtils
import dev.meow.zeta.render.engine.Rect
import dev.meow.zeta.render.engine.Rectangle
import dev.meow.zeta.render.graphics.font.FontRenderer
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.Component
import java.awt.Color

class TextComponent(
    val text: Component,
    val font: FontRenderer
) : BaseComponent(), Rect by Rectangle.empty() {

    constructor(
        text: String,
        font: FontRenderer
    ) : this(Component.literal(text), font)

    private val padding = Padding(0f, 0f)

    private var background: Color = Color(1, true)

    private var color: Color = Color.WHITE

    override fun render(context: GuiGraphics, mouseX: Double, mouseY: Double, deltaTime: Float) {

        RenderUtils.drawRect(x(), y(), width(), height(), background)

        font.drawText(text, x() + padding.x(), y() + padding.y(), color)

    }

    fun padding(x: Float = 0f, y: Float = 0f) = apply {
        padding.setPosition(x, y)
    }

    fun background(color: Color) = apply {
        this.background = color
    }

    fun color(color: Color) = apply {
        this.color = color
    }

    override fun width(): Float = padding.x() * 2 + font.width(text)

    override fun height(): Float = padding.y() * 2 + font.height(text)

}
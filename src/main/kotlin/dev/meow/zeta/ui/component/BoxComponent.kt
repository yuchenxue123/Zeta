package dev.meow.zeta.ui.component

import dev.meow.zeta.render.RenderUtils
import dev.meow.zeta.render.engine.Rect
import dev.meow.zeta.render.engine.Rectangle
import dev.meow.zeta.render.graphics.PaintMode
import dev.meow.zeta.render.graphics.RenderStyle
import dev.meow.zeta.utils.holder.writeOnce
import net.minecraft.client.gui.GuiGraphics
import java.awt.Color

open class BoxComponent(
    rectangle: Rect,
    onClick: BoxComponent.(button: Int) -> Unit = {}
) : BaseComponent(), Rect by rectangle {

    constructor(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        onClick: BoxComponent.(button: Int) -> Unit = {}
    ) : this(Rectangle(x, y, width, height), onClick)

    private var fill: Boolean = true

    private var radius: Float = 0f

    private var color: Color = Color(1, true)

    private var onClick: BoxComponent.(button: Int) -> Unit by writeOnce(onClick)

    override fun render(context: GuiGraphics, mouseX: Double, mouseY: Double, deltaTime: Float) {
        val mode = PaintMode.fromBoolean(fill = fill)
        val style = RenderStyle(color, mode)

        if (radius > 0f) {
            RenderUtils.drawRoundRect(x(), y(), width(), height(), radius, style)
        } else {
            RenderUtils.drawRect(x(), y(), width(), height(), style)
        }
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        if (isHovered(mouseX, mouseY)) {
            onClick.invoke(this, button)
            return true
        }
        return false
    }

    fun fill(fill: Boolean): BoxComponent = apply {
        this.fill = fill
    }

    fun radius(radius: Float): BoxComponent = apply {
        this.radius = radius
    }

    fun color(color: Color): BoxComponent = apply {
        this.color = color
    }

    fun onClick(onClick: BoxComponent.(button: Int) -> Unit): BoxComponent = apply {
        this.onClick = onClick
    }

}
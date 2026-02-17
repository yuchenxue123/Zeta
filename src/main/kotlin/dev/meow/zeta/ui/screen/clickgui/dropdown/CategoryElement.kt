package dev.meow.zeta.ui.screen.clickgui.dropdown

import dev.meow.zeta.features.module.Category
import dev.meow.zeta.render.FontManager
import dev.meow.zeta.render.RenderUtils
import dev.meow.zeta.render.engine.Rect
import dev.meow.zeta.render.graphics.Orientation
import dev.meow.zeta.render.graphics.style.GradientStyle
import dev.meow.zeta.render.graphics.style.NONE_COLOR
import dev.meow.zeta.ui.engine.Element
import net.minecraft.client.gui.GuiGraphics
import java.awt.Color

class CategoryElement(
    private val category: Category,
    rectangle: Rect
) : Element, Rect by rectangle {

    private val elements = mutableListOf<Element>()

    private var open = false

    init {
        build()
    }

    override fun render(context: GuiGraphics, mouseX: Double, mouseY: Double, deltaTime: Float) {
        RenderUtils.drawGradientRoundRect(
            x(), y(), width(),  height(), DropdownConstants.CategoryRadius,
            GradientStyle(
                first = category.color,
                second = Color(40, 40, 40),
                orientation = Orientation.VERTICAL
            )
        )

        FontManager.Genshin.drawCenteredText(
            category.showName,
            x(), y(), width(), height(),
            Color.WHITE
        )
    }

    private fun build() {

    }

}
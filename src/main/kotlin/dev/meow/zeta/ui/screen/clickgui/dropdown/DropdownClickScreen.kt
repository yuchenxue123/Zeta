package dev.meow.zeta.ui.screen.clickgui.dropdown

import dev.meow.zeta.features.module.Category
import dev.meow.zeta.render.engine.Rectangle
import dev.meow.zeta.ui.engine.Element
import dev.meow.zeta.ui.screen.Screen
import dev.meow.zeta.ui.screen.clickgui.dropdown.DropdownConstants.CategoryHeight
import dev.meow.zeta.ui.screen.clickgui.dropdown.DropdownConstants.CategoryHorizontalSpace
import dev.meow.zeta.ui.screen.clickgui.dropdown.DropdownConstants.CategoryStartX
import dev.meow.zeta.ui.screen.clickgui.dropdown.DropdownConstants.CategoryStartY
import dev.meow.zeta.ui.screen.clickgui.dropdown.DropdownConstants.CategoryWidth
import net.minecraft.client.gui.GuiGraphics

object DropdownClickScreen : Screen() {

    val categoryElements = mutableListOf<Element>()

    init {
        build()
    }

    override fun render(context: GuiGraphics, mouseX: Double, mouseY: Double, deltaTime: Float) {
        categoryElements.forEach { element ->
            element.render(context, mouseX, mouseY, deltaTime)
        }
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {

        categoryElements.asReversed().forEach { element ->
            if (element.mouseClicked(mouseX, mouseY, button)) {
                categoryElements.sortedBy { it == element }
                return true
            }
        }

        return super.mouseClicked(mouseX, mouseY, button)
    }

    override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int): Boolean {

        categoryElements.asReversed().forEach { element ->
            if (element.mouseReleased(mouseX, mouseY, button)) {
                return true
            }
        }

        return super.mouseReleased(mouseX, mouseY, button)
    }

    private fun build() {
        Category.categories.forEachIndexed { index, category ->
            categoryElements.add(
                CategoryElement(
                    category = category,
                    rectangle =  Rectangle.of(
                        x = CategoryStartX + index * (CategoryWidth + CategoryHorizontalSpace),
                        y = CategoryStartY,
                        width = CategoryWidth,
                        height = CategoryHeight
                    )
                )
            )
        }

    }

}
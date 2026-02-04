package dev.meow.zeta.ui.screen.clickgui.dropdown

import dev.meow.zeta.features.module.Category
import dev.meow.zeta.render.engine.Rect
import dev.meow.zeta.ui.engine.Element

class CategoryElement(
    category: Category,
    rectangle: Rect
) : Element, Rect by rectangle {

    private val elements = mutableListOf<Element>()

    private var open = false

}
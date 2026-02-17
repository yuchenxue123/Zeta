package dev.meow.zeta.features.module

import dev.meow.zeta.core.Nameable
import java.awt.Color

class Category private constructor(
    override val showName: String,
    val color: Color
) : Nameable {

    companion object {

        private val map = mutableMapOf<String, Category>()

        val categories: Collection<Category>
            get() = map.values

        val COMBAT = create("Combat", Color(255, 82, 82))

        val MOVEMENT = create("Movement", Color(68, 138, 255))

        val PLAYER = create("Player", Color(255, 171, 64))

        val RENDER = create("Redner", Color(105, 240, 174))

        val MISC = create("Misc", Color(128, 128, 128))

        fun create(name: String, color: Color): Category {
            val category = Category(name, color)
            map.putIfAbsent(name, category)
            return category
        }

    }

    val modules: List<ClientModule>
        get() = ModuleManager.modules.filter { it.category == this }.sortedBy { it.name }
}
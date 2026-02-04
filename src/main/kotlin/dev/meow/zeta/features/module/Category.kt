package dev.meow.zeta.features.module

import dev.meow.zeta.core.Nameable

class Category private constructor(
    override val showName: String,
) : Nameable {

    companion object {

        private val map = mutableMapOf<String, Category>()

        val categories: Collection<Category>
            get() = map.values

        val COMBAT = of("Combat")

        val MOVEMENT = of("Movement")

        val PLAYER = of("Player")

        val RENDER = of("Redner")

        val MISC = of("Misc")

        fun of(name: String): Category {
            val category = Category(name)
            map.putIfAbsent(name, category)
            return category
        }

    }

    val modules: List<ClientModule>
        get() = ModuleManager.modules.filter { it.category == this }.sortedBy { it.name }
}
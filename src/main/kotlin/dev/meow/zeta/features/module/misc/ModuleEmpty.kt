package dev.meow.zeta.features.module.misc

import dev.meow.zeta.features.module.ClientModule
import dev.meow.zeta.features.module.Category

object ModuleEmpty : ClientModule(
    name = "Testing",
    category = Category.MISC,
) {

    init {
        state = true
    }

}
package dev.meow.zeta.render

import dev.meow.zeta.render.graphics.font.FontRenderer
import dev.meow.zeta.render.graphics.font.FontRendererProvider
import dev.meow.zeta.utils.holder.writeOnce

object FontManager {

    var Genshin: FontRenderer by writeOnce(FontRenderer.empty())
    var Genshin12: FontRenderer by writeOnce(FontRenderer.empty())
    var Genshin15: FontRenderer by writeOnce(FontRenderer.empty())
    var Genshin24: FontRenderer by writeOnce(FontRenderer.empty())

    fun initialize(provider: FontRendererProvider) {
        Genshin = provider.createFont("genshin", "ttf", 18f)
        Genshin12 = provider.createFont("genshin", "ttf", 12f)
        Genshin15 = provider.createFont("genshin", "ttf", 15f)
        Genshin24 = provider.createFont("genshin", "ttf", 24f)
    }

}
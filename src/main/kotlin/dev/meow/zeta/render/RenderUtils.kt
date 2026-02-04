package dev.meow.zeta.render

import dev.meow.zeta.render.graphics.RenderContext
import dev.meow.zeta.render.nano.NanoRenderContext

private val context = NanoRenderContext

object RenderUtils : RenderContext by context
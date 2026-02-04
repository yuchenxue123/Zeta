package dev.meow.zeta.ui.component

import dev.meow.zeta.render.engine.Position
import dev.meow.zeta.render.engine.Positionable

class Padding(
    x: Float,
    y: Float,
) : Positionable by Position(x, y)
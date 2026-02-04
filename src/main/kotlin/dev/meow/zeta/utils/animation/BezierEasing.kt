package dev.meow.zeta.utils.animation

import dev.meow.zeta.utils.math.BezierCurve
import dev.meow.zeta.utils.math.Point

class BezierEasing(
    first: Point,
    second: Point,
) : Easing {

    constructor(
        x1: Float,
        y1: Float,
        x2: Float,
        y2: Float,
    ) : this(Point(x1, y1), Point(x2, y2))

    private val bezier = BezierCurve(first, second)

    override fun ease(process: Float): Float {
        return bezier.calculateBezierY(bezier.calculateProcessFromX(process))
    }

}
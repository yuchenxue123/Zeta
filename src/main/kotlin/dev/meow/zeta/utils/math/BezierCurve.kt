package dev.meow.zeta.utils.math

import kotlin.math.abs

class BezierCurve(
    val start: Point,
    val first: Point,
    val second: Point,
    val end: Point,
) {
    constructor(
        first: Point,
        second: Point
    ) : this(Point(0f, 0f), first, second, Point(1f, 1f))


    companion object {
        private const val BINARY_SEARCH_ITERATIONS = 10
        private const val PRECISION_EPSILON = 0.0001f
    }

    /**
     * 通过曲线进度计算贝塞尔曲线上的点
     *
     * @param process 曲线进度
     *
     * 注：在渲染里请尽量使用 [calculateBezierX] 和 [calculateBezierY]
     */
    fun calculateBezierPoint(process: Float): Point = Point(calculateBezierX(process), calculateBezierY(process))

    fun calculateBezierX(process: Float): Float {
        val pp = process * process
        val ppp = process * process * process
        val q = 1 - process
        val qq = q * q
        val qqq = q * q * q

        return qqq * start.x + 3 * qq * process * first.x + 3 * q * pp * second.x + ppp * end.x
    }

    fun calculateBezierY(process: Float): Float {
        val pp = process * process
        val ppp = process * process * process
        val q = 1 - process
        val qq = q * q
        val qqq = q * q * q

        return qqq * start.y + 3 * qq * process * first.y + 3 * q * pp * second.y + ppp * end.y
    }

    fun calculateProcessFromX(x: Float): Float {
        var low = 0f
        var high = 1f
        var t = 0.5f

        repeat(BINARY_SEARCH_ITERATIONS) {
            val currentX = calculateBezierY(t)

            if (abs(currentX - x) < PRECISION_EPSILON) {
                return t
            }

            if (currentX < x) {
                low = t
            } else {
                high = t
            }

            t = (low + high) / 2.0f
        }

        return t
    }


}
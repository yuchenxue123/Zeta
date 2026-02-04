package dev.meow.zeta.utils.animation

import java.awt.Color
import kotlin.reflect.KProperty

class ColorAnimation(
    easing: Easing
) : Animation<Color> {

    private var _color: Color = Color(1, true)

    private var start: Color = Color(1, true)

    private var target: Color = Color(1, true)

    private var duration = 200f

    fun prepare(block: ColorAnimation.() -> Unit) = apply {
        this.apply(block)
        sync()
    }

    private val redAnimation = SimpleAnimation.empty()
        .prepare {
            from(start.red.toFloat())
            to(target.red.toFloat())
            duration(this@ColorAnimation.duration)
            easing(easing)
        }

    private val greenAnimation = SimpleAnimation.empty()
        .prepare {
            from(start.green.toFloat())
            to(target.green.toFloat())
            duration(duration)
            easing(easing)
        }

    private val blueAnimation = SimpleAnimation.empty()
        .prepare {
            from(start.blue.toFloat())
            to(target.blue.toFloat())
            duration(duration)
            easing(easing)
        }

    private val alphaAnimation = SimpleAnimation.empty()
        .prepare {
            from(start.alpha.toFloat())
            to(target.alpha.toFloat())
            duration(duration)
            easing(easing)
        }

    private val red by redAnimation

    private val green by greenAnimation

    private val blue by blueAnimation

    private val alpha by alphaAnimation


    override fun animate(): Color {
        redAnimation.animate()
        greenAnimation.animate()
        blueAnimation.animate()
        alphaAnimation.animate()

        _color = Color(
            (red / 255f).coerceIn(0f, 1f),
            (green / 255f).coerceIn(0f, 1f),
            (blue / 255f).coerceIn(0f, 1f),
            (alpha / 255f).coerceIn(0f, 1f)
        )

        return _color
    }

    override fun finished(): Boolean {
        return redAnimation.finished()
                && greenAnimation.finished()
                && blueAnimation.finished()
                && alphaAnimation.finished()
    }

    override fun reset() {
        redAnimation.reset()
        greenAnimation.reset()
        blueAnimation.reset()
        alphaAnimation.reset()
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): Color = _color

    fun from(value: Color) = apply {
        this.start = value
    }

    fun to(value: Color) = apply {
        this.target = value
    }

    fun duration(duration: Float) = apply {
        this.duration = duration
    }

    fun sync() = apply {
        redAnimation.prepare {
            from(start.red.toFloat())
            to(target.red.toFloat())
            duration(duration)
        }
        greenAnimation.prepare {
            from(start.green.toFloat())
            to(target.green.toFloat())
            duration(duration)
        }

        blueAnimation.prepare {
            from(start.blue.toFloat())
            to(target.blue.toFloat())
            duration(duration)
        }

        alphaAnimation.prepare {
            from(start.alpha.toFloat())
            to(target.alpha.toFloat())
            duration(duration)
        }
    }


}
package dev.meow.zeta.utils.animation

import dev.meow.zeta.utils.misc.TimeTracker
import kotlin.math.abs
import kotlin.reflect.KProperty

class SimpleAnimation private constructor() : Animation<Float> {

    companion object {

        fun empty(): SimpleAnimation = SimpleAnimation()

    }

    private var start: Float = 0f

    private var target: Float = 0f

    private var duration: Float = 200f

    private var easing: Easing = Easing.Linear

    private var _value: Float = 0f

    fun prepare(block: SimpleAnimation.() -> Unit): SimpleAnimation {
        this.apply(block)
        reset()
        return this
    }

    private val tracker = TimeTracker()

    override fun animate(): Float {
        if (duration == 0f || finished()) {
            _value = target
            return _value
        }

        val process = (tracker.elapsed / duration).coerceIn(0f, 1f)
        val interpolate = start + (target - start) * easing.ease(process)

        _value = if (abs(interpolate - target) <= 0.0001f) target else interpolate

        return _value
    }

    override fun finished(): Boolean = tracker.elapsed >= duration

    override fun reset() {
        tracker.reset()
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): Float = _value

    fun from(value: Float) {
        this.start = value
    }

    fun to(value: Float) {
        this.target = value
    }

    fun duration(duration: Float) {
        this.duration = duration
    }

    fun easing(easing: Easing) {
        this.easing = easing
    }

}
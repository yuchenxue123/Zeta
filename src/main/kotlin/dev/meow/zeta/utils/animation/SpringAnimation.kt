package dev.meow.zeta.utils.animation

import dev.meow.zeta.utils.misc.TimeTracker
import kotlin.math.abs
import kotlin.reflect.KProperty

class SpringAnimation(
    initialValue: Float,
    private val spring: Spring = Spring.Default
) : Animation<Float> {

    private var _value: Float = initialValue

    private var velocity: Float = 0f

    private val tracker = TimeTracker()

    private var target: Float = initialValue

    override fun animate(): Float {
        var dt = tracker.elapsed / 1000f
        tracker.reset()

        if (dt > 0.1f) dt = 0.1f

        val displacement = _value - target
        val force = -spring.stiffness * displacement - spring.damping * velocity

        velocity += force * dt
        _value += velocity * dt

        if (abs(_value - target) < 0.01f && abs(velocity) < 0.1f) {
            _value = target
            velocity = 0f
        }

        return _value
    }

    fun to(value: Float) {
        this.target = value
    }

    override fun finished() = _value == target

    override fun reset() {
        tracker.reset()
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): Float = _value

}
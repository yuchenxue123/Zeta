package dev.meow.zeta.utils.animation

import kotlin.reflect.KProperty

interface Animation<T> {

    fun animate(): T

    fun finished(): Boolean

    fun reset()

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T

}
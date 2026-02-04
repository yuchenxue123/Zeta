package dev.meow.zeta.utils.holder

import kotlin.reflect.KProperty

internal fun <T : Any> writeOnce(initialValue: T) = WriteOnce(initialValue)

class WriteOnce<T : Any>(
    private var value: T?
) {

    private var written = false

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value ?: throw NullPointerException("空指针哈哈")
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (written) {
            throw IllegalStateException("这个属性是只写一次的哈哈")
        }

        this.value = value
        written = true
    }
}
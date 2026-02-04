package dev.meow.zeta.event

abstract class CancellableEvent : Event() {

    private var cancelled: Boolean = false

    fun cancelEvent() {
        cancelled = true
    }

    fun isCancelled() = cancelled

}
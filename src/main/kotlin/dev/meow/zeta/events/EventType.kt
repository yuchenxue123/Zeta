package dev.meow.zeta.events

import dev.meow.zeta.core.Mode

enum class EventType(override val showName: String) : Mode {
    PRE("Pre"),
    POST("Post")
}
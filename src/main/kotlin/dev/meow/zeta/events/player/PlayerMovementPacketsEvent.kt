package dev.meow.zeta.events.player

import dev.meow.zeta.event.CancellableEvent
import dev.meow.zeta.events.EventType

class PlayerMovementPacketsEvent(
    val type: EventType,
    var x: Double,
    var y: Double,
    var z: Double,
    @get:JvmName("onGround")
    var ground: Boolean
) : CancellableEvent()
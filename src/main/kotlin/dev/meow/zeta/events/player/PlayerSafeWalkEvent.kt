package dev.meow.zeta.events.player

import dev.meow.zeta.event.Event

class PlayerSafeWalkEvent(
    var isSafeWalk: Boolean = false
) : Event()
package dev.meow.zeta.events.game

import com.mojang.blaze3d.platform.InputConstants
import dev.meow.zeta.event.Event

class KeyboardEvent(
    val key: InputConstants.Key,
    val keycode: Int,
    val scancode: Int,
    val action: Int
) : Event()
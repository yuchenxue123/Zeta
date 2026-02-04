package dev.meow.zeta.events.client

import dev.meow.zeta.event.Event
import dev.meow.zeta.features.module.ClientModule

class ModuleToggleEvent(
    val module: ClientModule,
    val state: Boolean
) : Event()
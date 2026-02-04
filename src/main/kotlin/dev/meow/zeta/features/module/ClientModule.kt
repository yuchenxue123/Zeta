package dev.meow.zeta.features.module

import dev.meow.zeta.config.Configurable
import dev.meow.zeta.core.Toggleable
import dev.meow.zeta.event.EventListener
import dev.meow.zeta.event.EventManager
import dev.meow.zeta.event.removeEventListenerScope
import dev.meow.zeta.events.client.ModuleToggleEvent
import dev.meow.zeta.utils.client.ingame

open class ClientModule(
    name: String,
    val category: Category,
    key: Int = -1,
    val locked: Boolean = false,
    defaultState: Boolean = false
) : Configurable(name), EventListener, Toggleable, MinecraftShortcut {

    val bind by bind("Bind", key)

    var state: Boolean = defaultState
        set(newState) {
            if (newState == field) return

            field = newState

            this.onToggled(newState)
        }

    override fun onToggled(state: Boolean): Boolean {
        if (!ingame) {
            return state
        }

        if (!state) {
            runCatching {
                removeEventListenerScope()
            }.onFailure {
                error("失败取消: $it")
            }
        }

        val state = super.onToggled(state)
        inner.filterIsInstance<Toggleable>().forEach { it.onToggled(state) }

        // Call module toggle event
        EventManager.callEvent(ModuleToggleEvent(this, state))
        return state
    }

    fun toggle() {
        state = !state
    }

}
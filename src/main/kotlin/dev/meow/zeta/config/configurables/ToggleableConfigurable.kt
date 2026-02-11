package dev.meow.zeta.config.configurables

import dev.meow.zeta.Zeta
import dev.meow.zeta.config.Configurable
import dev.meow.zeta.config.Setting
import dev.meow.zeta.config.settings.BooleanSetting
import dev.meow.zeta.core.Toggleable
import dev.meow.zeta.event.EventListener
import dev.meow.zeta.event.removeEventListenerScope

class ToggleableConfigurable(
    name: String,
    enabled: Boolean,
    private val parent: EventListener? = null,
    displayable: () -> Boolean = { true },
) : Configurable(name), EventListener, Toggleable {

    private val enabledSetting = BooleanSetting(name, enabled, displayable)
        .onValueChange { old, new -> onToggled(new) }

    val enabled by setting(enabledSetting)

    override fun <T : Setting<*>> setting(setting: T): T {
        if (setting != enabledSetting) {
            setting.displayable { enabled }
        }
        return super.setting(setting)
    }

    override fun onToggled(state: Boolean): Boolean {
        if (!state) {
            runCatching {
                removeEventListenerScope()
            }.onFailure {
                Zeta.logger.error("Failed cancel sequences: ${it.message}", it)
            }
        }

        val state = super.onToggled(state)
        inner.filterIsInstance<Toggleable>().forEach { it.onToggled(state) }
        return state
    }

    override fun parent(): EventListener? = parent

    override val running: Boolean
        get() = super.running && enabled

}
package dev.meow.zeta.config.settings.bind

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import dev.meow.zeta.config.Setting
import dev.meow.zeta.utils.input.Bind
import dev.meow.zeta.utils.input.InputUtils

class BindSetting(
    name: String,
    value: Bind,
    displayable: () -> Boolean = { true },
) : Setting<Bind>(name, value, displayable) {

    constructor(
        name: String,
        value: Int,
        displayable: () -> Boolean = { true },
    ) : this(name, Bind(value), displayable)

    override fun toJson(): JsonElement = JsonPrimitive(inner.keyName)

    override fun fromJson(json: JsonElement) {
        inner = Bind(InputUtils.getKey(json.asJsonPrimitive.asString))
    }
}
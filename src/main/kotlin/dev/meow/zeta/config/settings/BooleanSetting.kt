package dev.meow.zeta.config.settings

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import dev.meow.zeta.config.Setting

class BooleanSetting(
    name: String,
    value: Boolean,
    displayable: () -> Boolean = { true },
) : Setting<Boolean>(
    name, value, displayable
) {

    fun toggle() {
        set(!get())
    }

    override fun toJson(): JsonElement = JsonPrimitive(inner)

    override fun fromJson(json: JsonElement) {
        inner = json.asJsonPrimitive.asBoolean
    }

}
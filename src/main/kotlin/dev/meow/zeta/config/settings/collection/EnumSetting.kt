package dev.meow.zeta.config.settings.collection

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import dev.meow.zeta.config.Setting
import dev.meow.zeta.core.Mode

class EnumSetting<E>(
    name: String,
    value: E,
    displayable: () -> Boolean = { true },
) : Setting<E>(
    name, value, displayable
) where E : Enum<E>, E : Mode {
    val enums: Array<E>
        get() = inner.declaringJavaClass.enumConstants

    fun asString() = inner.showName

    fun fromString(value: String) {
        val newValue = enums.find { it.showName == value } ?: enums[0]
        this.set(newValue)
    }

    override fun toJson(): JsonElement = JsonObject().apply {
        add(
            "modes",
            JsonArray().apply {
                enums.forEach { enum -> add(enum.showName) }
            }
        )
        addProperty("value", inner.showName)
    }

    override fun fromJson(json: JsonElement) {
        val value = json.asJsonObject.get("value").asString
        fromString(value)
    }

}
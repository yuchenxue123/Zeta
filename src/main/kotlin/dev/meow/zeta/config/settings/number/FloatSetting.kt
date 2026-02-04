package dev.meow.zeta.config.settings.number

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import dev.meow.zeta.config.settings.NumberSetting
import dev.meow.zeta.utils.extension.step

class FloatSetting(
    name: String,
    value: Float,
    range: ClosedFloatingPointRange<Float>,
    step: Float,
    displayable: () -> Boolean = { true },
) : NumberSetting<Float>(name, value, range, step, displayable) {

    override fun setPercent(value: Float) {
        val percent = value.coerceIn(0f, 1f)
        val newValue = (min + (max - min) * percent).step(step)
        this.set(newValue)
    }

    override fun toJson(): JsonElement = JsonPrimitive(inner)

    override fun fromJson(json: JsonElement) {
        inner = json.asJsonPrimitive.asFloat
    }


}
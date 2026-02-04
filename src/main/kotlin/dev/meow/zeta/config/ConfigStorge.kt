package dev.meow.zeta.config

import com.google.gson.JsonElement
import com.google.gson.JsonObject

class ConfigStorge(
    private val configs: Array<Config>
) : JsonSerializable {

    override fun toJson(): JsonElement = JsonObject().apply {
        configs.forEach { config ->
            add(config.configName, config.toJson())
        }
    }

    override fun fromJson(json: JsonElement) {
        json.asJsonObject.entrySet().forEach { (name, value) ->
            configs.find { it.configName.equals(name, true) }?.fromJson(value)
        }
    }
}
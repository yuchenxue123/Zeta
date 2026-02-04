package dev.meow.zeta.config

import com.google.gson.JsonElement

interface JsonSerializable {

    fun toJson(): JsonElement

    fun fromJson(json: JsonElement)

}
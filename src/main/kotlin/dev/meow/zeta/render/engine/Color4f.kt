package dev.meow.zeta.render.engine

import org.joml.Vector4f
import java.awt.Color

data class Color4f(
    val red: Float,
    val green: Float,
    val blue: Float,
    val alpha: Float
) : Vector4f(red, green, blue, alpha) {

    companion object {

        val NONE = Color4f(0f, 0f, 0f, 0f)

    }

    fun toColor(): Color = Color(red, green, blue, alpha)

}
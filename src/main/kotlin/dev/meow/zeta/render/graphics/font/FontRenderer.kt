package dev.meow.zeta.render.graphics.font

import net.minecraft.network.chat.Component
import java.awt.Color

interface FontRenderer {

    fun width(text: Component): Float

    fun width(text: String): Float

    fun height(text: Component): Float

    fun height(text: String): Float

    fun drawText(text: Component, x: Float, y: Float, color: Color): Float

    fun drawText(text: String, x: Float, y: Float, color: Color): Float

    companion object {

        fun empty(): FontRenderer = EmptyImplement()

    }

    private class EmptyImplement : FontRenderer {

        override fun width(text: Component): Float = 0f

        override fun width(text: String): Float = 0f

        override fun height(text: Component): Float = 0f

        override fun height(text: String): Float = 0f

        override fun drawText(
            text: Component,
            x: Float,
            y: Float,
            color: Color
        ): Float = 0f

        override fun drawText(
            text: String,
            x: Float,
            y: Float,
            color: Color
        ): Float = 0f

    }

}
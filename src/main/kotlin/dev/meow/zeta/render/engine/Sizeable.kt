package dev.meow.zeta.render.engine

interface Sizeable {

    fun width(): Float

    fun height(): Float

    fun setSize(width: Float = width(), height: Float = height())

}
package dev.meow.zeta.render.engine

interface Positionable {

    fun x(): Float

    fun y(): Float

    fun setPosition(x: Float = x(), y: Float = y())

}
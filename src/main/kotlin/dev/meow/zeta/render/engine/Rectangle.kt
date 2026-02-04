package dev.meow.zeta.render.engine

class Rectangle(
    position: Positionable,
    size: Sizeable
) : Rect {

    constructor(
        x: Float,
        y: Float,
        width: Float,
        height: Float
    ) : this(Position(x, y), Size(width, height))

    companion object {

        fun empty(): Rectangle = Rectangle(0f, 0f, 0f, 0f)

        fun of(x: Float, y: Float, width: Float, height: Float): Rectangle = Rectangle(x, y, width, height)

    }

    private val position: Positionable = position

    private val size: Sizeable = size

    override fun x(): Float = position.x()

    override fun y(): Float = position.y()

    override fun width(): Float = size.width()

    override fun height(): Float = size.height()

    override fun setPosition(x: Float, y: Float) {
        position.setPosition(x, y)
    }

    override fun setSize(width: Float, height: Float) {
        size.setSize(width, height)
    }

}
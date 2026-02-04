package dev.meow.zeta.render.engine

class Position(
    private var x: Float,
    private var y: Float
) : Positionable {

    companion object {

        fun empty(): Position = Position(0f, 0f)

        fun of(x: Float, y: Float): Position = Position(x, y)

    }

    override fun x(): Float = x

    override fun y(): Float = y

    override fun setPosition(x: Float, y: Float) {
        this.x = x
        this.y = y
    }
}
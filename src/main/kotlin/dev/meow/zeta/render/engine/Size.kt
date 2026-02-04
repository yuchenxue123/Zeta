package dev.meow.zeta.render.engine

class Size(
    private var width: Float,
    private var height: Float
) : Sizeable {

    override fun width(): Float = width

    override fun height(): Float = height

    override fun setSize(width: Float, height: Float) {
        this.width = width
        this.height = height
    }

}
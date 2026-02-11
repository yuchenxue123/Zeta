package dev.meow.zeta.render.graphics

enum class PaintMode {
    FILL,
    STROKE

    ;

    companion object {

        fun fromBoolean(fill: Boolean) = when (fill) {
            true -> FILL
            else -> STROKE
        }

    }
}
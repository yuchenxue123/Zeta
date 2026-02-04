package dev.meow.zeta.utils.misc

import org.joml.Matrix3x2f
import org.joml.Vector2f
import org.lwjgl.nanovg.NVGColor

object Pools {

    val Vec2f = ObjectPool(::Vector2f)

    val Mat3x2f = ObjectPool(::Matrix3x2f)

    val NanoColor = ObjectPool(
        create = NVGColor::malloc,
        close = NVGColor::free
    )

}
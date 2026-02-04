package dev.meow.zeta.render.nano

import dev.meow.zeta.utils.extension.toColor4f
import dev.meow.zeta.utils.misc.Pools
import org.lwjgl.nanovg.NVGColor
import java.awt.Color

inline fun <R> withColor(color: Color, action: (color: NVGColor) -> R): R {
    val nc = Pools.NanoColor.acquire()
    val (red, green, blue, alpha) = color.toColor4f()
    nc.r(red).g(green).b(blue).a(alpha)
    return try {
        action.invoke(nc)
    } finally {
        Pools.NanoColor.release(nc)
    }
}
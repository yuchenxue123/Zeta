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

inline fun <R> withColor(color: Color, color2: Color, action: (color: NVGColor, color2: NVGColor) -> R): R {
    val nc = Pools.NanoColor.acquire()
    val nc2 = Pools.NanoColor.acquire()

    val (red, green, blue, alpha) = color.toColor4f()
    nc.r(red).g(green).b(blue).a(alpha)

    val (red2, green2, blue2, alpha2) = color2.toColor4f()
    nc2.r(red2).g(green2).b(blue2).a(alpha2)

    return try {
        action.invoke(nc, nc2)
    } finally {
        Pools.NanoColor.release(nc)
        Pools.NanoColor.release(nc2)
    }

}
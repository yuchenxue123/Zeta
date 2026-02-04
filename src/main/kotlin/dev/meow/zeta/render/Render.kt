package dev.meow.zeta.render

import dev.meow.zeta.render.state.RectangleRenderState
import dev.meow.zeta.render.state.TriangleRenderState
import dev.meow.zeta.utils.extension.ceilToInt
import dev.meow.zeta.utils.extension.floorToInt
import dev.meow.zeta.utils.misc.Pools
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.navigation.ScreenRectangle
import net.minecraft.client.renderer.RenderPipelines
import org.joml.Matrix3x2f
import java.awt.Color

@Suppress("UnnecessaryVariable")
fun GuiGraphics.createBounds(x: Float, y: Float, width: Float, height: Float): ScreenRectangle {
    val left = x
    val top = y
    val right = x + width
    val bottom = y + height

    val pose = this.copyPose()

    val vector2f = pose.transformPosition(left, top, Pools.Vec2f.acquire())
    val vector2f2 = pose.transformPosition(right, top, Pools.Vec2f.acquire())
    val vector2f3 = pose.transformPosition(left, bottom, Pools.Vec2f.acquire())
    val vector2f4 = pose.transformPosition(right, bottom, Pools.Vec2f.acquire())


    val l = minOf(vector2f.x, vector2f3.x, vector2f2.x, vector2f4.x).floorToInt()
    val t = minOf(vector2f.y, vector2f3.y, vector2f2.y, vector2f4.y).floorToInt()
    val r = maxOf(vector2f.x, vector2f3.x, vector2f2.x, vector2f4.x).ceilToInt()
    val b = maxOf(vector2f.y, vector2f3.y, vector2f2.y, vector2f4.y).ceilToInt()

    Pools.Vec2f.release(vector2f)
    Pools.Vec2f.release(vector2f2)
    Pools.Vec2f.release(vector2f3)
    Pools.Vec2f.release(vector2f4)

    val rectangle = ScreenRectangle(l, t, r - l, b - t)

    return this.scissorStack.peek()?.intersection(rectangle) ?: rectangle
}

fun GuiGraphics.copyPose(): Matrix3x2f = Matrix3x2f().set(this.pose())

fun GuiGraphics.drawVerticalGradientRectangle(
    x: Float, y: Float, width: Float, height: Float,
    tc: Color, bc: Color
) {
    val bounds = createBounds(x, y, width, height)
    this.guiRenderState.submitGuiElement(
        RectangleRenderState(
            pipeline = RenderPipelines.GUI,
            x = x, y = y, width = width, height = height,
            tlc = tc, trc = tc,
            blc = bc, brc = bc,
            pose = this.copyPose(),
            scissorArea = this.scissorStack.peek(),
            bounds = bounds
        )
    )
}

fun GuiGraphics.drawHorizonalGradientRectangle(
    x: Float, y: Float, width: Float, height: Float,
    lc: Color, rc: Color
) {
    val bounds = createBounds(x, y, width, height)
    this.guiRenderState.submitGuiElement(
        RectangleRenderState(
            pipeline = RenderPipelines.GUI,
            x = x, y = y, width = width, height = height,
            tlc = lc, trc = rc,
            blc = lc, brc = rc,
            pose = this.copyPose(),
            scissorArea = this.scissorStack.peek(),
            bounds = bounds
        )
    )
}

fun GuiGraphics.drawGradientRectangle(
    x: Float, y: Float, width: Float, height: Float,
    tlc: Color, blc: Color, brc: Color, trc: Color,
) {
    val bounds = createBounds(x, y, width, height)
    this.guiRenderState.submitGuiElement(
        RectangleRenderState(
            pipeline = RenderPipelines.GUI,
            x = x, y = y, width = width, height = height,
            tlc = tlc, trc = trc,
            blc = blc, brc = brc,
            pose = this.copyPose(),
            scissorArea = this.scissorStack.peek(),
            bounds = bounds
        )
    )
}

fun GuiGraphics.drawTriangle(
    x1: Float, y1: Float,
    x2: Float, y2: Float,
    x3: Float, y3: Float,
    color: Color,
) {
    val minX = minOf(x1, x2, x3)
    val minY = minOf(y1, y2, y3)
    val maxX = maxOf(x1, x2, x3)
    val maxY = maxOf(y1, y2, y3)

    val bounds = createBounds(minX, minY, maxX - minX, maxY - minY)
    this.guiRenderState.submitGuiElement(
        TriangleRenderState(
            pipeline = Pipelines.TRIANGLE_PIPELINE,
            x1 = x1, y1 = y1,
            x2 = x2, y2 = y2,
            x3 = x3, y3 = y3,
            color = color,
            pose = this.copyPose(),
            scissorArea = this.scissorStack.peek(),
            bounds = bounds
        )
    )
}
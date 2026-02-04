package dev.meow.zeta.render.state

import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.gui.navigation.ScreenRectangle
import net.minecraft.client.gui.render.TextureSetup
import net.minecraft.client.gui.render.state.GuiElementRenderState
import org.joml.Matrix3x2f
import java.awt.Color

class TriangleRenderState(
    val pipeline: RenderPipeline,
    // Vertex
    val x1: Float,
    val y1: Float,
    val x2: Float,
    val y2: Float,
    val x3: Float,
    val y3: Float,
    // Color
    val c1: Int,
    val c2: Int,
    val c3: Int,
    val pose: Matrix3x2f,
    val scissorArea: ScreenRectangle?,
    val bounds: ScreenRectangle?
) : GuiElementRenderState {

    constructor(
        pipeline: RenderPipeline,
        x1: Float,
        y1: Float,
        x2: Float,
        y2: Float,
        x3: Float,
        y3: Float,
        color: Color,
        pose: Matrix3x2f,
        scissorArea: ScreenRectangle?,
        bounds: ScreenRectangle?
    ) : this(pipeline, x1, y1, x2, y2, x3, y3, color, color, color, pose, scissorArea, bounds)

    constructor(
        pipeline: RenderPipeline,
        x1: Float,
        y1: Float,
        x2: Float,
        y2: Float,
        x3: Float,
        y3: Float,
        c1: Color,
        c2: Color,
        c3: Color,
        pose: Matrix3x2f,
        scissorArea: ScreenRectangle?,
        bounds: ScreenRectangle?
    ) : this(pipeline, x1, y1, x2, y2, x3, y3, c1.rgb, c2.rgb, c3.rgb, pose, scissorArea, bounds)

    override fun buildVertices(consumer: VertexConsumer) {
        consumer.addVertexWith2DPose(pose, x1, y1).setColor(c1)
        consumer.addVertexWith2DPose(pose, x2, y2).setColor(c2)
        consumer.addVertexWith2DPose(pose, x3, y3).setColor(c3)
    }

    override fun pipeline(): RenderPipeline = pipeline

    override fun textureSetup(): TextureSetup = TextureSetup.noTexture()

    override fun scissorArea(): ScreenRectangle? = scissorArea

    override fun bounds(): ScreenRectangle? = bounds

}
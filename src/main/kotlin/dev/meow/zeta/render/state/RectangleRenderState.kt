package dev.meow.zeta.render.state

import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.gui.navigation.ScreenRectangle
import net.minecraft.client.gui.render.TextureSetup
import net.minecraft.client.gui.render.state.GuiElementRenderState
import org.joml.Matrix3x2f
import java.awt.Color

/**
 * See [net.minecraft.client.gui.render.state.ColoredRectangleRenderState]
 */
class RectangleRenderState(
    val pipeline: RenderPipeline,
    // Rectangle
    val x: Float,
    val y: Float,
    val width: Float,
    val height: Float,
    // Color
    val tlc: Int,
    val blc: Int,
    val brc: Int,
    val trc: Int,
    val pose: Matrix3x2f,
    val scissorArea: ScreenRectangle?,
    val bounds: ScreenRectangle?
) : GuiElementRenderState {

    constructor(
        pipeline: RenderPipeline,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        tlc: Color,
        blc: Color,
        brc: Color,
        trc: Color,
        pose: Matrix3x2f,
        scissorArea: ScreenRectangle?,
        bounds: ScreenRectangle?
    ) : this(pipeline, x, y, width, height, tlc.rgb, blc.rgb, brc.rgb, trc.rgb, pose, scissorArea, bounds)

    override fun buildVertices(consumer: VertexConsumer) {
        consumer.addVertexWith2DPose(pose, x, y).setColor(tlc)
        consumer.addVertexWith2DPose(pose, x, y + height).setColor(blc)
        consumer.addVertexWith2DPose(pose, x + width, y + height).setColor(brc)
        consumer.addVertexWith2DPose(pose, x + width, y).setColor(trc)
    }

    override fun pipeline(): RenderPipeline = pipeline

    override fun textureSetup(): TextureSetup = TextureSetup.noTexture()

    override fun scissorArea(): ScreenRectangle? = scissorArea

    override fun bounds(): ScreenRectangle? = bounds

}
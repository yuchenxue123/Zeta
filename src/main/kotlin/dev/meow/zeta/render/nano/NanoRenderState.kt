package dev.meow.zeta.render.nano

import dev.meow.zeta.render.graphics.RenderCallback
import net.minecraft.client.gui.navigation.ScreenRectangle
import net.minecraft.client.gui.render.state.pip.PictureInPictureRenderState

class NanoRenderState(
    val x: Int,
    val y: Int,
    val width: Int,
    val height: Int,
    val scissorArea: ScreenRectangle?,
    val bounds: ScreenRectangle?,
    val drawing: RenderCallback
) : PictureInPictureRenderState {

    override fun x0(): Int = x

    override fun y0(): Int = y

    override fun x1(): Int = x + width

    override fun y1(): Int = y + height

    override fun scale(): Float = 1f

    override fun scissorArea(): ScreenRectangle? = scissorArea

    override fun bounds(): ScreenRectangle? = bounds

}
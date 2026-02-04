package dev.meow.zeta.render.nano

import com.mojang.blaze3d.opengl.GlConst
import com.mojang.blaze3d.opengl.GlDevice
import com.mojang.blaze3d.opengl.GlStateManager
import com.mojang.blaze3d.opengl.GlTexture
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import dev.meow.zeta.render.createBounds
import dev.meow.zeta.render.graphics.RenderCallback
import net.fabricmc.fabric.api.client.rendering.v1.SpecialGuiElementRegistry
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.render.pip.PictureInPictureRenderer
import net.minecraft.client.renderer.MultiBufferSource
import org.lwjgl.opengl.GL33

class NanoRenderer(
    bufferSource: MultiBufferSource.BufferSource
) : PictureInPictureRenderer<NanoRenderState>(bufferSource) {

    constructor(ctx: SpecialGuiElementRegistry.Context) : this(ctx.vertexConsumers())

    companion object {
        fun draw(
            context: GuiGraphics,
            x: Int,
            y: Int,
            width: Int,
            height: Int,
            drawing: RenderCallback
        ) {
            val scissor = context.scissorStack.peek()
            val bounds = context.createBounds(
                x.toFloat(), y.toFloat(),
                (x + width).toFloat(), (y + height).toFloat()
            )

            val state = NanoRenderState(
                x, y, width, height,
                scissor, bounds,
                drawing
            )

            context.guiRenderState.submitPicturesInPictureState(state)
        }
    }

    override fun getRenderStateClass(): Class<NanoRenderState> = NanoRenderState::class.java

    override fun renderToTexture(
        state: NanoRenderState,
        poseStack: PoseStack
    ) {
        val colorTex = RenderSystem.outputColorTextureOverride
        val depthTex = RenderSystem.outputDepthTextureOverride

        if (colorTex == null || depthTex == null) {
            throw IllegalStateException()
        }

        val glDevice = RenderSystem.getDevice() as GlDevice
        val glColorTex = colorTex.texture() as GlTexture
        val glDepthTex = depthTex.texture() as GlTexture

        val fbo = glColorTex.getFbo(glDevice.directStateAccess(), glDepthTex)


        val width = colorTex.getWidth(0)
        val height = colorTex.getHeight(0)

        GlStateManager._glBindFramebuffer(GlConst.GL_FRAMEBUFFER, fbo)
        GlStateManager._viewport(0, 0, width, height)

        GL33.glBindSampler(0, 0)

        NanoRenderContext.beginFrame(width.toFloat(), height.toFloat())
        state.drawing.render()
        NanoRenderContext.endFrame()

        GlStateManager._disableDepthTest()
        GlStateManager._disableCull()
        GlStateManager._enableBlend()
        GlStateManager._blendFuncSeparate(
            GlConst.GL_SRC_ALPHA,
            GlConst.GL_ONE_MINUS_SRC_ALPHA,
            GlConst.GL_ONE,
            GlConst.GL_ZERO
        )
    }

    override fun getTranslateY(i: Int, j: Int): Float {
        return i / 2f
    }

    override fun getTextureLabel(): String = "nano"

}
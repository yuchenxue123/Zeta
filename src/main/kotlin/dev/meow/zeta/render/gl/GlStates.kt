package dev.meow.zeta.render.gl

import org.lwjgl.opengl.*
import org.lwjgl.system.MemoryStack
import kotlin.properties.Delegates


object GlStates {

    private val _MaxTextureUnits by lazy {
        GL11.glGetInteger(GL20.GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS)
    }

    object AllState : GlState {
        override fun save() {
            BlendState.save()
            DepthState.save()
            CullState.save()
            StencilState.save()
            TextureState.save()
            ShaderProgramState.save()
            VertexArrayState.save()
            ColorMaskState.save()
            PixelStoreState.save()
            ScissorState.save()
        }

        override fun restore() {
            BlendState.restore()
            DepthState.restore()
            CullState.restore()
            StencilState.restore()
            TextureState.restore()
            ShaderProgramState.restore()
            VertexArrayState.restore()
            ColorMaskState.restore()
            PixelStoreState.restore()
            ScissorState.restore()
        }

    }

    object BlendState : GlState {

        private var enabled by Delegates.notNull<Boolean>()
        private var srcRgb by Delegates.notNull<Int>()
        private var dstRgb by Delegates.notNull<Int>()
        private var srcAlpha by Delegates.notNull<Int>()
        private var dstAlpha by Delegates.notNull<Int>()


        override fun save() {
            enabled = GL11.glIsEnabled(GL11.GL_BLEND)
            srcRgb = GL11.glGetInteger(GL14.GL_BLEND_SRC_RGB)
            dstRgb = GL11.glGetInteger(GL14.GL_BLEND_DST_RGB)
            srcAlpha = GL11.glGetInteger(GL14.GL_BLEND_SRC_ALPHA)
            dstAlpha = GL11.glGetInteger(GL14.GL_BLEND_DST_ALPHA)
        }

        override fun restore() {
            if (enabled) GL11.glEnable(GL11.GL_BLEND) else GL11.glDisable(GL11.GL_BLEND)
            GL14.glBlendFuncSeparate(srcRgb, dstRgb, srcAlpha, dstAlpha)
        }

    }

    object DepthState : GlState {

        private var enabled by Delegates.notNull<Boolean>()
        private var depthMask by Delegates.notNull<Boolean>()
        private var depthFunc by Delegates.notNull<Int>()

        override fun save() {
            enabled = GL11.glIsEnabled(GL11.GL_DEPTH_TEST)
            depthMask = GL11.glGetBoolean(GL11.GL_DEPTH_WRITEMASK)
            depthFunc = GL11.glGetInteger(GL11.GL_DEPTH_FUNC)
        }

        override fun restore() {
            if (enabled) GL11.glEnable(GL11.GL_DEPTH_TEST) else GL11.glDisable(GL11.GL_DEPTH_TEST)
            GL11.glDepthMask(depthMask)
            GL11.glDepthFunc(depthFunc)
        }
    }

    object CullState : GlState {

        private var enabled by Delegates.notNull<Boolean>()
        private var cullFace by Delegates.notNull<Int>()

        override fun save() {
            enabled = GL11.glIsEnabled(GL11.GL_CULL_FACE)
            cullFace = GL11.glGetInteger(GL11.GL_CULL_FACE_MODE)
        }

        override fun restore() {
            if (enabled) GL11.glEnable(GL11.GL_CULL_FACE) else GL11.glDisable(GL11.GL_CULL_FACE)
            GL11.glCullFace(cullFace)
        }
    }

    object StencilState : GlState {

        private var enabled by Delegates.notNull<Boolean>()
        private var stencilFunc by Delegates.notNull<Int>()
        private var stencilRef by Delegates.notNull<Int>()
        private var stencilMask by Delegates.notNull<Int>()
        private var stencilFail by Delegates.notNull<Int>()
        private var stencilPassDepthFail by Delegates.notNull<Int>()
        private var stencilPassDepthPass by Delegates.notNull<Int>()
        private var stencilWriteMask by Delegates.notNull<Int>()


        override fun save() {
            enabled = GL11.glIsEnabled(GL11.GL_STENCIL_TEST)
            stencilFunc = GL11.glGetInteger(GL11.GL_STENCIL_FUNC)
            stencilRef = GL11.glGetInteger(GL11.GL_STENCIL_REF)
            stencilMask = GL11.glGetInteger(GL11.GL_STENCIL_VALUE_MASK)
            stencilFail = GL11.glGetInteger(GL11.GL_STENCIL_FAIL)
            stencilPassDepthFail = GL11.glGetInteger(GL11.GL_STENCIL_PASS_DEPTH_FAIL)
            stencilPassDepthPass = GL11.glGetInteger(GL11.GL_STENCIL_PASS_DEPTH_PASS)
            stencilWriteMask = GL11.glGetInteger(GL11.GL_STENCIL_WRITEMASK)
        }

        override fun restore() {
            if (enabled) GL11.glEnable(GL11.GL_STENCIL_TEST) else GL11.glDisable(GL11.GL_STENCIL_TEST)
            GL11.glStencilFunc(stencilFunc, stencilRef, stencilMask)
            GL11.glStencilOp(stencilFail, stencilPassDepthFail, stencilPassDepthPass)
            GL11.glStencilMask(stencilWriteMask)
        }
    }

    object TextureState : GlState {

        private var texture by Delegates.notNull<Int>()
        private var textures by Delegates.notNull<IntArray>()
        private var samplers by Delegates.notNull<IntArray>()

        override fun save() {
            texture = GL11.glGetInteger(GL13.GL_ACTIVE_TEXTURE)
            textures = IntArray(_MaxTextureUnits)
            samplers = IntArray(_MaxTextureUnits)

            for (i in 0 until _MaxTextureUnits) {
                GL13.glActiveTexture(GL13.GL_TEXTURE0 + i)
                textures[i] = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D)
                samplers[i] = GL11.glGetInteger(GL33.GL_SAMPLER_BINDING)
            }

            GL13.glActiveTexture(texture)
        }

        override fun restore() {
            for (i in textures.indices) {
                GL13.glActiveTexture(GL13.GL_TEXTURE0 + i)
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, textures[i])
                GL33.glBindSampler(i, samplers[i])
            }
            GL13.glActiveTexture(texture)
        }
    }

    object ShaderProgramState : GlState {

        private var program by Delegates.notNull<Int>()

        override fun save() {
            program = GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM)
        }

        override fun restore() {
            GL20.glUseProgram(program)
        }
    }

    object VertexArrayState : GlState {

        private var vao by Delegates.notNull<Int>()

        override fun save() {
            vao = GL11.glGetInteger(GL30.GL_VERTEX_ARRAY_BINDING)
        }

        override fun restore() {
            GL30.glBindVertexArray(vao)
        }
    }

    object ColorMaskState : GlState {

        private var red by Delegates.notNull<Boolean>()
        private var green by Delegates.notNull<Boolean>()
        private var blue by Delegates.notNull<Boolean>()
        private var alpha by Delegates.notNull<Boolean>()

        override fun save() {
            MemoryStack.stackPush().use { stack ->
                val buffer = stack.malloc(4)
                GL11.glGetBooleanv(GL11.GL_COLOR_WRITEMASK, buffer)

                red = buffer[0].toInt() != 0
                green = buffer[1].toInt() != 0
                blue = buffer[2].toInt() != 0
                alpha = buffer[3].toInt() != 0
            }
        }

        override fun restore() {
            GL11.glColorMask(red, green, blue, alpha)
        }
    }

    object PixelStoreState : GlState {

        private var unpackAlignment by Delegates.notNull<Int>()
        private var unpackRowLength by Delegates.notNull<Int>()
        private var unpackSkipPixels by Delegates.notNull<Int>()
        private var unpackSkipRows by Delegates.notNull<Int>()
        private var unpackImageHeight by Delegates.notNull<Int>()
        private var unpackSkipImages by Delegates.notNull<Int>()
        private var unpackBufferBinding by Delegates.notNull<Int>()


        override fun save() {
            unpackAlignment = GL11.glGetInteger(GL11.GL_UNPACK_ALIGNMENT)
            unpackRowLength = GL11.glGetInteger(GL11.GL_UNPACK_ROW_LENGTH)
            unpackSkipPixels = GL11.glGetInteger(GL11.GL_UNPACK_SKIP_PIXELS)
            unpackSkipRows = GL11.glGetInteger(GL11.GL_UNPACK_SKIP_ROWS)
            unpackImageHeight = GL11.glGetInteger(GL12.GL_UNPACK_IMAGE_HEIGHT)
            unpackSkipImages = GL11.glGetInteger(GL12.GL_UNPACK_SKIP_IMAGES)

            unpackBufferBinding = GL11.glGetInteger(GL21.GL_PIXEL_UNPACK_BUFFER_BINDING)
        }

        override fun restore() {
            GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, unpackAlignment)
            GL11.glPixelStorei(GL11.GL_UNPACK_ROW_LENGTH, unpackRowLength)
            GL11.glPixelStorei(GL11.GL_UNPACK_SKIP_PIXELS, unpackSkipPixels)
            GL11.glPixelStorei(GL11.GL_UNPACK_SKIP_ROWS, unpackSkipRows)
            GL11.glPixelStorei(GL12.GL_UNPACK_IMAGE_HEIGHT, unpackImageHeight)
            GL11.glPixelStorei(GL12.GL_UNPACK_SKIP_IMAGES, unpackSkipImages)

            GL21.glBindBuffer(GL21.GL_PIXEL_UNPACK_BUFFER, unpackBufferBinding)
        }
    }

    object ScissorState : GlState {

        private var enabled by Delegates.notNull<Boolean>()
        private var box by  Delegates.notNull<IntArray>()

        override fun save() {
            enabled = GL11.glIsEnabled(GL11.GL_SCISSOR_TEST)
            MemoryStack.stackPush().use { stack ->
                val buffer = stack.mallocInt(4)
                GL11.glGetIntegerv(GL11.GL_SCISSOR_BOX, buffer)

                box = intArrayOf(buffer[0], buffer[1], buffer[2], buffer[3])
            }
        }

        override fun restore() {
            if (enabled) GL11.glEnable(GL11.GL_SCISSOR_TEST) else GL11.glDisable(GL11.GL_SCISSOR_TEST)
            GL11.glScissor(box[0], box[1], box[2], box[3])
        }
    }


    interface GlState {

        fun save()

        fun restore()

    }

}

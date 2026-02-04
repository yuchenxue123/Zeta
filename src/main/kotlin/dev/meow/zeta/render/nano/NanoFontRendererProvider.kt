package dev.meow.zeta.render.nano

import dev.meow.zeta.render.graphics.font.FontRendererProvider
import dev.meow.zeta.render.graphics.font.FontRenderer
import dev.meow.zeta.utils.resource.ResourceManager
import dev.meow.zeta.utils.resource.ResourceType
import org.lwjgl.nanovg.NanoVG
import org.lwjgl.system.MemoryUtil
import java.nio.ByteBuffer

object NanoFontRendererProvider : FontRendererProvider {

    private val fonts = mutableMapOf<String, Int>()

    override fun createFont(name: String, extension: String, size: Float): FontRenderer {
        val id = fonts.getOrPut(name) {
            val bytes = ResourceManager.getBytes("$name.$extension", ResourceType.FONT)
            val buffer = MemoryUtil.memAlloc(bytes.size)
                .put(bytes)
                .flip() as ByteBuffer

            val font = NanoVG.nvgCreateFontMem(NanoRenderContext.context, name, buffer, true)

            if (font == -1) {
                try {
                    throw RuntimeException("创建字体 $name 失败")
                } finally {
                }
            }

            font
        }

        return NanoFontRenderer(name, id, size)
    }
}
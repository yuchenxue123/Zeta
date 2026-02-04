package dev.meow.zeta.render

import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.shaders.UniformType
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.VertexFormat
import dev.meow.zeta.utils.client.identifier
import net.minecraft.client.renderer.RenderPipelines

object Pipelines {

    val TRIANGLE_PIPELINE: RenderPipeline = RenderPipeline.builder(RenderPipelines.GUI_SNIPPET)
        .withLocation(identifier("pipeline/gui/triangles"))
        .withVertexFormat(DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.TRIANGLES)
        .withCull(false)
        .build()

}
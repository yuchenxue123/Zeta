package dev.meow.zeta.render.graphics.font

interface FontRendererProvider {

    fun createFont(name: String, extension: String, size: Float): FontRenderer

}
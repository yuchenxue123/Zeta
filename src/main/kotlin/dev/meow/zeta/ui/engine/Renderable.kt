package dev.meow.zeta.ui.engine

import net.minecraft.client.gui.GuiGraphics

interface Renderable {

    fun render(context: GuiGraphics, mouseX: Double, mouseY: Double, deltaTime: Float) {}

}
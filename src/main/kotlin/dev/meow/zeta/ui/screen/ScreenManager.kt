package dev.meow.zeta.ui.screen

import dev.meow.zeta.render.nano.NanoRenderer
import dev.meow.zeta.utils.client.mc
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.input.KeyEvent
import net.minecraft.client.input.MouseButtonEvent
import net.minecraft.network.chat.Component
import net.minecraft.client.gui.screens.Screen as MinecraftScreen

object ScreenManager : MinecraftScreen(Component.literal("ScreenManager")) {

    private var currentScreen: Screen? = null

    fun displayScreen(screen: Screen?) {

        currentScreen?.onClose()

        currentScreen = screen

        if (screen == null) {
            mc.setScreen(null)
            return
        }

        screen.init()
        screen.onOpen()

        if (mc.screen != this) {
            mc.setScreen(this)
        }
    }

    override fun render(context: GuiGraphics, mouseX: Int, mouseY: Int, deltaTime: Float) {
        val screen = currentScreen ?: return

        if (screen.shouldRemove()) {
            displayScreen(null)
        }

        NanoRenderer.draw(context, 0, 0, context.guiWidth(), context.guiHeight()) {
            screen.render(
                context,
                mouseX * mc.window.guiScale.toDouble(),
                mouseY * mc.window.guiScale.toDouble(),
                deltaTime
            )
        }
    }

    override fun mouseClicked(event: MouseButtonEvent, bl: Boolean): Boolean {
        if (super.mouseClicked(event, bl)) {
            return true
        }
        return currentScreen?.mouseClicked(event.x(), event.y(), event.button()) ?: false
    }

    override fun mouseReleased(event: MouseButtonEvent): Boolean {
        if (super.mouseReleased(event)) {
            return true
        }

        return currentScreen?.mouseReleased(event.x(), event.y(), event.button()) ?: false
    }

    override fun mouseScrolled(mouseX: Double, mouseY: Double, horizontalAmount: Double, verticalAmount: Double): Boolean {
        if (super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount)) {
            return true
        }

        return currentScreen?.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount) ?: false
    }

    override fun keyPressed(event: KeyEvent): Boolean {
        if (currentScreen?.keyPressed(event.key(), event.scancode(), event.modifiers()) == true) {
            return true
        }

        return super.keyPressed(event)
    }

    override fun keyReleased(event: KeyEvent): Boolean {
        if (super.keyReleased(event)) {
            return true
        }

        return currentScreen?.keyReleased(event.key(), event.scancode(), event.modifiers()) ?: false
    }
}
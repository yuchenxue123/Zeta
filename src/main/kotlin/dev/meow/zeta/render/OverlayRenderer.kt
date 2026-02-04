package dev.meow.zeta.render

import dev.meow.zeta.event.EventManager
import dev.meow.zeta.events.render.OverlayRenderEvent
import dev.meow.zeta.render.nano.NanoRenderer
import net.minecraft.client.gui.GuiGraphics

object OverlayRenderer {

    fun render(context: GuiGraphics, deltaTime: Float) {
        NanoRenderer.draw(
            context, 0, 0, context.guiWidth(), context.guiHeight(),
        ) {
            EventManager.callEvent(OverlayRenderEvent(context, deltaTime))
        }
    }

}
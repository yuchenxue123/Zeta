package dev.meow.zeta.events.render

import dev.meow.zeta.event.Event
import net.minecraft.client.gui.GuiGraphics

class OverlayRenderEvent(
    context: GuiGraphics,
    deltaTime: Float,
) : Event()
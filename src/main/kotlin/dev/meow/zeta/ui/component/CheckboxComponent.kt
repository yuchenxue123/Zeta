package dev.meow.zeta.ui.component

import dev.meow.zeta.render.RenderUtils
import dev.meow.zeta.render.engine.Rect
import dev.meow.zeta.utils.animation.ColorAnimation
import dev.meow.zeta.utils.animation.Easing
import dev.meow.zeta.utils.animation.SimpleAnimation
import net.minecraft.client.gui.GuiGraphics
import java.awt.Color

class CheckboxComponent(
    rect: Rect,
    style: CheckboxStyle = CheckboxStyle.Default,
    private val getter: () -> Boolean,
    private val setter: (Boolean) -> Unit,
) : BoxComponent(rect) {

    private var state: Boolean = false

    private val padding = Padding(0f, 0f)

    private val knobRadius: Float
        get() = (height() - padding.y() * 2f) / 2f

    private val minOffset: Float = 0f

    private val maxOffset: Float
        get() = width() - padding.x() * 2 - knobRadius * 2

    private val _offset_animation = SimpleAnimation.empty()
        .prepare {
            to(if (state) maxOffset else minOffset)
            easing(Easing.EaseOutQuad)
        }

    private val offset by _offset_animation

    private val disableColor: Color = style.disable

    private val enableColor: Color = style.enable

    private val knobColor: Color = style.knob

    private val _color_animation = ColorAnimation(Easing.EaseOutQuad)
        .prepare {
            to(disableColor)
        }

    private val color by _color_animation

    init {
        radius(height() / 2f)
    }

    override fun render(context: GuiGraphics, mouseX: Double, mouseY: Double, deltaTime: Float) {
        super.render(context, mouseX, mouseY, deltaTime)

        val currentState = getter.invoke()

        if (currentState != state) {
            when (currentState) {
                true -> {
                    _offset_animation.prepare {
                        from(offset)
                        to(maxOffset)
                        easing(Easing.EaseOutQuad)
                    }
                    _color_animation.prepare {
                        from(color)
                        to(enableColor)
                    }
                }

                false -> {
                    _offset_animation.prepare {
                        from(offset)
                        to(minOffset)
                        easing(Easing.EaseInQuad)
                    }
                    _color_animation.prepare {
                        from(color)
                        to(disableColor)
                    }
                }
            }

            state = currentState
        }

        _offset_animation.animate()
        _color_animation.animate()

        color(color)

        val knobX = x() + padding.x() + knobRadius + offset
        val knobY = y() + padding.y() + knobRadius

        RenderUtils.drawCircle(
            knobX,
            knobY,
            knobRadius,
            knobColor
        )
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {

        if (isHovered(mouseX, mouseY) && button == 0) {
            setter.invoke(state.not())
        }

        return super.mouseClicked(mouseX, mouseY, button)
    }

    fun padding(x: Float = 0f, y: Float = 0f) = apply {
        padding.setPosition(x, y)
    }

    data class CheckboxStyle(
        val enable: Color,
        val disable: Color,
        val knob: Color
    ) {

        companion object {

            val Default = CheckboxStyle(
                enable = Color(0, 140, 255),
                disable = Color(30, 30, 30),
                knob = Color(255, 255, 255)
            )

        }

    }

}
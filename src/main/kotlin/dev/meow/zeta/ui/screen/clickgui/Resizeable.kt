package dev.meow.zeta.ui.screen.clickgui

import dev.meow.zeta.render.engine.Sizeable

interface Resizeable {

    // 大小有变化的组件
    fun size(): Sizeable

}
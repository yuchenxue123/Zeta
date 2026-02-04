package dev.meow.zeta.ui.component

import dev.meow.zeta.ui.engine.Element

abstract class BaseComponent : Element {

    companion object : BaseComponent()

}

/**
 * 用于设置参数
 */
fun <T : BaseComponent> T.prepare(block: T.() -> Unit): T {
    block.invoke(this)
    return this
}
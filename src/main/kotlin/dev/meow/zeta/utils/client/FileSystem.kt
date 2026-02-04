package dev.meow.zeta.utils.client

import dev.meow.zeta.Zeta
import java.io.File

object FileSystem {

    val ROOT_FOLD = File(
        mc.gameDirectory, Zeta.NAME
    ).apply {
        if (!exists()) {
            mkdir()
        }
    }

    val CONFIG_FOLD = File(
        ROOT_FOLD, "configs"
    ).apply {
        if (!exists()) {
            mkdir()
        }
    }

}
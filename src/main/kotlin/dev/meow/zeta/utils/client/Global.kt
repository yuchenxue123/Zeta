package dev.meow.zeta.utils.client

import dev.meow.zeta.Zeta
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.multiplayer.ClientPacketListener
import net.minecraft.client.player.LocalPlayer
import net.minecraft.resources.Identifier

val mc: Minecraft
    inline get() = Minecraft.getInstance()

val player: LocalPlayer
    inline get() = mc.player!!

val world: ClientLevel
    inline get() = mc.level!!

val network: ClientPacketListener
    inline get() = mc.connection!!

val ingame: Boolean
    get() = mc.player != null && mc.level != null

fun identifier(path : String) : Identifier {
    return Identifier.fromNamespaceAndPath(Zeta.NAMESPACE, path)
}

package dev.meow.zeta.events.network

import dev.meow.zeta.event.CancellableEvent
import dev.meow.zeta.events.PacketType
import net.minecraft.network.protocol.Packet

class PacketEvent(
    val type: PacketType,
    val packet: Packet<*>
) : CancellableEvent()
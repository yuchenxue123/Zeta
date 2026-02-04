package dev.meow.zeta.events.player

import dev.meow.zeta.event.Event
import net.minecraft.world.entity.MoverType
import net.minecraft.world.phys.Vec3

class PlayerMoveEvent(
    val type: MoverType,
    val movement: Vec3
) : Event()
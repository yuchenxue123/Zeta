package dev.meow.zeta.events.player

import dev.meow.zeta.event.Event
import net.minecraft.world.phys.Vec3

class PlayerVelocityEvent(
    val movementInput: Vec3,
    val speed: Float,
    val yaw: Float,
    var velocity: Vec3
) : Event()
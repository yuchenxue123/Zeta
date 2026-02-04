package dev.meow.zeta.event

import dev.meow.zeta.events.client.ModuleToggleEvent
import dev.meow.zeta.events.game.GameTickEvent
import dev.meow.zeta.events.game.KeyboardEvent
import dev.meow.zeta.events.render.OverlayRenderEvent
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.reflect.KClass

val ALL_EVENT_CLASSES: Array<KClass<out Event>> = arrayOf(
    GameTickEvent::class,
    KeyboardEvent::class,
    OverlayRenderEvent::class,

    ModuleToggleEvent::class,
)

object EventManager {

    private val registry: Map<Class<out Event>, CopyOnWriteArrayList<EventHook<in Event>>> =
        ALL_EVENT_CLASSES.associate { it.java to CopyOnWriteArrayList() }

    init {
        CoroutineTicker
    }

    /**
     * Register EventHook
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : Event> registerEventHook(eventClass: Class<out Event>, eventHook: EventHook<T>): EventHook<T> {
        val handlers = registry[eventClass] ?: error("The event '${eventClass.name}' is not registered in EventManager.kt::ALL_EVENT_CLASSES.")

        val hook = eventHook as EventHook<in Event>

        if (!handlers.contains(hook)) {
            handlers.add(hook)

            handlers.sortedByDescending { it.priority }
        }

        return eventHook
    }

    /**
     * Unregisters a handler.
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : Event> unregisterEventHook(eventClass: Class<out Event>, eventHook: EventHook<T>) {
        registry[eventClass]?.remove(eventHook as EventHook<in Event>)
    }


    /**
     * Unregister listener
     */
    fun unregisterEventHandler(eventHandler: EventListener) {
        registry.values.forEach {
            it.removeIf { hook -> hook.listener == eventHandler }
        }
    }

    /**
     * Call event to listeners
     */
    fun  <T: Event> callEvent(event: T): T {
        val targets = registry[event.javaClass] ?: return event

        for (eventHook in targets) {

            if (!eventHook.listener.running) continue

            runCatching {
                eventHook.handler(event)
            }.onFailure {
                println("Exception while executing handler. -> <${event::class.java.name}> -> ${it.cause} -> ${it.stackTraceToString()}")
            }
        }

        return event
    }
}

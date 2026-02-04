package dev.meow.zeta.utils.misc

import java.util.concurrent.ConcurrentLinkedDeque
import java.util.concurrent.atomic.AtomicInteger

class ObjectPool<T : Any>(
    private val create: () -> T,
    private val reset: (T) -> Unit = {},
    private val close: (T) -> Unit = {},
    private val capacity: Int = 16
) {

    private val pool = ConcurrentLinkedDeque<T>()

    private val count = AtomicInteger(0)

    fun acquire(): T {
        val obj = pool.poll()

        if (obj != null) {
            count.decrementAndGet()
            return obj
        }

        return create.invoke()
    }

    fun release(obj: T) {
        reset.invoke(obj)

        if (count.get() < capacity) {
            pool.offer(obj)
            count.incrementAndGet()
            return
        }

        close.invoke(obj)
    }

    fun clear() {
        pool.forEach(close)
        pool.clear()
    }

    fun <R> use(block: (T) -> R): R {
        val item = this.acquire()
        try {
            return block.invoke(item)
        } finally {
            release(item)
        }
    }
}
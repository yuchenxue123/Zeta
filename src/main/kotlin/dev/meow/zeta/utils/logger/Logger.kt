package dev.meow.zeta.utils.logger

import java.io.PrintStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Logger(
    private val name: String
) {
    constructor(clazz: Class<*>) : this(clazz.simpleName)

    constructor(obj: Any) : this(obj::class.java)

    companion object {

        private val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")

        private const val ANSI_RESET = "\u001B[0m"
        private const val ANSI_BLACK = "\u001B[30m"
        private const val ANSI_GREY = "\u001B[90m"
        private const val ANSI_RED = "\u001B[31m"
        private const val ANSI_GREEN = "\u001B[32m"
        private const val ANSI_YELLOW = "\u001B[33m"
        private const val ANSI_BLUE = "\u001B[34m"
        private const val ANSI_PURPLE = "\u001B[35m"
        private const val ANSI_CYAN = "\u001B[36m"
        private const val ANSI_WHITE = "\u001B[37m"

    }

    fun create(subname: String): Logger {
        return Logger("$name/$subname")
    }


    private val out = PrintStream(System.out, true, "UTF-8")
    private val err = PrintStream(System.err, true, "UTF-8")

    fun verbose(msg: String) {
        log(Level.VERBOSE, msg)
    }

    fun debug(message: String) {
        log(Level.DEBUG, message)
    }

    fun info(msg: String) {
        log(Level.INFO, msg)
    }

    fun warn(msg: String) {
        log(Level.WARN, msg)
    }

    fun error(msg: String, throwable: Throwable? = null) {
        log(Level.ERROR, msg)
        throwable?.printStackTrace(err)
    }

    fun wtf(tag: String, msg: String, throwable: Throwable? = null) {
        log(Level.ASSERT, msg)
        throwable?.printStackTrace(err)
    }

    private fun log(lv: Level, message: String) {
        val level = lv.str()

        val time = LocalDateTime.now().format(formatter).brackets().color(ANSI_CYAN)
        val thread = Thread.currentThread().name.brackets().color(ANSI_BLUE)
        val from = name.brackets(1).color(ANSI_PURPLE)
        val msg = if (lv >= Level.ERROR) message.color(ANSI_RED) else message

        val formatted = "$time $thread $level $from -> $msg"

        out.println(formatted)
    }

    private fun String.color(color: String): String {
        return "$color$this${ANSI_RESET}"
    }

    private fun String.brackets(type: Int = 0): String {
        return when (type) {
            1 -> "($this)"
            else -> "[$this]"
        }
    }

    private fun Level.str(): String {
        return name.brackets().color(color)
    }

    private enum class Level(val color: String) {
        VERBOSE(ANSI_GREY),
        DEBUG(ANSI_WHITE),
        INFO(ANSI_GREEN),
        WARN(ANSI_YELLOW),
        ERROR(ANSI_RED),
        ASSERT(ANSI_PURPLE),
    }
}
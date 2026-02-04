package dev.meow.zeta.utils.resource

object ResourceManager {

    fun getBytes(path: String): ByteArray {
        return this::class.java.classLoader.getResourceAsStream(path)?.use { stream ->
            stream.readAllBytes()
        } ?: byteArrayOf()
    }

    fun getText(path: String): String {
        return this::class.java.classLoader.getResourceAsStream(path)
            ?.bufferedReader(Charsets.UTF_8)
            ?.use { it.readText() }
            ?: ""
    }

    fun getBytes(name: String, type: ResourceType): ByteArray {
        return getBytes(type.directory + name)
    }

    fun getText(name: String, type: ResourceType): String {
        return getText(type.directory + name)
    }
}
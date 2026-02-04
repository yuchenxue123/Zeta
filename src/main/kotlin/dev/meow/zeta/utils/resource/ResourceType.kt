package dev.meow.zeta.utils.resource

private const val FONT_DIRECTORY = "assets/zeta/font/"
private const val IMAGE_DIRECTORY = "assets/zeta/image/"

enum class ResourceType(val directory: String) {
    FONT(FONT_DIRECTORY),
    IMAGE(IMAGE_DIRECTORY),
}
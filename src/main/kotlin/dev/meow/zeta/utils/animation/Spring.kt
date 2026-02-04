package dev.meow.zeta.utils.animation

data class Spring(
    val stiffness: Float,
    val damping: Float
) {

    companion object {

        /**
         * 默认
         */
        val Default = Spring(stiffness = 200f, damping = 15f)

        /**
         * Q弹
         */
        val Bouncy = Spring(stiffness = 500f, damping = 10f)

        /**
         * 丝滑
         */
        val Smooth = Spring(stiffness = 150f, damping = 30f)

        /**
         * 响应快
         */
        val Snappy = Spring(stiffness = 1500f, damping = 80f)

        /**
         * 软
         */
        val Run = Spring(stiffness = 50f, damping = 10f)

    }

}
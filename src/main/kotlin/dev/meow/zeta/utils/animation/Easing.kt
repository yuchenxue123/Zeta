package dev.meow.zeta.utils.animation

interface Easing {

    companion object {
        
        val Linear: Easing = LinearEasing()

        val EaseInSine: Easing = BezierEasing(0.47f, 0f, 0.745f, 0.715f)

        val EaseOutSine: Easing = BezierEasing(0.39f, 0.575f, 0.565f, 1f)

        val EaseInOutSine: Easing = BezierEasing(0.445f, 0.05f, 0.55f, 0.95f)
        
        val EaseInQuad: Easing = BezierEasing(0.55f, 0.085f, 0.68f, 0.53f)
        
        val EaseOutQuad: Easing = BezierEasing(0.25f, 0.46f, 0.45f, 0.94f)
        
        val EaseInOutQuad: Easing = BezierEasing(0.455f, 0.03f, 0.515f, 0.955f)
        
        val EaseInCubic: Easing = BezierEasing(0.55f, 0.055f, 0.675f, 0.19f)
  
        val EaseOutCubic: Easing = BezierEasing(0.215f, 0.61f, 0.355f, 1f)
 
        val EaseInOutCubic: Easing = BezierEasing(0.645f, 0.045f, 0.355f, 1f)
     
        val EaseInQuart: Easing = BezierEasing(0.895f, 0.03f, 0.685f, 0.22f)
   
        val EaseOutQuart: Easing = BezierEasing(0.165f, 0.84f, 0.44f, 1f)
  
        val EaseInOutQuart: Easing = BezierEasing(0.77f, 0f, 0.175f, 1f)
    
        val EaseInQuint: Easing = BezierEasing(0.755f, 0.05f, 0.855f, 0.06f)
     
        val EaseOutQuint: Easing = BezierEasing(0.23f, 1f, 0.32f, 1f)
 
        val EaseInOutQuint: Easing = BezierEasing(0.86f, 0f, 0.07f, 1f)

        val EaseInExpo: Easing = BezierEasing(0.95f, 0.05f, 0.795f, 0.035f)
      
        val EaseOutExpo: Easing = BezierEasing(0.19f, 1f, 0.22f, 1f)
  
        val EaseInOutExpo: Easing = BezierEasing(1f, 0f, 0f, 1f)

        val EaseInCirc: Easing = BezierEasing(0.6f, 0.04f, 0.98f, 0.335f)
        
        val EaseOutCirc: Easing = BezierEasing(0.075f, 0.82f, 0.165f, 1f)
        
        val EaseInOutCirc: Easing = BezierEasing(0.785f, 0.135f, 0.15f, 0.86f)

        val EaseInBack: Easing = BezierEasing(0.6f, -0.28f, 0.735f, 0.045f)
        
        val EaseOutBack: Easing = BezierEasing(0.175f, 0.885f, 0.32f, 1.275f)
        
        val EaseInOutBack: Easing = BezierEasing(0.68f, -0.55f, 0.265f, 1.55f)

    }

    fun ease(process: Float): Float

    fun reverse(): Easing = ReverseEasing(this)

    private class ReverseEasing(private val easing: Easing) : Easing {

        override fun ease(process: Float): Float = easing.ease(1f - process)

        override fun reverse(): Easing = easing

    }

    private class LinearEasing : Easing {

        override fun ease(process: Float): Float = process

    }




}
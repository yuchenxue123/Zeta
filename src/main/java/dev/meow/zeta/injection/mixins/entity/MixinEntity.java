package dev.meow.zeta.injection.mixins.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.meow.zeta.event.EventManager;
import dev.meow.zeta.events.player.PlayerVelocityEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public abstract class MixinEntity {

    @Shadow
    public abstract float getXRot();

    @Shadow
    public abstract double getX();

    @Shadow
    public abstract double getY();

    @Shadow
    public abstract double getZ();

    @Shadow
    public abstract boolean onGround();

    @Shadow
    public abstract float getYRot();

    @ModifyExpressionValue(
            method = "moveRelative",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;getInputVector(Lnet/minecraft/world/phys/Vec3;FF)Lnet/minecraft/world/phys/Vec3;"
            )
    )
    public Vec3 hookVelocityStrafe(Vec3 original, @Local(argsOnly = true) Vec3 movementInput, @Local(argsOnly = true) float speed, @Local(argsOnly = true) float yaw) {
        if ((Object) this != Minecraft.getInstance().player) {
            return original;
        }

        final var event = new PlayerVelocityEvent(movementInput, speed, yaw, original);
        EventManager.INSTANCE.callEvent(event);
        return event.getVelocity();
    }

    @ModifyExpressionValue(method = "isLocalInstanceAuthoritative", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isClientAuthoritative()Z"))
    private boolean hookFixFallDistanceCalculation(boolean original) {
        if ((Object) this == Minecraft.getInstance().player) {
            return false;
        }

        return original;
    }

}

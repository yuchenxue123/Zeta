package dev.meow.zeta.injection.mixins.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.meow.zeta.event.EventManager;
import dev.meow.zeta.events.player.PlayerAfterJumpEvent;
import dev.meow.zeta.events.player.PlayerJumpEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends MixinEntity {

    @Shadow
    protected abstract float getJumpPower();

    @Unique
    private PlayerJumpEvent jumpEvent;

    @Inject(method = "jumpFromGround", at = @At(value = "HEAD"), cancellable = true)
    private void hookJumpEvent(CallbackInfo ci) {
        if ((Object) this != Minecraft.getInstance().player) {
            return;
        }

        jumpEvent = new PlayerJumpEvent(this.getJumpPower(), this.getYRot());
        EventManager.INSTANCE.callEvent(jumpEvent);
        if (jumpEvent.isCancelled()) {
            ci.cancel();
        }
    }

    @ModifyExpressionValue(method = "jumpFromGround", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getJumpPower()F"))
    private float hookJumpVelocity(float original) {
        if (jumpEvent == null) {
            return original;
        }

        return jumpEvent.getVelocity();
    }

    @ModifyExpressionValue(method = "jumpFromGround", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getYRot()F"))
    private float hookJumpYaw(float original) {
        if (jumpEvent == null) {
            return original;
        }

        return jumpEvent.getYaw();
    }

    @Inject(method = "jumpFromGround", at = @At("RETURN"))
    private void hookAfterJumpEvent(CallbackInfo ci) {
        jumpEvent = null;

        if ((Object) this != Minecraft.getInstance().player) {
            return;
        }

        EventManager.INSTANCE.callEvent(new PlayerAfterJumpEvent());
    }

}

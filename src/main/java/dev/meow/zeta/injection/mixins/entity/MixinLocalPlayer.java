package dev.meow.zeta.injection.mixins.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.meow.zeta.event.EventManager;
import dev.meow.zeta.events.EventType;
import dev.meow.zeta.events.player.PlayerMoveEvent;
import dev.meow.zeta.events.player.PlayerMovementPacketsEvent;
import dev.meow.zeta.events.player.PlayerMovementTickEvent;
import dev.meow.zeta.events.player.PlayerTickEvent;
import dev.meow.zeta.features.module.ModuleManager;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public abstract class MixinLocalPlayer extends MixinPlayer {

    @Inject(method = "tick", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/player/AbstractClientPlayer;tick()V",
            shift = At.Shift.BEFORE,
            ordinal = 0),
            cancellable = true)
    private void hookTickEvent(CallbackInfo ci) {
        var tickEvent = new PlayerTickEvent();
        EventManager.INSTANCE.callEvent(tickEvent);

        if (tickEvent.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(method = "aiStep", at = @At("HEAD"))
    private void hookMovementTickEvent(CallbackInfo callbackInfo) {
        EventManager.INSTANCE.callEvent(new PlayerMovementTickEvent());
    }

    @Unique
    private PlayerMovementPacketsEvent playerMovementPacketsEvent;

    @Inject(method = "sendPosition", at = @At("HEAD"), cancellable = true)
    private void hookMovementPre(CallbackInfo callbackInfo) {
        playerMovementPacketsEvent = new PlayerMovementPacketsEvent(
                EventType.PRE,
                this.getX(), this.getY(), this.getZ(),
                this.onGround()
        );
        EventManager.INSTANCE.callEvent(playerMovementPacketsEvent);

        if (playerMovementPacketsEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @ModifyExpressionValue(
            method = "sendPosition",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;getX()D"
            )
    )
    private double hookModifyX(double original) {
        return playerMovementPacketsEvent.getX();
    }

    @ModifyExpressionValue(
            method = "sendPosition",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;getY()D"
            )
    )
    private double hookModifyY(double original) {
        return playerMovementPacketsEvent.getY();
    }

    @ModifyExpressionValue(
            method = "sendPosition",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;getZ()D"
            )
    )
    private double hookModifyZ(double original) {
        return playerMovementPacketsEvent.getZ();
    }

    @ModifyExpressionValue(method = "sendPosition", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;onGround()Z"))
    private boolean hookModifyOnGround(boolean original) {
        return playerMovementPacketsEvent.onGround();
    }

    @Inject(method = "sendPosition", at = @At("RETURN"))
    private void hookMovementPost(CallbackInfo callbackInfo) {
        EventManager.INSTANCE.callEvent(new PlayerMovementPacketsEvent(
                EventType.POST,
                this.getX(), this.getY(), this.getZ(),
                this.onGround()
        ));
    }

    @ModifyVariable(method = "move", at = @At("HEAD"), name = "arg2", ordinal = 0, index = 2, argsOnly = true)
    private Vec3 hookMove(Vec3 movement, MoverType type) {
        return EventManager.INSTANCE.callEvent(new PlayerMoveEvent(type, movement)).getMovement();
    }

}

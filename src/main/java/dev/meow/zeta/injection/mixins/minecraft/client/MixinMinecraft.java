package dev.meow.zeta.injection.mixins.minecraft.client;

import dev.meow.zeta.Zeta;
import dev.meow.zeta.event.EventManager;
import dev.meow.zeta.events.game.GameTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void onMinecraftInitialized(GameConfig config, CallbackInfo ci) {
        Zeta.INSTANCE.initialize();
    }

    @Inject(method = "destroy", at = @At("HEAD"))
    private void onMinecraftShutdown(CallbackInfo callback) {
        Zeta.INSTANCE.shutdown();
    }

    @Inject(method = "tick", at = @At(value = "HEAD"))
    private void hookTickEvent(CallbackInfo info) {
        EventManager.INSTANCE.callEvent(new GameTickEvent());
    }
}

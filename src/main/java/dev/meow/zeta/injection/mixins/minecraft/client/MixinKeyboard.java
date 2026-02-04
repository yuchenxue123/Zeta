package dev.meow.zeta.injection.mixins.minecraft.client;

import com.mojang.blaze3d.platform.InputConstants;
import dev.meow.zeta.event.EventManager;
import dev.meow.zeta.events.game.KeyboardEvent;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.input.KeyEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class MixinKeyboard {

    @Inject(method = "keyPress", at = @At(value = "FIELD",
            target = "Lnet/minecraft/client/Minecraft;screen:Lnet/minecraft/client/gui/screens/Screen;",
            shift = At.Shift.BEFORE,
            ordinal = 0))
    private void hookKeyboardKey(long l, int action, KeyEvent keyEvent, CallbackInfo ci) {
        var inputKey = InputConstants.getKey(keyEvent);
        EventManager.INSTANCE.callEvent(new KeyboardEvent(inputKey, keyEvent.key(), keyEvent.scancode(), action));
    }
}

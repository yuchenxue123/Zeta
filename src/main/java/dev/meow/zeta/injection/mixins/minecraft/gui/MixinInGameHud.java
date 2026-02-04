package dev.meow.zeta.injection.mixins.minecraft.gui;

import dev.meow.zeta.render.OverlayRenderer;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class MixinInGameHud {

    @Inject(method = "render", at = @At(value = "RETURN"))
    private void injected(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        OverlayRenderer.INSTANCE.render(guiGraphics, deltaTracker.getGameTimeDeltaPartialTick(false));
    }

}

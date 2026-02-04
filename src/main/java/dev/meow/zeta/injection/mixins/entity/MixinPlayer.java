package dev.meow.zeta.injection.mixins.entity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.meow.zeta.event.EventManager;
import dev.meow.zeta.events.player.PlayerSafeWalkEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public abstract class MixinPlayer extends MixinLivingEntity {

    @ModifyReturnValue(method = "isStayingOnGroundSurface", at = @At("RETURN"))
    private boolean hookSafeWalk(boolean original) {
        if ((Object) this == Minecraft.getInstance().player) {
            return original;
        }

        final var event = EventManager.INSTANCE.callEvent(new PlayerSafeWalkEvent());

        return original || event.isSafeWalk();
    }


}

package dev.meow.zeta.injection.mixins.minecraft.network;

import dev.meow.zeta.event.EventManager;
import dev.meow.zeta.events.PacketType;
import dev.meow.zeta.events.network.PacketEvent;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBundlePacket;
import net.minecraft.server.RunningOnDifferentThreadException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Connection.class)
public abstract class MixinConnection {

    @Shadow
    private static <T extends PacketListener> void genericsFtw(Packet<T> packet, PacketListener listener) {
    }

    @Inject(method = "send(Lnet/minecraft/network/protocol/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void hookSendPacket(Packet<?> packet, CallbackInfo ci) {
        final var packetEvent = new PacketEvent(PacketType.OUTBOUND, packet);
        EventManager.INSTANCE.callEvent(packetEvent);
        if (packetEvent.isCancelled()) {
            ci.cancel();
        }
    }

    /**
     * 泛型太棒了
     */
    @Inject(method = "genericsFtw", at = @At("HEAD"), cancellable = true, require = 1)
    private static void hookReceivePacket(Packet<?> packet, PacketListener listener, CallbackInfo ci) {
        // 这是捆绑数据包
        // 需要递归拆解
        // 但是 mc 应该没有多层的情况
        if (packet instanceof ClientboundBundlePacket bundlePacket) {
            ci.cancel();

            for (Packet<?> subPacket : bundlePacket.subPackets()) {
                try {
                    genericsFtw(subPacket, listener);
                } catch (RunningOnDifferentThreadException ignored) {
                }
            }
            return;
        }

        final var packetEvent = new PacketEvent(PacketType.INBOUND, packet);
        EventManager.INSTANCE.callEvent(packetEvent);
        if (packetEvent.isCancelled()) {
            ci.cancel();
        }
    }

}

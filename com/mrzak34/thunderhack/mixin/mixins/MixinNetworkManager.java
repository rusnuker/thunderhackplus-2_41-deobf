//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;
import io.netty.channel.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.network.play.server.*;
import net.minecraft.world.*;
import com.mrzak34.thunderhack.modules.combat.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import java.util.*;

@Mixin({ NetworkManager.class })
public class MixinNetworkManager
{
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("HEAD") }, cancellable = true)
    private void onSendPacketPre(final Packet<?> packet, final CallbackInfo info) {
        final PacketEvent.Send event = new PacketEvent.Send((Packet)packet);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("RETURN") }, cancellable = true)
    private void onSendPacketPost(final Packet<?> packet, final CallbackInfo info) {
        final PacketEvent.SendPost event = new PacketEvent.SendPost((Packet)packet);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "channelRead0" }, at = { @At("RETURN") }, cancellable = true)
    private void onChannelReadPost(final ChannelHandlerContext context, final Packet<?> packet, final CallbackInfo info) {
        if (Util.mc.player != null && Util.mc.world != null) {
            final PacketEvent.ReceivePost event = new PacketEvent.ReceivePost((Packet)packet);
            MinecraftForge.EVENT_BUS.post((Event)event);
            if (event.isCanceled()) {
                info.cancel();
            }
        }
    }
    
    @Inject(method = { "channelRead0" }, at = { @At("HEAD") }, cancellable = true)
    private void onChannelReadPre(final ChannelHandlerContext context, final Packet<?> packet, final CallbackInfo info) {
        if (Util.mc.player != null && Util.mc.world != null) {
            if (packet instanceof SPacketEntityStatus && ((SPacketEntityStatus)packet).getOpCode() == 35) {
                final Entity entity = ((SPacketEntityStatus)packet).getEntity((World)Util.mc.world);
                if (entity != null && entity.equals((Object)Util.mc.player)) {
                    AutoTotem.packet_latency_timer = System.currentTimeMillis();
                }
            }
            final PacketEvent.Receive event = new PacketEvent.Receive((Packet)packet);
            MinecraftForge.EVENT_BUS.post((Event)event);
            if (event.isCanceled()) {
                info.cancel();
            }
            else if (!event.getPostEvents().isEmpty()) {
                for (final Runnable runnable : event.getPostEvents()) {
                    Minecraft.getMinecraft().addScheduledTask(runnable);
                }
                info.cancel();
            }
        }
    }
}

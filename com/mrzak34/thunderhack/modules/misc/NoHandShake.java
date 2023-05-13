//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.misc;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraftforge.fml.common.network.internal.*;
import net.minecraft.network.play.client.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import io.netty.buffer.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class NoHandShake extends Module
{
    public NoHandShake() {
        super("NoHandshake", "\u043d\u0435 \u043e\u0442\u043f\u0440\u0430\u0432\u043b\u044f\u0435\u0442 \u043c\u043e\u0434\u043b\u0438\u0441\u0442-\u0441\u0435\u0440\u0432\u0435\u0440\u0443", Category.MISC);
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (event.getPacket() instanceof FMLProxyPacket && !NoHandShake.mc.isSingleplayer()) {
            event.setCanceled(true);
        }
        final CPacketCustomPayload packet;
        if (event.getPacket() instanceof CPacketCustomPayload && (packet = (CPacketCustomPayload)event.getPacket()).getChannelName().equals("MC|Brand")) {
            ((ICPacketCustomPayload)packet).setData(new PacketBuffer(Unpooled.buffer()).writeString("vanilla"));
        }
    }
}

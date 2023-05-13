//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.manager;

import net.minecraftforge.common.*;
import com.mrzak34.thunderhack.modules.*;
import com.mojang.realmsclient.gui.*;
import com.mrzak34.thunderhack.command.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.client.*;
import com.mrzak34.thunderhack.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ReloadManager
{
    public String prefix;
    
    public void init(final String prefix) {
        this.prefix = prefix;
        MinecraftForge.EVENT_BUS.register((Object)this);
        if (!Module.fullNullCheck()) {
            Command.sendMessage(ChatFormatting.RED + "\u0422\u0430\u043d\u0434\u0435\u0440\u0445\u0430\u043a \u043e\u0442\u043a\u043b\u044e\u0447\u0435\u043d! \u043d\u0430\u043f\u0438\u0448\u0438 " + prefix + "reload \u0447\u0442\u043e\u0431\u044b \u0432\u043a\u043b\u044e\u0447\u0438\u0442\u044c");
        }
    }
    
    public void unload() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        final CPacketChatMessage packet;
        if (event.getPacket() instanceof CPacketChatMessage && (packet = (CPacketChatMessage)event.getPacket()).getMessage().startsWith(this.prefix) && packet.getMessage().contains("reload")) {
            Thunderhack.load();
            event.setCanceled(true);
        }
    }
}

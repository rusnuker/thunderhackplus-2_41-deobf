//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.misc;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.client.*;
import com.mrzak34.thunderhack.command.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class MessageAppend extends Module
{
    public Setting<String> word;
    
    public MessageAppend() {
        super("MessageAppend", "\u0434\u043e\u0431\u0430\u0432\u043b\u044f\u0435\u0442 \u0444\u0440\u0430\u0437\u0443-\u0432 \u043a\u043e\u043d\u0446\u0435 \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f", Category.MISC);
        this.word = (Setting<String>)this.register(new Setting("word", (T)"   RAGE"));
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send e) {
        if (fullNullCheck()) {
            return;
        }
        if (e.getPacket() instanceof CPacketChatMessage) {
            if (((CPacketChatMessage)e.getPacket()).getMessage().startsWith("/") || ((CPacketChatMessage)e.getPacket()).getMessage().startsWith(Command.getCommandPrefix())) {
                return;
            }
            final CPacketChatMessage pac = (CPacketChatMessage)e.getPacket();
            ((ICPacketChatMessage)pac).setMessage(((CPacketChatMessage)e.getPacket()).getMessage() + this.word.getValue());
        }
    }
}

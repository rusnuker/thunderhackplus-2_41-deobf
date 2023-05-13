//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.player;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class XCarry extends Module
{
    public XCarry() {
        super("XCarry", "\u043f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u0445\u0440\u0430\u043d\u0438\u0442\u044c-\u043f\u0440\u0435\u0434\u043c\u0435\u0442\u044b \u0432 \u043c\u044b\u0448\u043a\u0435", Module.Category.PLAYER);
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send e) {
        if (fullNullCheck()) {
            return;
        }
        if (e.getPacket() instanceof CPacketCloseWindow) {
            e.setCanceled(true);
        }
    }
}

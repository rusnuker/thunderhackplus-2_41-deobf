//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.player;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class NoServerSlot extends Module
{
    public NoServerSlot() {
        super("NoServerSlot", "\u043d\u0435 \u0434\u0430\u0435\u0442 \u0441\u0435\u0440\u0432\u0435\u0440\u0443 \u0441\u0432\u0430\u043f\u0430\u0442\u044c \u0441\u043b\u043e\u0442\u044b", Module.Category.PLAYER);
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketHeldItemChange) {
            event.setCanceled(true);
            NoServerSlot.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(NoServerSlot.mc.player.inventory.currentItem));
        }
    }
}

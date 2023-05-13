//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.player;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class PearlBlockThrow extends Module
{
    public PearlBlockThrow() {
        super("PearlBlockThrow", "PearlBlockThrow", Module.Category.PLAYER);
    }
    
    @SubscribeEvent
    public void onPackerSend(final PacketEvent.Send event) {
        if (fullNullCheck()) {
            return;
        }
        if (PearlBlockThrow.mc.player.getHeldItemMainhand().getItem() == Items.ENDER_PEARL && event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
            final CPacketPlayerTryUseItemOnBlock pac = (CPacketPlayerTryUseItemOnBlock)event.getPacket();
            ((ICPacketPlayerTryUseItemOnBlock)pac).setHand(EnumHand.OFF_HAND);
        }
    }
}

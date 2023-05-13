//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.movement;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class AirStuck extends Module
{
    public AirStuck() {
        super("AirStuck", "AirStuck", Module.Category.MOVEMENT);
    }
    
    public void onUpdate() {
        AirStuck.mc.player.motionX = 0.0;
        AirStuck.mc.player.motionY = 0.0;
        AirStuck.mc.player.motionZ = 0.0;
    }
    
    @SubscribeEvent
    public void onSendPacket(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof CPacketPlayer) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketPlayer.Position) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketPlayer.PositionRotation) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketEntityAction) {
            event.setCanceled(true);
        }
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.player;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.server.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class NoServerRotation extends Module
{
    public NoServerRotation() {
        super("NoServerRotation", "\u0422\u0435\u0431\u0435 \u043d\u0435 \u0432\u0435\u0440\u0442\u0438\u0442 \u0431\u043e\u0448\u043a\u0443", Module.Category.PLAYER);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onReceivePacket(final PacketEvent.Receive event) {
        if (fullNullCheck()) {
            return;
        }
        if (event.getPacket() instanceof SPacketPlayerPosLook) {
            final SPacketPlayerPosLook sp = (SPacketPlayerPosLook)event.getPacket();
            ((ISPacketPlayerPosLook)sp).setYaw(NoServerRotation.mc.player.rotationYaw);
            ((ISPacketPlayerPosLook)sp).setPitch(NoServerRotation.mc.player.rotationPitch);
        }
    }
}

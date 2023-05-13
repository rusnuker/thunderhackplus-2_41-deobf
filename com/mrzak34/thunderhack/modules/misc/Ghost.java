//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.misc;

import com.mrzak34.thunderhack.modules.*;
import net.minecraft.client.gui.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Ghost extends Module
{
    private boolean bypass;
    
    public Ghost() {
        super("Ghost", "\u0416\u0438\u0442\u044c \u043f\u043e\u0441\u043b\u0435 \u0441\u043c\u0435\u0440\u0442\u0438", Category.MISC);
        this.bypass = false;
    }
    
    @Override
    public void onEnable() {
        this.bypass = false;
    }
    
    @Override
    public void onDisable() {
        if (Ghost.mc.player != null) {
            Ghost.mc.player.respawnPlayer();
        }
        this.bypass = false;
    }
    
    @Override
    public void onUpdate() {
        if (Ghost.mc.player == null || Ghost.mc.world == null) {
            return;
        }
        if (Ghost.mc.player.getHealth() == 0.0f) {
            Ghost.mc.player.setHealth(20.0f);
            Ghost.mc.player.isDead = false;
            this.bypass = true;
            Ghost.mc.displayGuiScreen((GuiScreen)null);
            Ghost.mc.player.setPositionAndUpdate(Ghost.mc.player.posX, Ghost.mc.player.posY, Ghost.mc.player.posZ);
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (this.bypass && event.getPacket() instanceof CPacketPlayer) {
            event.setCanceled(true);
        }
    }
}

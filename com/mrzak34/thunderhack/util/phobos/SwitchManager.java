//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import net.minecraftforge.common.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.network.play.server.*;

public class SwitchManager
{
    private final Timer timer;
    private volatile int last_slot;
    
    public SwitchManager() {
        this.timer = new Timer();
    }
    
    public void unload() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    public void init() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send e) {
        if (e.getPacket() instanceof CPacketHeldItemChange) {
            this.timer.reset();
            this.last_slot = ((CPacketHeldItemChange)e.getPacket()).getSlotId();
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive e) {
        if (Util.mc.player == null || Util.mc.world == null) {
            return;
        }
        if (e.getPacket() instanceof SPacketHeldItemChange) {
            this.last_slot = ((SPacketHeldItemChange)e.getPacket()).getHeldItemHotbarIndex();
        }
    }
    
    public long getLastSwitch() {
        return this.timer.getTimeMs();
    }
    
    public int getSlot() {
        return this.last_slot;
    }
}

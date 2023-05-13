//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import net.minecraftforge.common.*;
import net.minecraft.network.play.client.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class NoMotionUpdateService
{
    private boolean awaiting;
    
    public void init() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public void unload() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer.PositionRotation) {
            this.setAwaiting(false);
        }
        if (event.getPacket() instanceof CPacketPlayer.Position) {
            this.setAwaiting(false);
        }
        if (event.getPacket() instanceof CPacketPlayer.Rotation) {
            this.setAwaiting(false);
        }
        if (event.getPacket() instanceof CPacketPlayer) {
            this.setAwaiting(false);
        }
    }
    
    @SubscribeEvent
    public void onMotion(final EventSync e) {
        if (e.isCanceled()) {
            return;
        }
        this.setAwaiting(true);
    }
    
    @SubscribeEvent
    public void onPost(final EventPostSync e) {
        if (e.isCanceled()) {
            return;
        }
        if (this.isAwaiting()) {
            final NoMotionUpdateEvent noMotionUpdate = new NoMotionUpdateEvent();
            MinecraftForge.EVENT_BUS.post((Event)noMotionUpdate);
        }
        this.setAwaiting(false);
    }
    
    public boolean isAwaiting() {
        return this.awaiting;
    }
    
    public void setAwaiting(final boolean awaiting) {
        this.awaiting = awaiting;
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.events;

import net.minecraft.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.*;
import com.mrzak34.thunderhack.util.phobos.*;

public class PacketEvent extends Event
{
    private final Packet<?> packet;
    
    public PacketEvent(final Packet<?> packet) {
        this.packet = packet;
    }
    
    public <T extends Packet<?>> T getPacket() {
        return (T)this.packet;
    }
    
    @Cancelable
    public static class Send extends PacketEvent
    {
        public Send(final Packet<?> packet) {
            super(packet);
        }
    }
    
    @Cancelable
    public static class Receive extends PacketEvent
    {
        private final Deque<Runnable> postEvents;
        
        public Receive(final Packet<?> packet) {
            super(packet);
            this.postEvents = new ArrayDeque<Runnable>();
        }
        
        public void addPostEvent(final SafeRunnable runnable) {
            this.postEvents.add(runnable);
        }
        
        public Deque<Runnable> getPostEvents() {
            return this.postEvents;
        }
    }
    
    @Cancelable
    public static class SendPost extends PacketEvent
    {
        public SendPost(final Packet<?> packet) {
            super(packet);
        }
    }
    
    @Cancelable
    public static class ReceivePost extends PacketEvent
    {
        public ReceivePost(final Packet<?> packet) {
            super(packet);
        }
    }
}

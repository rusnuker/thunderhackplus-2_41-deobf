//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import java.util.*;
import net.minecraft.entity.player.*;
import java.util.concurrent.*;
import net.minecraftforge.common.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.server.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.util.*;

public class CombatManager
{
    private final Map<EntityPlayer, PopCounter> pops;
    
    public CombatManager() {
        this.pops = new ConcurrentHashMap<EntityPlayer, PopCounter>();
    }
    
    public void init() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public void unload() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive e) {
        if (Util.mc.player == null || Util.mc.world == null) {
            return;
        }
        if (e.getPacket() instanceof SPacketEntityStatus) {
            switch (((SPacketEntityStatus)e.getPacket()).getOpCode()) {
                case 3: {
                    Util.mc.addScheduledTask(() -> this.onDeath((Util.mc.world == null) ? null : ((SPacketEntityStatus)e.getPacket()).getEntity((World)Util.mc.world)));
                    break;
                }
                case 35: {
                    Util.mc.addScheduledTask(() -> this.onTotemPop((SPacketEntityStatus)e.getPacket()));
                    break;
                }
            }
        }
    }
    
    public void resetCombatManager() {
        this.pops.clear();
    }
    
    public int getPops(final Entity player) {
        if (player instanceof EntityPlayer) {
            final PopCounter popCounter = this.pops.get(player);
            if (popCounter != null) {
                return popCounter.getPops();
            }
        }
        return 0;
    }
    
    public long lastPop(final Entity player) {
        if (player instanceof EntityPlayer) {
            final PopCounter popCounter = this.pops.get(player);
            if (popCounter != null) {
                return popCounter.lastPop();
            }
        }
        return 2147483647L;
    }
    
    private void onTotemPop(final SPacketEntityStatus packet) {
        final Entity player = packet.getEntity((World)Util.mc.world);
        if (player instanceof EntityPlayer) {
            this.pops.computeIfAbsent((EntityPlayer)player, v -> new PopCounter()).pop();
        }
    }
    
    private void onDeath(final Entity entity) {
        if (entity instanceof EntityPlayer) {
            this.pops.remove(entity);
        }
    }
    
    private static class PopCounter
    {
        private final Timer timer;
        private int pops;
        
        private PopCounter() {
            this.timer = new Timer();
        }
        
        public int getPops() {
            return this.pops;
        }
        
        public void pop() {
            this.timer.reset();
            ++this.pops;
        }
        
        public void reset() {
            this.pops = 0;
        }
        
        public long lastPop() {
            return this.timer.getTimeMs();
        }
    }
}

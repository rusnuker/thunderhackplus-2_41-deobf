//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.manager;

import com.mrzak34.thunderhack.util.math.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.client.network.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.server.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.common.*;

public class ServerManager
{
    private final Timer timeDelay;
    private final ArrayDeque<Float> tpsResult;
    private long time;
    private float tps;
    
    public ServerManager() {
        this.tpsResult = new ArrayDeque<Float>(20);
        this.timeDelay = new Timer();
    }
    
    public Timer getDelayTimer() {
        return this.timeDelay;
    }
    
    public long getTime() {
        return this.time;
    }
    
    public void setTime(final long time) {
        this.time = time;
    }
    
    public float getTPS() {
        return MathUtil.round2(this.tps);
    }
    
    public void setTPS(final float tps) {
        this.tps = tps;
    }
    
    public ArrayDeque<Float> getTPSResults() {
        return this.tpsResult;
    }
    
    public int getPing() {
        if (Util.mc.world == null || Util.mc.player == null) {
            return 0;
        }
        try {
            return Objects.requireNonNull(Util.mc.getConnection()).getPlayerInfo(Util.mc.getConnection().getGameProfile().getId()).getResponseTime();
        }
        catch (Exception e) {
            return 0;
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (!(event.getPacket() instanceof SPacketChat)) {
            this.getDelayTimer().reset();
        }
        if (event.getPacket() instanceof SPacketTimeUpdate) {
            if (this.getTime() != 0L) {
                if (this.getTPSResults().size() > 20) {
                    this.getTPSResults().poll();
                }
                this.getTPSResults().add(20.0f * (1000.0f / (System.currentTimeMillis() - this.getTime())));
                float f = 0.0f;
                for (final Float value : this.getTPSResults()) {
                    f += Math.max(0.0f, Math.min(20.0f, value));
                }
                this.setTPS(f / this.getTPSResults().size());
            }
            this.setTime(System.currentTimeMillis());
        }
    }
    
    public void init() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public void unload() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
}

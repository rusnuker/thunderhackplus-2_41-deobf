//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.network.play.server.*;
import com.mrzak34.thunderhack.*;
import java.util.*;

public class ServerTickManager
{
    private final Timer serverTickTimer;
    private final ArrayDeque<Integer> spawnObjectTimes;
    private int averageSpawnObjectTime;
    
    public ServerTickManager() {
        this.serverTickTimer = new Timer();
        this.spawnObjectTimes = new ArrayDeque<Integer>();
    }
    
    public void init() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public void unload() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    @SubscribeEvent
    public void onConnect(final ConnectToServerEvent e) {
        this.resetTickManager();
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive e) {
        if (Util.mc.player == null || Util.mc.world == null) {
            return;
        }
        if (e.getPacket() instanceof SPacketTimeUpdate && Util.mc.world != null && Util.mc.world.isRemote) {
            this.resetTickManager();
        }
        if (e.getPacket() instanceof SPacketSpawnObject && Util.mc.world != null && Util.mc.world.isRemote) {
            this.onSpawnObject();
        }
    }
    
    public int getTickTime() {
        if (this.serverTickTimer.getTimeMs() < 50L) {
            return (int)this.serverTickTimer.getTimeMs();
        }
        return (int)(this.serverTickTimer.getTimeMs() % this.getServerTickLengthMS());
    }
    
    public int getTickTimeAdjusted() {
        final int time = this.getTickTime() + Thunderhack.serverManager.getPing() / 2;
        if (time < this.getServerTickLengthMS()) {
            return time;
        }
        return time % this.getServerTickLengthMS();
    }
    
    public int getTickTimeAdjustedForServerPackets() {
        final int time = this.getTickTime() - Thunderhack.serverManager.getPing() / 2;
        if (time < this.getServerTickLengthMS() && time > 0) {
            return time;
        }
        if (time < 0) {
            return time + this.getServerTickLengthMS();
        }
        return time % this.getServerTickLengthMS();
    }
    
    public void resetTickManager() {
        this.serverTickTimer.reset();
        this.serverTickTimer.adjust(Thunderhack.serverManager.getPing() / 2);
    }
    
    public int getServerTickLengthMS() {
        if (Thunderhack.serverManager.getTPS() == 0.0f) {
            return 50;
        }
        return (int)(50.0f * (20.0f / Thunderhack.serverManager.getTPS()));
    }
    
    public void onSpawnObject() {
        final int time = this.getTickTimeAdjustedForServerPackets();
        if (this.spawnObjectTimes.size() > 10) {
            this.spawnObjectTimes.poll();
        }
        this.spawnObjectTimes.add(time);
        int totalTime = 0;
        for (final int spawnTime : this.spawnObjectTimes) {
            totalTime += spawnTime;
        }
        this.averageSpawnObjectTime = totalTime / this.spawnObjectTimes.size();
    }
    
    public int normalize(int toNormalize) {
        while (toNormalize < 0) {
            toNormalize += this.getServerTickLengthMS();
        }
        while (toNormalize > this.getServerTickLengthMS()) {
            toNormalize -= this.getServerTickLengthMS();
        }
        return toNormalize;
    }
    
    public boolean valid(final int currentTime, final int minTime, final int maxTime) {
        if (minTime > maxTime) {
            return currentTime >= minTime || currentTime <= maxTime;
        }
        return currentTime >= minTime && currentTime <= maxTime;
    }
    
    public int getSpawnTime() {
        return this.averageSpawnObjectTime;
    }
}

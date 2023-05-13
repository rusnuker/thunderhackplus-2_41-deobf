//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import com.mrzak34.thunderhack.util.*;

public class GuardTimer implements DiscreteTimer
{
    private final Timer guard;
    private final long interval;
    private final long guardDelay;
    private long delay;
    private long time;
    
    public GuardTimer() {
        this(1000L);
    }
    
    public GuardTimer(final long guardDelay) {
        this(guardDelay, 10L);
    }
    
    public GuardTimer(final long guardDelay, final long interval) {
        this.guard = new Timer();
        this.guardDelay = guardDelay;
        this.interval = interval;
    }
    
    public long getTime() {
        return System.currentTimeMillis() - this.time;
    }
    
    public void setTime(final long time) {
        this.time = time;
    }
    
    public boolean passed(final long ms) {
        return ms == 0L || ms < this.interval || System.currentTimeMillis() - this.time >= ms;
    }
    
    public DiscreteTimer reset(final long ms) {
        if (ms <= this.interval || this.delay != ms || this.guard.passedMs(this.guardDelay)) {
            this.delay = ms;
            this.reset();
        }
        else {
            this.time += ms;
        }
        return (DiscreteTimer)this;
    }
    
    public void reset() {
        this.time = System.currentTimeMillis();
        this.guard.reset();
    }
}

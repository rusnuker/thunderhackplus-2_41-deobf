//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import com.mrzak34.thunderhack.util.phobos.*;

public class Timer implements Passable
{
    private long time;
    
    public Timer() {
        this.time = -1L;
    }
    
    public boolean passedS(final double s) {
        return this.getMs(System.nanoTime() - this.time) >= (long)(s * 1000.0);
    }
    
    public boolean passedM(final double m) {
        return this.getMs(System.nanoTime() - this.time) >= (long)(m * 1000.0 * 60.0);
    }
    
    public boolean passedDms(final double dms) {
        return this.getMs(System.nanoTime() - this.time) >= (long)(dms * 10.0);
    }
    
    public boolean passedDs(final double ds) {
        return this.getMs(System.nanoTime() - this.time) >= (long)(ds * 100.0);
    }
    
    public boolean passedMs(final long ms) {
        return this.getMs(System.nanoTime() - this.time) >= ms;
    }
    
    public boolean passedNS(final long ns) {
        return System.nanoTime() - this.time >= ns;
    }
    
    public void setMs(final long ms) {
        this.time = System.nanoTime() - ms * 1000000L;
    }
    
    public long getPassedTimeMs() {
        return this.getMs(System.nanoTime() - this.time);
    }
    
    public void reset() {
        this.time = System.nanoTime();
    }
    
    public long getMs(final long time) {
        return time / 1000000L;
    }
    
    public boolean passed(final long delay, final boolean reset) {
        if (reset) {
            this.reset();
        }
        return System.currentTimeMillis() - this.time >= delay;
    }
    
    public long getTimeMs() {
        return this.getMs(System.nanoTime() - this.time);
    }
    
    public long getTime() {
        return System.nanoTime() - this.time;
    }
    
    public void adjust(final int by) {
        this.time += by;
    }
    
    public boolean passed(final long delay) {
        return this.getMs(System.nanoTime() - this.time) >= delay;
    }
}

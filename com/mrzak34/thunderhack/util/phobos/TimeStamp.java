//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

public class TimeStamp
{
    private final long timeStamp;
    private boolean valid;
    
    public TimeStamp() {
        this.valid = true;
        this.timeStamp = System.currentTimeMillis();
    }
    
    public long getTimeStamp() {
        return this.timeStamp;
    }
    
    public boolean isValid() {
        return this.valid;
    }
    
    public void setValid(final boolean valid) {
        this.valid = valid;
    }
}

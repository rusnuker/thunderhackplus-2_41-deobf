//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import java.util.concurrent.atomic.*;
import net.minecraft.entity.*;

public class EntityTime
{
    private final AtomicBoolean valid;
    private final Entity entity;
    public long time;
    
    public EntityTime(final Entity entity) {
        this.valid = new AtomicBoolean(true);
        this.entity = entity;
        this.time = System.currentTimeMillis();
    }
    
    public boolean passed(final long ms) {
        return ms <= 0L || System.currentTimeMillis() - this.time > ms;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
    
    public boolean isValid() {
        return this.valid.get();
    }
    
    public void setValid(final boolean valid) {
        this.valid.set(valid);
    }
    
    public void reset() {
        this.time = System.currentTimeMillis();
    }
}

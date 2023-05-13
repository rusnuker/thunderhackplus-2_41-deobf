//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import net.minecraft.entity.*;

public class CustomEntityTime extends EntityTime
{
    private final long customTime;
    
    public CustomEntityTime(final Entity entity, final long customTime) {
        super(entity);
        this.customTime = customTime;
    }
    
    @Override
    public boolean passed(final long ms) {
        return System.currentTimeMillis() - this.time > this.customTime;
    }
}

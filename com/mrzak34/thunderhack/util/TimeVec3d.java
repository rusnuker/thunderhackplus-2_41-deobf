//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.util.math.*;

public class TimeVec3d extends Vec3d
{
    private final long time;
    
    public TimeVec3d(final double xIn, final double yIn, final double zIn, final long time) {
        super(xIn, yIn, zIn);
        this.time = time;
    }
    
    public TimeVec3d(final Vec3i vector, final long time) {
        super(vector);
        this.time = time;
    }
    
    public long getTime() {
        return this.time;
    }
}

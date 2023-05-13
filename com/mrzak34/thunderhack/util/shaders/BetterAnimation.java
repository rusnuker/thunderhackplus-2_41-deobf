//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.shaders;

import net.minecraft.util.math.*;
import net.minecraft.client.*;

public class BetterAnimation
{
    private int prevTick;
    private int tick;
    private final int maxTick;
    
    public BetterAnimation(final int maxTick) {
        this.maxTick = maxTick;
    }
    
    public BetterAnimation() {
        this(10);
    }
    
    public static double dropAnimation(final double value) {
        final double c1 = 1.70158;
        final double c2 = 2.70158;
        return 1.0 + c2 * Math.pow(value - 1.0, 3.0) + c1 * Math.pow(value - 1.0, 2.0);
    }
    
    public void update(final boolean update) {
        this.prevTick = this.tick;
        this.tick = MathHelper.clamp(this.tick + (update ? 1 : -1), 0, this.maxTick);
    }
    
    public double getAnimationd() {
        return dropAnimation((this.prevTick + (this.tick - this.prevTick) * Minecraft.getMinecraft().getRenderPartialTicks()) / this.maxTick);
    }
}

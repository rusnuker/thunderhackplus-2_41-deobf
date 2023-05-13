//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.shaders;

import net.minecraft.util.math.*;
import net.minecraft.client.*;
import com.mrzak34.thunderhack.util.math.*;

public class BetterDynamicAnimation
{
    private final int maxTicks;
    private double value;
    private double dstValue;
    private int prevStep;
    private int step;
    
    public BetterDynamicAnimation(final int maxTicks) {
        this.maxTicks = maxTicks;
    }
    
    public BetterDynamicAnimation() {
        this(5);
    }
    
    public void update() {
        this.prevStep = this.step;
        this.step = MathHelper.clamp(this.step + 1, 0, this.maxTicks);
    }
    
    public void setValue(final double value) {
        if (value != this.dstValue) {
            this.prevStep = 0;
            this.step = 0;
            this.value = this.dstValue;
            this.dstValue = value;
        }
    }
    
    public double getAnimationD() {
        final float pt = Minecraft.getMinecraft().getRenderPartialTicks();
        final double delta = this.dstValue - this.value;
        final double animation = DynamicAnimation.createAnimation((this.prevStep + (this.step - this.prevStep) * pt) / (double)this.maxTicks);
        return this.value + delta * animation;
    }
}

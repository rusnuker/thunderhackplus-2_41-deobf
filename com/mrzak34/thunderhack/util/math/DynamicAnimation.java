//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.math;

import net.minecraft.util.math.*;
import net.minecraft.client.*;

public class DynamicAnimation
{
    private final double speed;
    private double startValue;
    private double targetValue;
    private double outValue;
    private double step;
    private double prevStep;
    private double delta;
    
    public DynamicAnimation(final double speed) {
        this.speed = 0.15000000596046448 + speed;
    }
    
    public DynamicAnimation() {
        this(0.0);
    }
    
    public static double createAnimation(final double value) {
        return Math.sqrt(1.0 - Math.pow(value - 1.0, 2.0));
    }
    
    public void update() {
        this.prevStep = this.step;
        this.step = MathHelper.clamp(this.step + this.speed, 0.0, 1.0);
        this.outValue = this.startValue + this.delta * createAnimation(this.step);
    }
    
    public double getValue() {
        return this.startValue + this.delta * createAnimation(this.prevStep + (this.step - this.prevStep) * Minecraft.getMinecraft().getRenderPartialTicks());
    }
    
    public void setValue(final double value) {
        if (value == this.targetValue) {
            return;
        }
        this.targetValue = value;
        this.startValue = this.outValue;
        this.prevStep = 0.0;
        this.step = 0.0;
        this.delta = this.targetValue - this.startValue;
    }
}

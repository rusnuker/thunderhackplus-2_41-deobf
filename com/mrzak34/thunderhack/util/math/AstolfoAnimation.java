//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.math;

import net.minecraft.client.*;

public class AstolfoAnimation
{
    private double value;
    private double prevValue;
    
    public static int HSBtoRGB(final float hue, final float saturation, final float brightness) {
        int r = 0;
        int g = 0;
        int b = 0;
        if (saturation == 0.0f) {
            g = (r = (b = (int)(brightness * 255.0f + 0.5f)));
        }
        else {
            final float h = (hue - (float)Math.floor(hue)) * 6.0f;
            final float f = h - (float)Math.floor(h);
            final float p = brightness * (1.0f - saturation);
            final float q = brightness * (1.0f - saturation * f);
            final float t = brightness * (1.0f - saturation * (1.0f - f));
            switch ((int)h) {
                case 0: {
                    r = (int)(brightness * 255.0f + 0.5f);
                    g = (int)(t * 255.0f + 0.5f);
                    b = (int)(p * 255.0f + 0.5f);
                    break;
                }
                case 1: {
                    r = (int)(q * 255.0f + 0.5f);
                    g = (int)(brightness * 255.0f + 0.5f);
                    b = (int)(p * 255.0f + 0.5f);
                    break;
                }
                case 2: {
                    r = (int)(p * 255.0f + 0.5f);
                    g = (int)(brightness * 255.0f + 0.5f);
                    b = (int)(t * 255.0f + 0.5f);
                    break;
                }
                case 3: {
                    r = (int)(p * 255.0f + 0.5f);
                    g = (int)(q * 255.0f + 0.5f);
                    b = (int)(brightness * 255.0f + 0.5f);
                    break;
                }
                case 4: {
                    r = (int)(t * 255.0f + 0.5f);
                    g = (int)(p * 255.0f + 0.5f);
                    b = (int)(brightness * 255.0f + 0.5f);
                    break;
                }
                case 5: {
                    r = (int)(brightness * 255.0f + 0.5f);
                    g = (int)(p * 255.0f + 0.5f);
                    b = (int)(q * 255.0f + 0.5f);
                    break;
                }
            }
        }
        return 0xFF000000 | r << 16 | g << 8 | b;
    }
    
    public void update() {
        this.prevValue = this.value;
        this.value += 0.01;
    }
    
    public int getColor(final double offset) {
        double hue = (this.prevValue + (this.value - this.prevValue) * Minecraft.getMinecraft().getRenderPartialTicks() + offset) % 1.0;
        if (hue > 0.5) {
            hue = 0.5 - (hue - 0.5);
        }
        hue += 0.5;
        return HSBtoRGB((float)hue, 0.5f, 1.0f);
    }
}

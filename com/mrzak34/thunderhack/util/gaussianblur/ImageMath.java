//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.gaussianblur;

public class ImageMath
{
    public static final float PI = 3.1415927f;
    
    public static float step(final float a, final float x) {
        return (x < a) ? 0.0f : 1.0f;
    }
    
    public static float clamp(final float x, final float a, final float b) {
        return (x < a) ? a : Math.min(x, b);
    }
    
    public static int clamp(final int x, final int a, final int b) {
        return (x < a) ? a : Math.min(x, b);
    }
    
    public static double mod(double a, final double b) {
        final int n = (int)(a / b);
        a -= n * b;
        if (a < 0.0) {
            return a + b;
        }
        return a;
    }
    
    public static float mod(float a, final float b) {
        final int n = (int)(a / b);
        a -= n * b;
        if (a < 0.0f) {
            return a + b;
        }
        return a;
    }
    
    public static int mod(int a, final int b) {
        final int n = a / b;
        a -= n * b;
        if (a < 0) {
            return a + b;
        }
        return a;
    }
    
    public static void premultiply(final int[] p, final int offset, int length) {
        length += offset;
        for (int i = offset; i < length; ++i) {
            final int rgb = p[i];
            final int a = rgb >> 24 & 0xFF;
            int r = rgb >> 16 & 0xFF;
            int g = rgb >> 8 & 0xFF;
            int b = rgb & 0xFF;
            final float f = a * 0.003921569f;
            r *= (int)f;
            g *= (int)f;
            b *= (int)f;
            p[i] = (a << 24 | r << 16 | g << 8 | b);
        }
    }
    
    public static void unpremultiply(final int[] p, final int offset, int length) {
        length += offset;
        for (int i = offset; i < length; ++i) {
            final int rgb = p[i];
            final int a = rgb >> 24 & 0xFF;
            int r = rgb >> 16 & 0xFF;
            int g = rgb >> 8 & 0xFF;
            int b = rgb & 0xFF;
            if (a != 0 && a != 255) {
                final float f = 255.0f / a;
                r *= (int)f;
                g *= (int)f;
                b *= (int)f;
                if (r > 255) {
                    r = 255;
                }
                if (g > 255) {
                    g = 255;
                }
                if (b > 255) {
                    b = 255;
                }
                p[i] = (a << 24 | r << 16 | g << 8 | b);
            }
        }
    }
}

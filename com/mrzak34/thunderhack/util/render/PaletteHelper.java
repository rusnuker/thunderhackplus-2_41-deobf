//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.render;

import java.awt.*;

public class PaletteHelper
{
    public static int getColor(final Color color) {
        return getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }
    
    public static int getColor(final int bright) {
        return getColor(bright, bright, bright, 255);
    }
    
    public static Color astolfo(final float yDist, final float yTotal, final float saturation, final float speedt) {
        float speed;
        float hue;
        for (speed = 1800.0f, hue = System.currentTimeMillis() % (int)speed + (yTotal - yDist) * speedt; hue > speed; hue -= speed) {}
        if ((hue /= speed) > 0.5) {
            hue = 0.5f - (hue - 0.5f);
        }
        return Color.getHSBColor(hue += 0.5f, saturation, 1.0f);
    }
    
    public static int getColor(final int red, final int green, final int blue, final int alpha) {
        int color = 0;
        color |= alpha << 24;
        color |= red << 16;
        color |= green << 8;
        color |= blue;
        return color;
    }
    
    public static Color astolfo(final boolean clickgui, final int yOffset) {
        final float speed = clickgui ? 3500.0f : 3000.0f;
        float hue = (float)(System.currentTimeMillis() % (int)speed + yOffset);
        if (hue > speed) {
            hue -= speed;
        }
        hue /= speed;
        if (hue > 0.5f) {
            hue = 0.5f - (hue - 0.5f);
        }
        hue += 0.5f;
        return Color.getHSBColor(hue, 0.4f, 1.0f);
    }
    
    public static int getColor(final int brightness, final int alpha) {
        return getColor(brightness, brightness, brightness, alpha);
    }
    
    public static Color rainbow(final int delay, final float saturation, final float brightness) {
        double rainbow = Math.ceil((System.currentTimeMillis() + delay) / 16.0f);
        rainbow %= 360.0;
        return Color.getHSBColor((float)(rainbow / 360.0), saturation, brightness);
    }
    
    public static int fadeColor(final int startColor, final int endColor, float progress) {
        if (progress > 1.0f) {
            progress = 1.0f - progress % 1.0f;
        }
        return fade(startColor, endColor, progress);
    }
    
    public static int fade(final int startColor, final int endColor, final float progress) {
        final float invert = 1.0f - progress;
        final int r = (int)((startColor >> 16 & 0xFF) * invert + (endColor >> 16 & 0xFF) * progress);
        final int g = (int)((startColor >> 8 & 0xFF) * invert + (endColor >> 8 & 0xFF) * progress);
        final int b = (int)((startColor & 0xFF) * invert + (endColor & 0xFF) * progress);
        final int a = (int)((startColor >> 24 & 0xFF) * invert + (endColor >> 24 & 0xFF) * progress);
        return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF);
    }
}

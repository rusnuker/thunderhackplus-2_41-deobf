//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.clickui;

import java.awt.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.util.math.*;
import java.awt.image.*;

public class ColorUtil
{
    public static int fade(final Color color, final int delay) {
        final float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float brightness = Math.abs((System.currentTimeMillis() % 2000L + delay) / 1000.0f % 2.0f - 1.0f);
        brightness = 0.5f + 0.5f * brightness;
        hsb[2] = brightness % 2.0f;
        return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
    }
    
    public static int astolfoRainbow2(final int counter, final int alpha) {
        final int width = 110;
        double rainbowState = Math.ceil((double)(System.currentTimeMillis() - counter * 110L)) / 11.0;
        rainbowState %= 360.0;
        final float hue = ((float)(rainbowState / 360.0) < 0.5) ? (-(float)(rainbowState / 360.0)) : ((float)(rainbowState / 360.0));
        final Color color = Color.getHSBColor(hue, 0.7f, 1.0f);
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha).getRGB();
    }
    
    public static Color astolfoRainbow2(final int offset, final float distance, final float speedl) {
        float speed;
        float hue;
        for (speed = 3000.0f, hue = System.currentTimeMillis() % (int)speed + (distance - offset) * speedl; hue > speed; hue -= speed) {}
        hue /= speed;
        if (hue > 0.5) {
            hue = 0.5f - (hue - 0.5f);
        }
        hue += 0.5f;
        return Color.getHSBColor(hue, 0.4f, 1.0f);
    }
    
    public static Color astolfoRainbow(final int offset) {
        float speed;
        float hue;
        for (speed = 3000.0f, hue = (float)(System.currentTimeMillis() % (int)speed + offset); hue > speed; hue -= speed) {}
        hue /= speed;
        if (hue > 0.5) {
            hue = 0.5f - (hue - 0.5f);
        }
        hue += 0.5f;
        return Color.getHSBColor(hue, 0.4f, 1.0f);
    }
    
    public static Color skyRainbow(final int speed, final int index) {
        int angle = (int)((System.currentTimeMillis() / speed + index) % 360L);
        final float hue = angle / 360.0f;
        return Color.getHSBColor(((float)((angle %= (int)360.0) / 360.0) < 0.5) ? (-(float)(angle / 360.0)) : ((float)(angle / 360.0)), 0.5f, 1.0f);
    }
    
    public static int rainbow(final int delay, final double speed) {
        double rainbow = Math.ceil((System.currentTimeMillis() + delay) / speed);
        rainbow %= 360.0;
        return Color.getHSBColor((float)(-(rainbow / 360.0)), 0.9f, 1.0f).getRGB();
    }
    
    public static int getBrightness(final Color color, float brightness) {
        final float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        brightness = 0.5f + 0.5f * brightness;
        hsb[2] = brightness % 2.0f;
        return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
    }
    
    public static void glColor(final Color color) {
        final float red = color.getRed() / 255.0f;
        final float green = color.getGreen() / 255.0f;
        final float blue = color.getBlue() / 255.0f;
        final float alpha = color.getAlpha() / 255.0f;
        GlStateManager.color(red, green, blue, alpha);
    }
    
    public static void glColor(final int hex, final float alpha) {
        final float red = (hex >> 16 & 0xFF) / 255.0f;
        final float green = (hex >> 8 & 0xFF) / 255.0f;
        final float blue = (hex & 0xFF) / 255.0f;
        GlStateManager.color(red, green, blue, alpha / 255.0f);
    }
    
    public static void glColor(final int hex) {
        final float alpha = (hex >> 24 & 0xFF) / 255.0f;
        final float red = (hex >> 16 & 0xFF) / 255.0f;
        final float green = (hex >> 8 & 0xFF) / 255.0f;
        final float blue = (hex & 0xFF) / 255.0f;
        GlStateManager.color(red, green, blue, alpha);
    }
    
    public static Color getColor(final int hex, final int alpha) {
        final float f1 = (hex >> 16 & 0xFF) / 255.0f;
        final float f2 = (hex >> 8 & 0xFF) / 255.0f;
        final float f3 = (hex & 0xFF) / 255.0f;
        return new Color((int)(f1 * 255.0f), (int)(f2 * 255.0f), (int)(f3 * 255.0f), alpha);
    }
    
    public static Color getColor(final Color color, final int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }
    
    public static Color blendColors(final float[] fractions, final Color[] colors, final float progress) {
        if (fractions.length == colors.length) {
            final int[] indices = getFractionIndices(fractions, progress);
            final float[] range = { fractions[indices[0]], fractions[indices[1]] };
            final Color[] colorRange = { colors[indices[0]], colors[indices[1]] };
            final float max = range[1] - range[0];
            final float value = progress - range[0];
            final float weight = value / max;
            return blend(colorRange[0], colorRange[1], 1.0f - weight);
        }
        throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
    }
    
    public static int[] getFractionIndices(final float[] fractions, final float progress) {
        final int[] range = new int[2];
        int startPoint;
        for (startPoint = 0; startPoint < fractions.length && fractions[startPoint] <= progress; ++startPoint) {}
        if (startPoint >= fractions.length) {
            startPoint = fractions.length - 1;
        }
        range[0] = startPoint - 1;
        range[1] = startPoint;
        return range;
    }
    
    public static Color blend(final Color acolor, final Color bcolor, final double ratio) {
        final float r = (float)ratio;
        final float ir = 1.0f - r;
        final float[] rgb1 = new float[3];
        final float[] rgb2 = new float[3];
        acolor.getColorComponents(rgb1);
        bcolor.getColorComponents(rgb2);
        float red = rgb1[0] * r + rgb2[0] * ir;
        float green = rgb1[1] * r + rgb2[1] * ir;
        float blue = rgb1[2] * r + rgb2[2] * ir;
        if (red < 0.0f) {
            red = 0.0f;
        }
        else if (red > 255.0f) {
            red = 255.0f;
        }
        if (green < 0.0f) {
            green = 0.0f;
        }
        else if (green > 255.0f) {
            green = 255.0f;
        }
        if (blue < 0.0f) {
            blue = 0.0f;
        }
        else if (blue > 255.0f) {
            blue = 255.0f;
        }
        Color color = null;
        try {
            color = new Color(red, green, blue);
        }
        catch (IllegalArgumentException ex) {}
        return color;
    }
    
    public static int evaluate(final float fraction, final int startValue, final int endValue) {
        final float startA = (startValue >> 24 & 0xFF) / 255.0f;
        float startR = (startValue >> 16 & 0xFF) / 255.0f;
        float startG = (startValue >> 8 & 0xFF) / 255.0f;
        float startB = (startValue & 0xFF) / 255.0f;
        final float endA = (endValue >> 24 & 0xFF) / 255.0f;
        float endR = (endValue >> 16 & 0xFF) / 255.0f;
        float endG = (endValue >> 8 & 0xFF) / 255.0f;
        float endB = (endValue & 0xFF) / 255.0f;
        startR = (float)Math.pow(startR, 2.2);
        startG = (float)Math.pow(startG, 2.2);
        startB = (float)Math.pow(startB, 2.2);
        endR = (float)Math.pow(endR, 2.2);
        endG = (float)Math.pow(endG, 2.2);
        endB = (float)Math.pow(endB, 2.2);
        float a = MathUtil.lerp(fraction, startA, endA);
        float r = MathUtil.lerp(fraction, startR, endR);
        float g = MathUtil.lerp(fraction, startG, endG);
        float b = MathUtil.lerp(fraction, startB, endB);
        a *= 255.0f;
        r = (float)Math.pow(r, 0.45454545454545453) * 255.0f;
        g = (float)Math.pow(g, 0.45454545454545453) * 255.0f;
        b = (float)Math.pow(b, 0.45454545454545453) * 255.0f;
        return Math.round(a) << 24 | Math.round(r) << 16 | Math.round(g) << 8 | Math.round(b);
    }
    
    public static Color[] getAnalogousColor(final Color color) {
        final Color[] colors = new Color[2];
        final float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        final float degree = 0.083333336f;
        final float newHueAdded = hsb[0] + degree;
        colors[0] = new Color(Color.HSBtoRGB(newHueAdded, hsb[1], hsb[2]));
        final float newHueSubtracted = hsb[0] - degree;
        colors[1] = new Color(Color.HSBtoRGB(newHueSubtracted, hsb[1], hsb[2]));
        return colors;
    }
    
    public static int[] rgbToHsv(final int rgb) {
        final float r = ((rgb & 0xFF0000) >> 16) / 255.0f;
        final float g = ((rgb & 0xFF00) >> 8) / 255.0f;
        final float b = (rgb & 0xFF) / 255.0f;
        final float M = (r > g) ? ((r > b) ? r : b) : ((g > b) ? g : b);
        final float m = (r < g) ? ((r < b) ? r : b) : ((g < b) ? g : b);
        final float c = M - m;
        float h;
        if (M == r) {
            for (h = (g - b) / c; h < 0.0f; h += 6.0f) {}
            h %= 6.0f;
        }
        else if (M == g) {
            h = (b - r) / c + 2.0f;
        }
        else {
            h = (r - g) / c + 4.0f;
        }
        h *= 60.0f;
        final float s = c / M;
        return new int[] { (c == 0.0f) ? -1 : ((int)h), (int)(s * 100.0f), (int)(M * 100.0f) };
    }
    
    public static Color hslToRGB(final float[] hsl) {
        float blue;
        float red;
        float green;
        if (hsl[1] == 0.0f) {
            green = (red = (blue = 1.0f));
        }
        else {
            final float q = (hsl[2] < 0.5) ? (hsl[2] * (1.0f + hsl[1])) : (hsl[2] + hsl[1] - hsl[2] * hsl[1]);
            final float p = 2.0f * hsl[2] - q;
            red = hueToRGB(p, q, hsl[0] + 0.33333334f);
            green = hueToRGB(p, q, hsl[0]);
            blue = hueToRGB(p, q, hsl[0] - 0.33333334f);
        }
        red *= 255.0f;
        green *= 255.0f;
        blue *= 255.0f;
        return new Color((int)red, (int)green, (int)blue);
    }
    
    public static float hueToRGB(final float p, final float q, final float t) {
        float newT = t;
        if (newT < 0.0f) {
            ++newT;
        }
        if (newT > 1.0f) {
            --newT;
        }
        if (newT < 0.16666667f) {
            return p + (q - p) * 6.0f * newT;
        }
        if (newT < 0.5f) {
            return q;
        }
        if (newT < 0.6666667f) {
            return p + (q - p) * (0.6666667f - newT) * 6.0f;
        }
        return p;
    }
    
    public static int hsvToRgb(int hue, final int saturation, final int value) {
        hue %= 360;
        final float s = saturation / 100.0f;
        final float v = value / 100.0f;
        final float c = v * s;
        final float h = hue / 60.0f;
        final float x = c * (1.0f - Math.abs(h % 2.0f - 1.0f));
        float r = 0.0f;
        float g = 0.0f;
        float b = 0.0f;
        switch (hue / 60) {
            case 0: {
                r = c;
                g = x;
                b = 0.0f;
                break;
            }
            case 1: {
                r = x;
                g = c;
                b = 0.0f;
                break;
            }
            case 2: {
                r = 0.0f;
                g = c;
                b = x;
                break;
            }
            case 3: {
                r = 0.0f;
                g = x;
                b = c;
                break;
            }
            case 4: {
                r = x;
                g = 0.0f;
                b = c;
                break;
            }
            case 5: {
                r = c;
                g = 0.0f;
                b = x;
                break;
            }
            default: {
                return 0;
            }
        }
        final float m = v - c;
        return (int)((r + m) * 255.0f) << 16 | (int)((g + m) * 255.0f) << 8 | (int)((b + m) * 255.0f);
    }
    
    public static float[] rgbToHSL(final Color rgb) {
        final float red = rgb.getRed() / 255.0f;
        final float green = rgb.getGreen() / 255.0f;
        final float blue = rgb.getBlue() / 255.0f;
        final float max = Math.max(Math.max(red, green), blue);
        final float min = Math.min(Math.min(red, green), blue);
        final float c = (max + min) / 2.0f;
        final float[] hsl = { c, c, c };
        if (max == min) {
            hsl[0] = (hsl[1] = 0.0f);
        }
        else {
            final float d = max - min;
            hsl[1] = ((hsl[2] > 0.5) ? (d / (2.0f - max - min)) : (d / (max + min)));
            if (max == red) {
                hsl[0] = (green - blue) / d + ((green < blue) ? 6 : 0);
            }
            else if (max == blue) {
                hsl[0] = (blue - red) / d + 2.0f;
            }
            else if (max == green) {
                hsl[0] = (red - green) / d + 4.0f;
            }
            final float[] array = hsl;
            final int n = 0;
            array[n] /= 6.0f;
        }
        return hsl;
    }
    
    public static Color imitateTransparency(final Color backgroundColor, final Color accentColor, final float percentage) {
        return new Color(interpolateColor(backgroundColor, accentColor, 255.0f * percentage / 255.0f));
    }
    
    public static int applyOpacity(final int color, final float opacity) {
        final Color old = new Color(color);
        return applyOpacity(old, opacity).getRGB();
    }
    
    public static Color applyOpacity(final Color color, float opacity) {
        opacity = Math.min(1.0f, Math.max(0.0f, opacity));
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(color.getAlpha() * opacity));
    }
    
    public static Color darker(final Color color, final float FACTOR) {
        return new Color(Math.max((int)(color.getRed() * FACTOR), 0), Math.max((int)(color.getGreen() * FACTOR), 0), Math.max((int)(color.getBlue() * FACTOR), 0), color.getAlpha());
    }
    
    public static Color brighter(final Color color, final float FACTOR) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        final int alpha = color.getAlpha();
        final int i = (int)(1.0 / (1.0 - FACTOR));
        if (r == 0 && g == 0 && b == 0) {
            return new Color(i, i, i, alpha);
        }
        if (r > 0 && r < i) {
            r = i;
        }
        if (g > 0 && g < i) {
            g = i;
        }
        if (b > 0 && b < i) {
            b = i;
        }
        return new Color(Math.min((int)(r / FACTOR), 255), Math.min((int)(g / FACTOR), 255), Math.min((int)(b / FACTOR), 255), alpha);
    }
    
    public static Color averageColor(final BufferedImage bi, final int width, final int height, final int pixelStep) {
        final int[] color = new int[3];
        for (int x = 0; x < width; x += pixelStep) {
            for (int y = 0; y < height; y += pixelStep) {
                final Color pixel = new Color(bi.getRGB(x, y));
                final int[] array = color;
                final int n = 0;
                array[n] += pixel.getRed();
                final int[] array2 = color;
                final int n2 = 1;
                array2[n2] += pixel.getGreen();
                final int[] array3 = color;
                final int n3 = 2;
                array3[n3] += pixel.getBlue();
            }
        }
        final int num = width * height / (pixelStep * pixelStep);
        return new Color(color[0] / num, color[1] / num, color[2] / num);
    }
    
    public static Color rainbow(final int speed, final int index, final float saturation, final float brightness, final float opacity) {
        final int angle = (int)((System.currentTimeMillis() / speed + index) % 360L);
        final float hue = angle / 360.0f;
        final Color color = new Color(Color.HSBtoRGB(hue, saturation, brightness));
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.max(0, Math.min(255, (int)(opacity * 255.0f))));
    }
    
    public static Color interpolateColorsBackAndForth(final int speed, final int index, final Color start, final Color end, final boolean trueColor) {
        int angle = (int)((System.currentTimeMillis() / speed + index) % 360L);
        angle = ((angle >= 180) ? (360 - angle) : angle) * 2;
        return trueColor ? interpolateColorHue(start, end, angle / 360.0f) : interpolateColorC(start, end, angle / 360.0f);
    }
    
    public static int interpolateColor(final Color color1, final Color color2, float amount) {
        amount = Math.min(1.0f, Math.max(0.0f, amount));
        return interpolateColorC(color1, color2, amount).getRGB();
    }
    
    public static int interpolateColor(final int color1, final int color2, float amount) {
        amount = Math.min(1.0f, Math.max(0.0f, amount));
        final Color cColor1 = new Color(color1);
        final Color cColor2 = new Color(color2);
        return interpolateColorC(cColor1, cColor2, amount).getRGB();
    }
    
    public static Color interpolateColorC(final Color color1, final Color color2, float amount) {
        amount = Math.min(1.0f, Math.max(0.0f, amount));
        return new Color(interpolateInt(color1.getRed(), color2.getRed(), amount), interpolateInt(color1.getGreen(), color2.getGreen(), amount), interpolateInt(color1.getBlue(), color2.getBlue(), amount), interpolateInt(color1.getAlpha(), color2.getAlpha(), amount));
    }
    
    public static Color interpolateColorHue(final Color color1, final Color color2, float amount) {
        amount = Math.min(1.0f, Math.max(0.0f, amount));
        final float[] color1HSB = Color.RGBtoHSB(color1.getRed(), color1.getGreen(), color1.getBlue(), null);
        final float[] color2HSB = Color.RGBtoHSB(color2.getRed(), color2.getGreen(), color2.getBlue(), null);
        final Color resultColor = Color.getHSBColor(interpolateFloat(color1HSB[0], color2HSB[0], amount), interpolateFloat(color1HSB[1], color2HSB[1], amount), interpolateFloat(color1HSB[2], color2HSB[2], amount));
        return new Color(resultColor.getRed(), resultColor.getGreen(), resultColor.getBlue(), interpolateInt(color1.getAlpha(), color2.getAlpha(), amount));
    }
    
    public static Color fade(final int speed, final int index, final Color color, final float alpha) {
        final float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        int angle = (int)((System.currentTimeMillis() / speed + index) % 360L);
        angle = ((angle > 180) ? (360 - angle) : angle) + 180;
        final Color colorHSB = new Color(Color.HSBtoRGB(hsb[0], hsb[1], angle / 360.0f));
        return new Color(colorHSB.getRed(), colorHSB.getGreen(), colorHSB.getBlue(), Math.max(0, Math.min(255, (int)(alpha * 255.0f))));
    }
    
    private static float getAnimationEquation(final int index, final int speed) {
        final int angle = (int)((System.currentTimeMillis() / speed + index) % 360L);
        return (((angle > 180) ? (360 - angle) : angle) + 180) / 360.0f;
    }
    
    public static int[] createColorArray(final int color) {
        return new int[] { bitChangeColor(color, 16), bitChangeColor(color, 8), bitChangeColor(color, 0), bitChangeColor(color, 24) };
    }
    
    public static int getOppositeColor(final int color) {
        int R = bitChangeColor(color, 0);
        int G = bitChangeColor(color, 8);
        int B = bitChangeColor(color, 16);
        final int A = bitChangeColor(color, 24);
        R = 255 - R;
        G = 255 - G;
        B = 255 - B;
        return R + (G << 8) + (B << 16) + (A << 24);
    }
    
    private static int bitChangeColor(final int color, final int bitChange) {
        return color >> bitChange & 0xFF;
    }
    
    public static Double interpolate(final double oldValue, final double newValue, final double interpolationValue) {
        return oldValue + (newValue - oldValue) * interpolationValue;
    }
    
    public static float interpolateFloat(final float oldValue, final float newValue, final double interpolationValue) {
        return interpolate(oldValue, newValue, (float)interpolationValue).floatValue();
    }
    
    public static int interpolateInt(final int oldValue, final int newValue, final double interpolationValue) {
        return interpolate(oldValue, newValue, (float)interpolationValue).intValue();
    }
}

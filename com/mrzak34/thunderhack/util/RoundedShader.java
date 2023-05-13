//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import com.mrzak34.thunderhack.util.render.*;
import java.awt.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;

public class RoundedShader
{
    private static final ShaderUtil roundedGradientShader;
    public static ShaderUtil roundedShader;
    public static ShaderUtil roundedOutlineShader;
    
    public static void drawRound(final float x, final float y, final float width, final float height, final float radius, final Color color) {
        drawRound(x, y, width, height, radius, false, color);
    }
    
    public static void drawGradientRound(final float x, final float y, final float width, final float height, final float radius, final Color bottomLeft, final Color topLeft, final Color bottomRight, final Color topRight) {
        GlStateManager.resetColor();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        RoundedShader.roundedGradientShader.init();
        setupRoundedRectUniforms(x, y, width, height, radius, RoundedShader.roundedGradientShader);
        RoundedShader.roundedGradientShader.setUniformf("color1", new float[] { bottomLeft.getRed() / 255.0f, bottomLeft.getGreen() / 255.0f, bottomLeft.getBlue() / 255.0f, bottomLeft.getAlpha() / 255.0f });
        RoundedShader.roundedGradientShader.setUniformf("color2", new float[] { topLeft.getRed() / 255.0f, topLeft.getGreen() / 255.0f, topLeft.getBlue() / 255.0f, topLeft.getAlpha() / 255.0f });
        RoundedShader.roundedGradientShader.setUniformf("color3", new float[] { bottomRight.getRed() / 255.0f, bottomRight.getGreen() / 255.0f, bottomRight.getBlue() / 255.0f, bottomRight.getAlpha() / 255.0f });
        RoundedShader.roundedGradientShader.setUniformf("color4", new float[] { topRight.getRed() / 255.0f, topRight.getGreen() / 255.0f, topRight.getBlue() / 255.0f, topRight.getAlpha() / 255.0f });
        ShaderUtil.drawQuads(x - 1.0f, y - 1.0f, width + 2.0f, height + 2.0f);
        RoundedShader.roundedGradientShader.unload();
        GlStateManager.resetColor();
        GlStateManager.disableBlend();
    }
    
    public static void drawRound(final float x, final float y, final float width, final float height, final float radius, final boolean blur, final Color color) {
        GlStateManager.resetColor();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        RoundedShader.roundedShader.init();
        setupRoundedRectUniforms(x, y, width, height, radius, RoundedShader.roundedShader);
        RoundedShader.roundedShader.setUniformi("blur", new int[] { blur ? 1 : 0 });
        RoundedShader.roundedShader.setUniformf("color", new float[] { color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f });
        ShaderUtil.drawQuads(x - 1.0f, y - 1.0f, width + 2.0f, height + 2.0f);
        RoundedShader.roundedShader.unload();
        GlStateManager.disableBlend();
    }
    
    public static void drawRoundOutline(final float x, final float y, final float width, final float height, final float radius, final float outlineThickness, final Color color, final Color outlineColor) {
        GlStateManager.resetColor();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        RoundedShader.roundedOutlineShader.init();
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        setupRoundedRectUniforms(x, y, width, height, radius, RoundedShader.roundedOutlineShader);
        RoundedShader.roundedOutlineShader.setUniformf("outlineThickness", new float[] { outlineThickness * sr.getScaleFactor() });
        RoundedShader.roundedOutlineShader.setUniformf("color", new float[] { color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f });
        RoundedShader.roundedOutlineShader.setUniformf("outlineColor", new float[] { outlineColor.getRed() / 255.0f, outlineColor.getGreen() / 255.0f, outlineColor.getBlue() / 255.0f, outlineColor.getAlpha() / 255.0f });
        ShaderUtil.drawQuads(x - (2.0f + outlineThickness), y - (2.0f + outlineThickness), width + (4.0f + outlineThickness * 2.0f), height + (4.0f + outlineThickness * 2.0f));
        RoundedShader.roundedOutlineShader.unload();
        GlStateManager.disableBlend();
    }
    
    private static void setupRoundedRectUniforms(final float x, final float y, final float width, final float height, final float radius, final ShaderUtil roundedTexturedShader) {
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        roundedTexturedShader.setUniformf("location", new float[] { x * sr.getScaleFactor(), Util.mc.displayHeight - height * sr.getScaleFactor() - y * sr.getScaleFactor() });
        roundedTexturedShader.setUniformf("rectSize", new float[] { width * sr.getScaleFactor(), height * sr.getScaleFactor() });
        roundedTexturedShader.setUniformf("radius", new float[] { radius * sr.getScaleFactor() });
    }
    
    static {
        roundedGradientShader = new ShaderUtil("roundedRectGradient");
        RoundedShader.roundedShader = new ShaderUtil("roundedRect");
        RoundedShader.roundedOutlineShader = new ShaderUtil("textures/roundrectoutline.frag");
    }
}

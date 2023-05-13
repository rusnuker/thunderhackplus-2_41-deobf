//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.render;

import java.util.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import java.awt.image.*;
import com.mrzak34.thunderhack.util.gaussianblur.*;
import net.minecraft.client.renderer.texture.*;
import java.awt.*;

public class Drawable
{
    private static final HashMap<Integer, Integer> shadowCache;
    
    public static void drawTexture(final ResourceLocation texture, final double x, final double y, final double width, final double height) {
        drawTexture(texture, x, y, width, height, Color.WHITE);
    }
    
    public static void drawTexture(final ResourceLocation texture, final double x, final double y, final double width, final double height, final Color color) {
        Util.mc.getTextureManager().bindTexture(texture);
        RenderUtil.glColor(color);
        final BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(4, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferBuilder.pos(x + width, y, 0.0).tex(1.0, 0.0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferBuilder.pos(x, y, 0.0).tex(0.0, 0.0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferBuilder.pos(x, y + height, 0.0).tex(0.0, 1.0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferBuilder.pos(x, y + height, 0.0).tex(0.0, 1.0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferBuilder.pos(x + width, y + height, 0.0).tex(1.0, 1.0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferBuilder.pos(x + width, y, 0.0).tex(1.0, 0.0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        draw(true);
        GlStateManager.resetColor();
    }
    
    public static void draw(final boolean texture) {
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.disableAlpha();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GL11.glEnable(2848);
        GlStateManager.shadeModel(7425);
        if (texture) {
            GlStateManager.enableTexture2D();
        }
        else {
            GlStateManager.disableTexture2D();
        }
        Tessellator.getInstance().draw();
        GlStateManager.enableAlpha();
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GL11.glDisable(2848);
    }
    
    public static void drawRect(final Rectangle r, final Color color) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(2, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)r.x, (double)(r.y + r.height), 0.0).color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f).endVertex();
        bufferbuilder.pos((double)(r.x + r.width), (double)(r.y + r.height), 0.0).color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f).endVertex();
        bufferbuilder.pos((double)(r.x + r.width), (double)r.y, 0.0).color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f).endVertex();
        bufferbuilder.pos((double)r.x, (double)r.y, 0.0).color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f).endVertex();
        tessellator.draw();
    }
    
    public static void drawRect(final int mode, double left, double top, double right, double bottom, final int color) {
        if (left < right) {
            final double i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            final double j = top;
            top = bottom;
            bottom = j;
        }
        final float f3 = (color >> 24 & 0xFF) / 255.0f;
        final float f4 = (color >> 16 & 0xFF) / 255.0f;
        final float f5 = (color >> 8 & 0xFF) / 255.0f;
        final float f6 = (color & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(f4, f5, f6, f3);
        bufferbuilder.begin(mode, DefaultVertexFormats.POSITION);
        bufferbuilder.pos(left, bottom, 0.0).endVertex();
        bufferbuilder.pos(right, bottom, 0.0).endVertex();
        bufferbuilder.pos(right, top, 0.0).endVertex();
        bufferbuilder.pos(left, top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public static void drawRect(final double left, final double top, final double right, final double bottom, final int color) {
        drawRect(7, left, top, right, bottom, color);
    }
    
    public static void drawRectWH(final double x, final double y, final double width, final double height, final int color) {
        drawRect(x, y, x + width, y + height, color);
    }
    
    public static void horizontalGradient(final double x1, final double y1, final double x2, final double y2, final int startColor, final int endColor) {
        final float f = (startColor >> 24 & 0xFF) / 255.0f;
        final float f2 = (startColor >> 16 & 0xFF) / 255.0f;
        final float f3 = (startColor >> 8 & 0xFF) / 255.0f;
        final float f4 = (startColor & 0xFF) / 255.0f;
        final float f5 = (endColor >> 24 & 0xFF) / 255.0f;
        final float f6 = (endColor >> 16 & 0xFF) / 255.0f;
        final float f7 = (endColor >> 8 & 0xFF) / 255.0f;
        final float f8 = (endColor & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x1, y1, 0.0).color(f2, f3, f4, f).endVertex();
        bufferbuilder.pos(x1, y2, 0.0).color(f2, f3, f4, f).endVertex();
        bufferbuilder.pos(x2, y2, 0.0).color(f6, f7, f8, f5).endVertex();
        bufferbuilder.pos(x2, y1, 0.0).color(f6, f7, f8, f5).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
    
    public static void verticalGradient(final double left, final double top, final double right, final double bottom, final int startColor, final int endColor) {
        final float f = (startColor >> 24 & 0xFF) / 255.0f;
        final float f2 = (startColor >> 16 & 0xFF) / 255.0f;
        final float f3 = (startColor >> 8 & 0xFF) / 255.0f;
        final float f4 = (startColor & 0xFF) / 255.0f;
        final float f5 = (endColor >> 24 & 0xFF) / 255.0f;
        final float f6 = (endColor >> 16 & 0xFF) / 255.0f;
        final float f7 = (endColor >> 8 & 0xFF) / 255.0f;
        final float f8 = (endColor & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(right, top, 0.0).color(f2, f3, f4, f).endVertex();
        bufferbuilder.pos(left, top, 0.0).color(f2, f3, f4, f).endVertex();
        bufferbuilder.pos(left, bottom, 0.0).color(f6, f7, f8, f5).endVertex();
        bufferbuilder.pos(right, bottom, 0.0).color(f6, f7, f8, f5).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
    
    public static boolean isHovered(final double mouseX, final double mouseY, final double x, final double y, final double width, final double height) {
        return mouseX >= x && mouseX - width <= x && mouseY >= y && mouseY - height <= y;
    }
    
    public static void drawBlurredShadow(float x, float y, float width, float height, final int blurRadius, final Color color) {
        GlStateManager.alphaFunc(516, 0.01f);
        width += blurRadius * 2;
        height += blurRadius * 2;
        x -= blurRadius;
        y -= blurRadius;
        final float _X = x - 0.25f;
        final float _Y = y + 0.25f;
        final int identifier = (int)(width * height + width + color.hashCode() * blurRadius + blurRadius);
        final boolean text2d = GL11.glIsEnabled(3553);
        final boolean cface = GL11.glIsEnabled(2884);
        final boolean atest = GL11.glIsEnabled(3008);
        final boolean blend = GL11.glIsEnabled(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2884);
        GL11.glEnable(3008);
        GlStateManager.enableBlend();
        int texId = -1;
        if (Drawable.shadowCache.containsKey(identifier)) {
            texId = Drawable.shadowCache.get(identifier);
            GlStateManager.bindTexture(texId);
        }
        else {
            final BufferedImage original = new BufferedImage((int)width, (int)height, 2);
            final Graphics g = original.getGraphics();
            g.setColor(color);
            g.fillRect(blurRadius, blurRadius, (int)(width - blurRadius * 2), (int)(height - blurRadius * 2));
            g.dispose();
            final GaussianFilter op = new GaussianFilter((float)blurRadius);
            final BufferedImage blurred = op.filter(original, (BufferedImage)null);
            texId = TextureUtil.uploadTextureImageAllocate(TextureUtil.glGenTextures(), blurred, true, false);
            Drawable.shadowCache.put(identifier, texId);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex2f(_X, _Y);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex2f(_X, _Y + height);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex2f(_X + width, _Y + height);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex2f(_X + width, _Y);
        GL11.glEnd();
        GlStateManager.resetColor();
        if (!blend) {
            GlStateManager.disableBlend();
        }
        if (!atest) {
            GL11.glDisable(3008);
        }
        if (!cface) {
            GL11.glEnable(2884);
        }
        if (!text2d) {
            GL11.glDisable(3553);
        }
    }
    
    static {
        shadowCache = new HashMap<Integer, Integer>();
    }
}

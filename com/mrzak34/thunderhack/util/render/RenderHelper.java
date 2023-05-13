//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.render;

import net.minecraft.client.renderer.culling.*;
import java.util.*;
import org.lwjgl.opengl.*;
import java.awt.image.*;
import com.mrzak34.thunderhack.util.gaussianblur.*;
import net.minecraft.client.renderer.texture.*;
import java.awt.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import com.mrzak34.thunderhack.modules.render.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.gui.hud.elements.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;

public class RenderHelper
{
    public static Frustum frustum;
    private static final HashMap<Integer, Integer> shadowCache;
    
    public static void drawBlurredShadow(float x, float y, float width, float height, final int blurRadius, final Color color) {
        BufferedImage original = null;
        GaussianFilter op = null;
        GL11.glPushMatrix();
        GlStateManager.alphaFunc(516, 0.01f);
        final float _X = (x -= blurRadius) - 0.25f;
        final float _Y = (y -= blurRadius) + 0.25f;
        final int identifier = String.valueOf((width += blurRadius * 2) * (height += blurRadius * 2) + width + 1000000000 * blurRadius + blurRadius).hashCode();
        GL11.glEnable(3553);
        GL11.glDisable(2884);
        GL11.glEnable(3008);
        GlStateManager.enableBlend();
        int texId = -1;
        if (RenderHelper.shadowCache.containsKey(identifier)) {
            texId = RenderHelper.shadowCache.get(identifier);
            GlStateManager.bindTexture(texId);
        }
        else {
            if (width <= 0.0f) {
                width = 1.0f;
            }
            if (height <= 0.0f) {
                height = 1.0f;
            }
            if (original == null) {
                original = new BufferedImage((int)width, (int)height, 3);
            }
            final Graphics g = original.getGraphics();
            g.setColor(Color.white);
            g.fillRect(blurRadius, blurRadius, (int)(width - blurRadius * 2), (int)(height - blurRadius * 2));
            g.dispose();
            if (op == null) {
                op = new GaussianFilter((float)blurRadius);
            }
            final BufferedImage blurred = op.filter(original, (BufferedImage)null);
            texId = TextureUtil.uploadTextureImageAllocate(TextureUtil.glGenTextures(), blurred, true, false);
            RenderHelper.shadowCache.put(identifier, texId);
        }
        GlStateManager.color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
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
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.resetColor();
        GL11.glEnable(2884);
        GL11.glPopMatrix();
    }
    
    public static boolean isInViewFrustum(final Entity entity) {
        return isInViewFrustum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck;
    }
    
    private static boolean isInViewFrustum(final AxisAlignedBB bb) {
        final Entity current = Util.mc.getRenderViewEntity();
        if (current != null) {
            RenderHelper.frustum.setPosition(current.posX, current.posY, current.posZ);
        }
        return RenderHelper.frustum.isBoundingBoxInFrustum(bb);
    }
    
    public static void setColor(final int color) {
        GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color & 0xFF), (byte)(color >> 24 & 0xFF));
    }
    
    public static void setColor(final Color color, final float alpha) {
        final float red = color.getRed() / 255.0f;
        final float green = color.getGreen() / 255.0f;
        final float blue = color.getBlue() / 255.0f;
        GlStateManager.color(red, green, blue, alpha);
    }
    
    public static void drawCircle3D(final Entity entity, final double radius, final float partialTicks, final int points, final float width, final int color, final boolean astolfo) {
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glDisable(2929);
        GL11.glLineWidth(width);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2929);
        GL11.glBegin(3);
        final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosX();
        final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosY();
        final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosZ();
        if (!astolfo) {
            setColor(color);
            for (int i = 0; i <= points; ++i) {
                GL11.glVertex3d(x + radius * Math.cos(i * 6.28 / points), y, z + radius * Math.sin(i * 6.28 / points));
            }
        }
        else {
            for (int i = 0; i <= points; ++i) {
                setColor(ItemESP.astolfo2.getColor((double)i));
                GL11.glVertex3d(x + radius * Math.cos(i * 6.28 / points), y, z + radius * Math.sin(i * 6.28 / points));
            }
        }
        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glPopMatrix();
    }
    
    public static void drawEntityBox(final Entity entity, final Color color, final Color color2, final boolean fullBox, final float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.blendFunc(770, 771);
        GL11.glEnable(3042);
        GlStateManager.glLineWidth(2.0f);
        GlStateManager.disableTexture2D();
        GL11.glDisable(2929);
        GlStateManager.depthMask(false);
        final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * Util.mc.getRenderPartialTicks() - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosX();
        final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * Util.mc.getRenderPartialTicks() - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosY();
        final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * Util.mc.getRenderPartialTicks() - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosZ();
        final AxisAlignedBB axisAlignedBB = entity.getEntityBoundingBox();
        final AxisAlignedBB axisAlignedBB2 = new AxisAlignedBB(axisAlignedBB.minX - entity.posX + x - 0.05, axisAlignedBB.minY - entity.posY + y, axisAlignedBB.minZ - entity.posZ + z - 0.05, axisAlignedBB.maxX - entity.posX + x + 0.05, axisAlignedBB.maxY - entity.posY + y + 0.15, axisAlignedBB.maxZ - entity.posZ + z + 0.05);
        GlStateManager.glLineWidth(2.0f);
        GL11.glEnable(2848);
        GlStateManager.color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, alpha);
        if (fullBox) {
            drawColorBox(axisAlignedBB2, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, alpha);
            GlStateManager.color(color2.getRed() / 255.0f, color2.getGreen() / 255.0f, color2.getBlue() / 255.0f, alpha);
        }
        drawSelectionBoundingBox(axisAlignedBB2);
        GlStateManager.glLineWidth(2.0f);
        GlStateManager.enableTexture2D();
        GL11.glEnable(2929);
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static void drawSelectionBoundingBox(final AxisAlignedBB boundingBox) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder builder = tessellator.getBuffer();
        builder.begin(3, DefaultVertexFormats.POSITION);
        builder.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        builder.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        builder.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        builder.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        builder.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        tessellator.draw();
        builder.begin(3, DefaultVertexFormats.POSITION);
        builder.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        builder.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        builder.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        builder.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        builder.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        tessellator.draw();
        builder.begin(1, DefaultVertexFormats.POSITION);
        builder.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        builder.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        builder.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        builder.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        builder.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        builder.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        builder.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        builder.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        tessellator.draw();
    }
    
    public static void drawCircle(final float x, final float y, float start, float end, final float radius, final boolean filled, final Color color) {
        GlStateManager.color(0.0f, 0.0f, 0.0f, 0.0f);
        if (start > end) {
            final float endOffset = end;
            end = start;
            start = endOffset;
        }
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        if (color != null) {
            setColor(color.getRGB());
        }
        GL11.glEnable(2848);
        GL11.glLineWidth(2.0f);
        if (filled) {
            GL11.glBegin(6);
        }
        else {
            GL11.glBegin(3);
        }
        for (float i = end; i >= start; i -= 5.0f) {
            if (color == null) {
                final double stage = (i + 90.0f) / 360.0;
                final int clr = RadarRewrite.astolfo.getColor(stage);
                final int red = clr >> 16 & 0xFF;
                final int green = clr >> 8 & 0xFF;
                final int blue = clr & 0xFF;
                GL11.glColor4f(red / 255.0f, green / 255.0f, blue / 255.0f, 1.0f);
            }
            final float cos = (float)Math.cos(i * 3.141592653589793 / 180.0) * radius;
            final float sin = (float)Math.sin(i * 3.141592653589793 / 180.0) * radius;
            GL11.glVertex2f(x + cos, y + sin);
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public static void drawElipse(final float x, final float y, final float rx, final float ry, float start, float end, final float radius, final Color color, final int stage1, final RadarRewrite.mode2 cmode) {
        GlStateManager.color(0.0f, 0.0f, 0.0f, 0.0f);
        if (start > end) {
            final float endOffset = end;
            end = start;
            start = endOffset;
        }
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glEnable(2848);
        GL11.glLineWidth(2.0f);
        GL11.glBegin(3);
        for (float i = start; i <= end; i += 2.0f) {
            final double stage2 = (i - start) / 360.0f;
            Color clr = null;
            if (cmode == RadarRewrite.mode2.Astolfo) {
                clr = new Color(RadarRewrite.astolfo.getColor(stage2));
            }
            else if (cmode == RadarRewrite.mode2.Rainbow) {
                clr = color;
            }
            else if (cmode == RadarRewrite.mode2.Custom) {
                clr = color;
            }
            else {
                clr = RenderUtil.TwoColoreffect(color, ((ColorSetting)((RadarRewrite)Thunderhack.moduleManager.getModuleByClass((Class)RadarRewrite.class)).cColor2.getValue()).getColorObject(), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + i * ((20.0f - (int)((RadarRewrite)Thunderhack.moduleManager.getModuleByClass((Class)RadarRewrite.class)).colorOffset1.getValue()) / 200.0f));
            }
            final int clr2 = clr.getRGB();
            final int red = clr2 >> 16 & 0xFF;
            final int green = clr2 >> 8 & 0xFF;
            final int blue = clr2 & 0xFF;
            GL11.glColor4f(red / 255.0f, green / 255.0f, blue / 255.0f, 1.0f);
            final float cos = (float)Math.cos(i * 3.141592653589793 / 180.0) * (radius / ry);
            final float sin = (float)Math.sin(i * 3.141592653589793 / 180.0) * (radius / rx);
            GL11.glVertex2f(x + cos, y + sin);
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        if (stage1 != -1) {
            final float cos = (float)Math.cos((start - 15.0f) * 3.141592653589793 / 180.0) * (radius / ry);
            final float sin = (float)Math.sin((start - 15.0f) * 3.141592653589793 / 180.0) * (radius / rx);
            switch (stage1) {
                case 0: {
                    FontRender.drawCentString3("W", x + cos, y + sin, -1);
                    break;
                }
                case 1: {
                    FontRender.drawCentString3("N", x + cos, y + sin, -1);
                    break;
                }
                case 2: {
                    FontRender.drawCentString3("E", x + cos, y + sin, -1);
                    break;
                }
                case 3: {
                    FontRender.drawCentString3("S", x + cos, y + sin, -1);
                    break;
                }
            }
        }
    }
    
    public static void drawCircle(final float x, final float y, final float radius, final boolean filled, final Color color) {
        drawCircle(x, y, 0.0f, 360.0f, radius, filled, color);
    }
    
    public static void drawEllipsCompas(final int yaw, final float x, final float y, final float x2, final float y2, final float radius, final Color color, final boolean Dir, final RadarRewrite.mode2 mode) {
        if (Dir) {
            drawElipse(x, y, x2, y2, (float)(15 + yaw), (float)(75 + yaw), radius, color, 0, mode);
            drawElipse(x, y, x2, y2, (float)(105 + yaw), (float)(165 + yaw), radius, color, 1, mode);
            drawElipse(x, y, x2, y2, (float)(195 + yaw), (float)(255 + yaw), radius, color, 2, mode);
            drawElipse(x, y, x2, y2, (float)(285 + yaw), (float)(345 + yaw), radius, color, 3, mode);
        }
        else {
            drawElipse(x, y, x2, y2, (float)(15 + yaw), (float)(75 + yaw), radius, color, -1, mode);
            drawElipse(x, y, x2, y2, (float)(105 + yaw), (float)(165 + yaw), radius, color, -1, mode);
            drawElipse(x, y, x2, y2, (float)(195 + yaw), (float)(255 + yaw), radius, color, -1, mode);
            drawElipse(x, y, x2, y2, (float)(285 + yaw), (float)(345 + yaw), radius, color, -1, mode);
        }
    }
    
    public static void drawColorBox(final AxisAlignedBB axisalignedbb, final float red, final float green, final float blue, final float alpha) {
        final Tessellator ts = Tessellator.getInstance();
        final BufferBuilder buffer = ts.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
    }
    
    static {
        RenderHelper.frustum = new Frustum();
        shadowCache = new HashMap<Integer, Integer>();
    }
}

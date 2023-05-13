//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.render;

import com.mrzak34.thunderhack.modules.combat.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.vertex.*;
import javax.vecmath.*;
import org.lwjgl.util.glu.*;
import org.lwjgl.opengl.*;
import java.nio.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.client.gui.*;
import com.mrzak34.thunderhack.util.math.*;
import java.text.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import java.awt.image.*;
import com.mrzak34.thunderhack.util.gaussianblur.*;
import net.minecraft.client.renderer.texture.*;
import java.awt.*;
import net.minecraft.client.renderer.culling.*;

public class RenderUtil implements Util
{
    public static RenderItem itemRender;
    public static ICamera camera;
    public static Tessellator tessellator;
    public static BufferBuilder bufferbuilder;
    private static final HashMap<Integer, Integer> shadowCache;
    public static long delta;
    
    public static double interpolate(final double current, final double old, final double scale) {
        return old + (current - old) * scale;
    }
    
    public static double interpolate(final double current, final double old) {
        return old + (current - old) * RenderUtil.mc.getRenderPartialTicks();
    }
    
    public static void bindTexture(final int texture) {
        GL11.glBindTexture(3553, texture);
    }
    
    public static void renderEntity(final BackTrack.Box entity, final ModelBase modelBase, final float limbSwing, final float limbSwingAmount, final float netHeadYaw, final float headPitch, final EntityLivingBase entityIn, final Color color1) {
        final boolean texture = GL11.glIsEnabled(3553);
        final boolean blend = GL11.glIsEnabled(3042);
        final boolean hz = GL11.glIsEnabled(2848);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GlStateManager.color(color1.getRed() / 255.0f, color1.getGreen() / 255.0f, color1.getBlue() / 255.0f, color1.getAlpha() / 255.0f);
        GL11.glPolygonMode(1032, 6914);
        if (modelBase instanceof ModelPlayer) {
            final ModelPlayer modelPlayer = (ModelPlayer)modelBase;
            modelPlayer.bipedBodyWear.showModel = false;
            modelPlayer.bipedLeftLegwear.showModel = false;
            modelPlayer.bipedRightLegwear.showModel = false;
            modelPlayer.bipedLeftArmwear.showModel = false;
            modelPlayer.bipedRightArmwear.showModel = false;
            modelPlayer.bipedHeadwear.showModel = true;
            modelPlayer.bipedHead.showModel = false;
        }
        final float partialTicks = RenderUtil.mc.getRenderPartialTicks();
        final double x = entity.getPosition().x - RenderUtil.mc.getRenderManager().viewerPosX;
        final double y = entity.getPosition().y - RenderUtil.mc.getRenderManager().viewerPosY;
        final double z = entity.getPosition().z - RenderUtil.mc.getRenderManager().viewerPosZ;
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.rotate(180.0f - entity.getYaw(), 0.0f, 1.0f, 0.0f);
        final float f4 = prepareScale(1.0f);
        final float yaw = entity.getYaw();
        final boolean alpha = GL11.glIsEnabled(3008);
        GlStateManager.enableAlpha();
        modelBase.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTicks);
        modelBase.setRotationAngles(limbSwing, limbSwingAmount, 0.0f, yaw, entity.getPitch(), f4, (Entity)entityIn);
        modelBase.render((Entity)entityIn, limbSwing, limbSwingAmount, 0.0f, yaw, entity.getPitch(), f4);
        if (!alpha) {
            GlStateManager.disableAlpha();
        }
        GlStateManager.popMatrix();
        GlStateManager.enableTexture2D();
        if (!hz) {
            GL11.glDisable(2848);
        }
        if (texture) {
            GlStateManager.enableTexture2D();
        }
        if (!blend) {
            GlStateManager.disableBlend();
        }
    }
    
    private static float prepareScale(final float scale) {
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0f, -1.0f, 1.0f);
        final double widthX = 0.6000000238418579;
        final double widthZ = 0.6000000238418579;
        GlStateManager.scale(scale + widthX, (double)(scale * 1.8f), scale + widthZ);
        final float f = 0.0625f;
        GlStateManager.translate(0.0f, -1.501f, 0.0f);
        return f;
    }
    
    public static void drawBoundingBox(final BackTrack.Box box, final double width, final Color color) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.glLineWidth((float)width);
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        colorVertex(box.getPosition().x - 0.3, box.getPosition().y, box.getPosition().z - 0.3, color, color.getAlpha(), bufferbuilder);
        colorVertex(box.getPosition().x - 0.3, box.getPosition().y, box.getPosition().z + 0.3, color, color.getAlpha(), bufferbuilder);
        colorVertex(box.getPosition().x + 0.3, box.getPosition().y, box.getPosition().z + 0.3, color, color.getAlpha(), bufferbuilder);
        colorVertex(box.getPosition().x + 0.3, box.getPosition().y, box.getPosition().z - 0.3, color, color.getAlpha(), bufferbuilder);
        colorVertex(box.getPosition().x - 0.3, box.getPosition().y, box.getPosition().z - 0.3, color, color.getAlpha(), bufferbuilder);
        colorVertex(box.getPosition().x - 0.3, box.getPosition().y + 1.7999999523162842, box.getPosition().z - 0.3, color, color.getAlpha(), bufferbuilder);
        colorVertex(box.getPosition().x - 0.3, box.getPosition().y + 1.7999999523162842, box.getPosition().z + 0.3, color, color.getAlpha(), bufferbuilder);
        colorVertex(box.getPosition().x - 0.3, box.getPosition().y, box.getPosition().z + 0.3, color, color.getAlpha(), bufferbuilder);
        colorVertex(box.getPosition().x + 0.3, box.getPosition().y, box.getPosition().z + 0.3, color, color.getAlpha(), bufferbuilder);
        colorVertex(box.getPosition().x + 0.3, box.getPosition().y + 1.7999999523162842, box.getPosition().z + 0.3, color, color.getAlpha(), bufferbuilder);
        colorVertex(box.getPosition().x - 0.3, box.getPosition().y + 1.7999999523162842, box.getPosition().z + 0.3, color, color.getAlpha(), bufferbuilder);
        colorVertex(box.getPosition().x + 0.3, box.getPosition().y + 1.7999999523162842, box.getPosition().z + 0.3, color, color.getAlpha(), bufferbuilder);
        colorVertex(box.getPosition().x + 0.3, box.getPosition().y + 1.7999999523162842, box.getPosition().z - 0.3, color, color.getAlpha(), bufferbuilder);
        colorVertex(box.getPosition().x + 0.3, box.getPosition().y, box.getPosition().z - 0.3, color, color.getAlpha(), bufferbuilder);
        colorVertex(box.getPosition().x + 0.3, box.getPosition().y + 1.7999999523162842, box.getPosition().z - 0.3, color, color.getAlpha(), bufferbuilder);
        colorVertex(box.getPosition().x - 0.3, box.getPosition().y + 1.7999999523162842, box.getPosition().z - 0.3, color, color.getAlpha(), bufferbuilder);
        tessellator.draw();
    }
    
    private static void colorVertex(final double x, final double y, final double z, final Color color, final int alpha, final BufferBuilder bufferbuilder) {
        bufferbuilder.pos(x - RenderUtil.mc.getRenderManager().viewerPosX, y - RenderUtil.mc.getRenderManager().viewerPosY, z - RenderUtil.mc.getRenderManager().viewerPosZ).color(color.getRed(), color.getGreen(), color.getBlue(), alpha).endVertex();
    }
    
    public static Vector3d vectorTo2D(final int scaleFactor, final double x, final double y, final double z) {
        final float xPos = (float)x;
        final float yPos = (float)y;
        final float zPos = (float)z;
        final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
        final FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
        final FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
        final FloatBuffer vector = GLAllocation.createDirectFloatBuffer(4);
        GL11.glGetFloat(2982, modelview);
        GL11.glGetFloat(2983, projection);
        GL11.glGetInteger(2978, viewport);
        if (GLU.gluProject(xPos, yPos, zPos, modelview, projection, viewport, vector)) {
            return new Vector3d((double)(vector.get(0) / scaleFactor), (double)((Display.getHeight() - vector.get(1)) / scaleFactor), (double)vector.get(2));
        }
        return null;
    }
    
    public static float scrollAnimate(final float endPoint, final float current, float speed) {
        final boolean shouldContinueAnimation = endPoint > current;
        if (speed < 0.0f) {
            speed = 0.0f;
        }
        else if (speed > 1.0f) {
            speed = 1.0f;
        }
        final float dif = Math.max(endPoint, current) - Math.min(endPoint, current);
        final float factor = dif * speed;
        return current + (shouldContinueAnimation ? factor : (-factor));
    }
    
    public static void glBillboard(final float x, final float y, final float z) {
        final float scale = 0.02666667f;
        GlStateManager.translate(x - ((IRenderManager)RenderUtil.mc.getRenderManager()).getRenderPosX(), y - ((IRenderManager)RenderUtil.mc.getRenderManager()).getRenderPosY(), z - ((IRenderManager)RenderUtil.mc.getRenderManager()).getRenderPosZ());
        GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-Minecraft.getMinecraft().player.rotationYaw, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(Minecraft.getMinecraft().player.rotationPitch, (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-scale, -scale, scale);
    }
    
    public static void glBillboardDistanceScaled(final float x, final float y, final float z, final EntityPlayer player, final float scale) {
        glBillboard(x, y, z);
        final int distance = (int)player.getDistance((double)x, (double)y, (double)z);
        float scaleDistance = distance / 2.0f / (2.0f + (2.0f - scale));
        if (scaleDistance < 1.0f) {
            scaleDistance = 1.0f;
        }
        GlStateManager.scale(scaleDistance, scaleDistance, scaleDistance);
    }
    
    public static void scale() {
        switch (RenderUtil.mc.gameSettings.guiScale) {
            case 0: {
                GlStateManager.scale(0.5, 0.5, 0.5);
                break;
            }
            case 1: {
                GlStateManager.scale(2.0f, 2.0f, 2.0f);
                break;
            }
            case 3: {
                GlStateManager.scale(0.6666666666666667, 0.6666666666666667, 0.6666666666666667);
                break;
            }
        }
    }
    
    public static float lerp(final float a, final float b, final float f) {
        return a + f * (b - a);
    }
    
    public static Color TwoColoreffect(final Color cl1, final Color cl2, final double speed) {
        final double thing = speed / 4.0 % 1.0;
        final float val = MathHelper.clamp((float)Math.sin(18.84955592153876 * thing) / 2.0f + 0.5f, 0.0f, 1.0f);
        return new Color(lerp(cl1.getRed() / 255.0f, cl2.getRed() / 255.0f, val), lerp(cl1.getGreen() / 255.0f, cl2.getGreen() / 255.0f, val), lerp(cl1.getBlue() / 255.0f, cl2.getBlue() / 255.0f, val));
    }
    
    public static void drawFilledCircleNoGL(final int x, final int y, final double r, final int c, final int quality) {
        final float f = (c >> 24 & 0xFF) / 255.0f;
        final float f2 = (c >> 16 & 0xFF) / 255.0f;
        final float f3 = (c >> 8 & 0xFF) / 255.0f;
        final float f4 = (c & 0xFF) / 255.0f;
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glBegin(6);
        for (int i = 0; i <= 360 / quality; ++i) {
            final double x2 = Math.sin(i * quality * 3.141592653589793 / 180.0) * r;
            final double y2 = Math.cos(i * quality * 3.141592653589793 / 180.0) * r;
            GL11.glVertex2d(x + x2, y + y2);
        }
        GL11.glEnd();
    }
    
    public static void glColor(final int hex) {
        final float alpha = (hex >> 24 & 0xFF) / 255.0f;
        final float red = (hex >> 16 & 0xFF) / 255.0f;
        final float green = (hex >> 8 & 0xFF) / 255.0f;
        final float blue = (hex & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }
    
    public static void glColor(final Color color) {
        final float red = color.getRed() / 255.0f;
        final float green = color.getGreen() / 255.0f;
        final float blue = color.getBlue() / 255.0f;
        final float alpha = color.getAlpha() / 255.0f;
        GlStateManager.color(red, green, blue, alpha);
    }
    
    public static void drawRect(final float x, final float y, final float w, final float h, final int color) {
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferBuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(red, green, blue, alpha);
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferBuilder.pos((double)x, (double)h, 0.0).endVertex();
        bufferBuilder.pos((double)w, (double)h, 0.0).endVertex();
        bufferBuilder.pos((double)w, (double)y, 0.0).endVertex();
        bufferBuilder.pos((double)x, (double)y, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public static void drawOutlineRect(final float x, final float y, final float w, final float h, final float lineWidth, final int color) {
        final float right = x + w;
        final float bottom = y + h;
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferBuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(red, green, blue, alpha);
        GL11.glEnable(2848);
        GlStateManager.glLineWidth(lineWidth);
        bufferBuilder.begin(1, DefaultVertexFormats.POSITION);
        bufferBuilder.pos((double)x, (double)bottom, 0.0).endVertex();
        bufferBuilder.pos((double)right, (double)bottom, 0.0).endVertex();
        bufferBuilder.pos((double)right, (double)bottom, 0.0).endVertex();
        bufferBuilder.pos((double)right, (double)y, 0.0).endVertex();
        bufferBuilder.pos((double)right, (double)y, 0.0).endVertex();
        bufferBuilder.pos((double)x, (double)y, 0.0).endVertex();
        bufferBuilder.pos((double)x, (double)y, 0.0).endVertex();
        bufferBuilder.pos((double)x, (double)bottom, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public static void draw2DGradientRect(final float left, final float top, final float right, final float bottom, final int leftBottomColor, final int leftTopColor, final int rightBottomColor, final int rightTopColor) {
        final float lba = (leftBottomColor >> 24 & 0xFF) / 255.0f;
        final float lbr = (leftBottomColor >> 16 & 0xFF) / 255.0f;
        final float lbg = (leftBottomColor >> 8 & 0xFF) / 255.0f;
        final float lbb = (leftBottomColor & 0xFF) / 255.0f;
        final float rba = (rightBottomColor >> 24 & 0xFF) / 255.0f;
        final float rbr = (rightBottomColor >> 16 & 0xFF) / 255.0f;
        final float rbg = (rightBottomColor >> 8 & 0xFF) / 255.0f;
        final float rbb = (rightBottomColor & 0xFF) / 255.0f;
        final float lta = (leftTopColor >> 24 & 0xFF) / 255.0f;
        final float ltr = (leftTopColor >> 16 & 0xFF) / 255.0f;
        final float ltg = (leftTopColor >> 8 & 0xFF) / 255.0f;
        final float ltb = (leftTopColor & 0xFF) / 255.0f;
        final float rta = (rightTopColor >> 24 & 0xFF) / 255.0f;
        final float rtr = (rightTopColor >> 16 & 0xFF) / 255.0f;
        final float rtg = (rightTopColor >> 8 & 0xFF) / 255.0f;
        final float rtb = (rightTopColor & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)right, (double)top, 0.0).color(rtr, rtg, rtb, rta).endVertex();
        bufferbuilder.pos((double)left, (double)top, 0.0).color(ltr, ltg, ltb, lta).endVertex();
        bufferbuilder.pos((double)left, (double)bottom, 0.0).color(lbr, lbg, lbb, lba).endVertex();
        bufferbuilder.pos((double)right, (double)bottom, 0.0).color(rbr, rbg, rbb, rba).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
    
    public static void draw1DGradientRect(final float left, final float top, final float right, final float bottom, final int leftColor, final int rightColor) {
        final float la = (leftColor >> 24 & 0xFF) / 255.0f;
        final float lr = (leftColor >> 16 & 0xFF) / 255.0f;
        final float lg = (leftColor >> 8 & 0xFF) / 255.0f;
        final float lb = (leftColor & 0xFF) / 255.0f;
        final float ra = (rightColor >> 24 & 0xFF) / 255.0f;
        final float rr = (rightColor >> 16 & 0xFF) / 255.0f;
        final float rg = (rightColor >> 8 & 0xFF) / 255.0f;
        final float rb = (rightColor & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)right, (double)top, 0.0).color(rr, rg, rb, ra).endVertex();
        bufferbuilder.pos((double)left, (double)top, 0.0).color(lr, lg, lb, la).endVertex();
        bufferbuilder.pos((double)left, (double)bottom, 0.0).color(lr, lg, lb, la).endVertex();
        bufferbuilder.pos((double)right, (double)bottom, 0.0).color(rr, rg, rb, ra).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
    
    public static void beginRender() {
        GL11.glBlendFunc(770, 771);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.enableBlend();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
    }
    
    public static void endRender() {
        GlStateManager.enableCull();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
    }
    
    public static void colorflux(final int color) {
        final float f = (color >> 24 & 0xFF) / 255.0f;
        final float f2 = (color >> 16 & 0xFF) / 255.0f;
        final float f3 = (color >> 8 & 0xFF) / 255.0f;
        final float f4 = (color & 0xFF) / 255.0f;
        GL11.glColor4f(f2, f3, f4, f);
    }
    
    public static void renderCrosses(final BlockPos pos, final Color color, final float lineWidth) {
        final AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - Util.mc.getRenderManager().viewerPosX, pos.getY() - Util.mc.getRenderManager().viewerPosY, pos.getZ() - Util.mc.getRenderManager().viewerPosZ, pos.getX() + 1 - Util.mc.getRenderManager().viewerPosX, pos.getY() + 1 - Util.mc.getRenderManager().viewerPosY, pos.getZ() + 1 - Util.mc.getRenderManager().viewerPosZ);
        RenderUtil.camera.setPosition(Objects.requireNonNull(Util.mc.getRenderViewEntity()).posX, Util.mc.getRenderViewEntity().posY, Util.mc.getRenderViewEntity().posZ);
        if (RenderUtil.camera.isBoundingBoxInFrustum(new AxisAlignedBB(pos))) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.disableDepth();
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            GL11.glLineWidth(lineWidth);
            renderCrosses(bb, color);
            GL11.glDisable(2848);
            GlStateManager.depthMask(true);
            GlStateManager.enableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }
    
    public static void renderCrosses(final AxisAlignedBB bb, final Color color) {
        final int hex = color.getRGB();
        final float red = (hex >> 16 & 0xFF) / 255.0f;
        final float green = (hex >> 8 & 0xFF) / 255.0f;
        final float blue = (hex & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, 1.0f).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, 1.0f).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, 1.0f).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, 1.0f).endVertex();
        tessellator.draw();
    }
    
    public static void blockEspFrame(final BlockPos blockPos, final double red, final double green, final double blue) {
        final double d = blockPos.getX();
        Minecraft.getMinecraft().getRenderManager();
        final double x = d - ((IRenderManager)RenderUtil.mc.getRenderManager()).getRenderPosX();
        final double d2 = blockPos.getY();
        Minecraft.getMinecraft().getRenderManager();
        final double y = d2 - ((IRenderManager)RenderUtil.mc.getRenderManager()).getRenderPosY();
        final double d3 = blockPos.getZ();
        Minecraft.getMinecraft().getRenderManager();
        final double z = d3 - ((IRenderManager)RenderUtil.mc.getRenderManager()).getRenderPosZ();
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(1.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4d(red, green, blue, 0.5);
        drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }
    
    public static void rectangleBordered(final double x, final double y, final double x1, final double y1, final double width, final int internalColor, final int borderColor) {
        drawRect((float)(x + width), (float)(y + width), (float)(x1 - width), (float)(y1 - width), internalColor);
        drawRect((float)(x + width), (float)y, (float)(x1 - width), (float)(y + width), borderColor);
        drawRect((float)x, (float)y, (float)(x + width), (float)y1, borderColor);
        drawRect((float)(x1 - width), (float)y, (float)x1, (float)y1, borderColor);
        drawRect((float)(x + width), (float)(y1 - width), (float)(x1 - width), (float)y1, borderColor);
    }
    
    public static void rotationHelper(final float xAngle, final float yAngle, final float zAngle) {
        GlStateManager.rotate(yAngle, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(zAngle, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(xAngle, 1.0f, 0.0f, 0.0f);
    }
    
    public static AxisAlignedBB interpolateAxis(final AxisAlignedBB bb) {
        return new AxisAlignedBB(bb.minX - RenderUtil.mc.getRenderManager().viewerPosX, bb.minY - RenderUtil.mc.getRenderManager().viewerPosY, bb.minZ - RenderUtil.mc.getRenderManager().viewerPosZ, bb.maxX - RenderUtil.mc.getRenderManager().viewerPosX, bb.maxY - RenderUtil.mc.getRenderManager().viewerPosY, bb.maxZ - RenderUtil.mc.getRenderManager().viewerPosZ);
    }
    
    public static void drawTexturedRect(final int x, final int y, final int textureX, final int textureY, final int width, final int height, final int zLevel) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder BufferBuilder2 = tessellator.getBuffer();
        BufferBuilder2.begin(7, DefaultVertexFormats.POSITION_TEX);
        BufferBuilder2.pos((double)x, (double)(y + height), (double)zLevel).tex((double)(textureX * 0.00390625f), (double)((textureY + height) * 0.00390625f)).endVertex();
        BufferBuilder2.pos((double)(x + width), (double)(y + height), (double)zLevel).tex((double)((textureX + width) * 0.00390625f), (double)((textureY + height) * 0.00390625f)).endVertex();
        BufferBuilder2.pos((double)(x + width), (double)y, (double)zLevel).tex((double)((textureX + width) * 0.00390625f), (double)(textureY * 0.00390625f)).endVertex();
        BufferBuilder2.pos((double)x, (double)y, (double)zLevel).tex((double)(textureX * 0.00390625f), (double)(textureY * 0.00390625f)).endVertex();
        tessellator.draw();
    }
    
    public static void drawBoxESP(final BlockPos pos, final Color color, final boolean secondC, final Color secondColor, final float lineWidth, final boolean outline, final boolean box, final int boxAlpha, final boolean air, final int mode) {
        if (box) {
            drawBox(pos, new Color(color.getRed(), color.getGreen(), color.getBlue(), boxAlpha), mode);
        }
        if (outline) {
            drawBlockOutline(pos, secondC ? secondColor : color, lineWidth, air, mode);
        }
    }
    
    public static void glScissor(final float x, final float y, final float x1, final float y1, final ScaledResolution sr) {
        GL11.glScissor((int)(x * sr.getScaleFactor()), (int)(RenderUtil.mc.displayHeight - y1 * sr.getScaleFactor()), (int)((x1 - x) * sr.getScaleFactor()), (int)((y1 - y) * sr.getScaleFactor()));
    }
    
    public static void glScissor(final float x, final float y, final float x1, final float y1, final ScaledResolution sr, final double animation_factor) {
        final float h = y + y1;
        final float h2 = (float)(h * (1.0 - MathUtil.clamp(animation_factor, 0.0, 1.002500057220459)));
        final float x2 = x;
        final float y2 = y + h2;
        float x3 = x1;
        float y3 = y1 - h2;
        if (x3 < x2) {
            x3 = x2;
        }
        if (y3 < y2) {
            y3 = y2;
        }
        glScissor(x2, y2, x3, y3, sr);
    }
    
    public static Color blend(final Color color1, final Color color2, final double ratio) {
        final float r = (float)ratio;
        final float ir = 1.0f - r;
        final float[] rgb1 = new float[3];
        final float[] rgb2 = new float[3];
        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);
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
        Color color3 = null;
        try {
            color3 = new Color(red, green, blue);
        }
        catch (IllegalArgumentException exp) {
            NumberFormat.getNumberInstance();
        }
        return color3;
    }
    
    public static void drawSmoothRect(final float left, final float top, final float right, final float bottom, final int color) {
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        drawRect(left, top, right, bottom, color);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        drawRect(left * 2.0f - 1.0f, top * 2.0f, left * 2.0f, bottom * 2.0f - 1.0f, color);
        drawRect(left * 2.0f, top * 2.0f - 1.0f, right * 2.0f, top * 2.0f, color);
        drawRect(right * 2.0f, top * 2.0f, right * 2.0f + 1.0f, bottom * 2.0f - 1.0f, color);
        drawRect(left * 2.0f, bottom * 2.0f - 1.0f, right * 2.0f, bottom * 2.0f, color);
        GL11.glDisable(3042);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
    
    public static void drawBox(final BlockPos pos, final Color color, final int mode) {
        final IBlockState iblockstate = RenderUtil.mc.world.getBlockState(pos);
        final Vec3d interp = EntityUtil.interpolateEntity(RenderUtil.mc.getRenderViewEntity(), RenderUtil.mc.getRenderPartialTicks());
        AxisAlignedBB bb = null;
        switch (mode) {
            case 0: {
                bb = iblockstate.getSelectedBoundingBox((World)RenderUtil.mc.world, pos).grow(0.0020000000949949026).offset(-interp.x, -interp.y, -interp.z);
                break;
            }
            case 1: {
                bb = iblockstate.getSelectedBoundingBox((World)RenderUtil.mc.world, pos).grow(0.0020000000949949026).expand(0.9, 0.0, 0.0).offset(-interp.x, -interp.y, -interp.z);
                break;
            }
            case 2: {
                bb = iblockstate.getSelectedBoundingBox((World)RenderUtil.mc.world, pos).grow(0.0020000000949949026).expand(-0.9, 0.0, 0.0).offset(-interp.x, -interp.y, -interp.z);
                break;
            }
            case 3: {
                bb = iblockstate.getSelectedBoundingBox((World)RenderUtil.mc.world, pos).grow(0.0020000000949949026).expand(0.0, 0.0, 0.9).offset(-interp.x, -interp.y, -interp.z);
                break;
            }
            case 4: {
                bb = iblockstate.getSelectedBoundingBox((World)RenderUtil.mc.world, pos).grow(0.0020000000949949026).expand(0.0, 0.0, -0.9).offset(-interp.x, -interp.y, -interp.z);
                break;
            }
        }
        RenderUtil.camera.setPosition(Objects.requireNonNull(RenderUtil.mc.getRenderViewEntity()).posX, RenderUtil.mc.getRenderViewEntity().posY, RenderUtil.mc.getRenderViewEntity().posZ);
        if (RenderUtil.camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + RenderUtil.mc.getRenderManager().viewerPosX, bb.minY + RenderUtil.mc.getRenderManager().viewerPosY, bb.minZ + RenderUtil.mc.getRenderManager().viewerPosZ, bb.maxX + RenderUtil.mc.getRenderManager().viewerPosX, bb.maxY + RenderUtil.mc.getRenderManager().viewerPosY, bb.maxZ + RenderUtil.mc.getRenderManager().viewerPosZ))) {
            GlStateManager.pushMatrix();
            final boolean texture = GL11.glIsEnabled(3553);
            final boolean blend = GL11.glIsEnabled(3042);
            final boolean hz = GL11.glIsEnabled(2848);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.disableTexture2D();
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            RenderGlobal.renderFilledBox(bb, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / ((mode == 0) ? 255.0f : 510.0f));
            if (!hz) {
                GL11.glDisable(2848);
            }
            if (texture) {
                GlStateManager.enableTexture2D();
            }
            if (!blend) {
                GlStateManager.disableBlend();
            }
            GlStateManager.popMatrix();
        }
    }
    
    public static void drawBlockOutline(final BlockPos pos, final Color color, final float linewidth, final boolean air, final int mode) {
        final IBlockState iblockstate = RenderUtil.mc.world.getBlockState(pos);
        if ((air || iblockstate.getMaterial() != Material.AIR) && RenderUtil.mc.world.getWorldBorder().contains(pos)) {
            assert RenderUtil.mc.getRenderViewEntity() != null;
            final Vec3d interp = EntityUtil.interpolateEntity(RenderUtil.mc.getRenderViewEntity(), RenderUtil.mc.getRenderPartialTicks());
            drawBlockOutline(iblockstate.getSelectedBoundingBox((World)RenderUtil.mc.world, pos).grow(0.0020000000949949026).offset(-interp.x, -interp.y, -interp.z), color, linewidth);
        }
    }
    
    public static void drawBlockOutline(final AxisAlignedBB bb, final Color color, final float linewidth) {
        final float red = color.getRed() / 255.0f;
        final float green = color.getGreen() / 255.0f;
        final float blue = color.getBlue() / 255.0f;
        final float alpha = color.getAlpha() / 255.0f;
        GlStateManager.pushMatrix();
        final boolean texture = GL11.glIsEnabled(3553);
        final boolean blend = GL11.glIsEnabled(3042);
        final boolean DEPTH = GL11.glIsEnabled(2929);
        final boolean HZ = GL11.glIsEnabled(2848);
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(linewidth);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        if (!HZ) {
            GL11.glDisable(2848);
        }
        if (DEPTH) {
            GlStateManager.depthMask(true);
            GlStateManager.enableDepth();
        }
        if (texture) {
            GlStateManager.enableTexture2D();
        }
        if (blend) {
            GlStateManager.disableBlend();
        }
        GlStateManager.popMatrix();
    }
    
    public static void blockEsp(final BlockPos blockPos, final Color c, final double length, final double length2) {
        final double x = blockPos.getX() - ((IRenderManager)RenderUtil.mc.getRenderManager()).getRenderPosX();
        final double y = blockPos.getY() - ((IRenderManager)RenderUtil.mc.getRenderManager()).getRenderPosY();
        final double z = blockPos.getZ() - ((IRenderManager)RenderUtil.mc.getRenderManager()).getRenderPosZ();
        GL11.glPushMatrix();
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4d((double)(c.getRed() / 255.0f), (double)(c.getGreen() / 255.0f), (double)(c.getBlue() / 255.0f), 0.25);
        drawColorBox(new AxisAlignedBB(x, y, z, x + length2, y + 1.0, z + length), 0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glColor4d(0.0, 0.0, 0.0, 0.5);
        drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + length2, y + 1.0, z + length));
        GL11.glLineWidth(2.0f);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void drawRect2(double left, double top, double right, double bottom, final int color) {
        GlStateManager.pushMatrix();
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
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos(left, bottom, 0.0).endVertex();
        bufferbuilder.pos(right, bottom, 0.0).endVertex();
        bufferbuilder.pos(right, top, 0.0).endVertex();
        bufferbuilder.pos(left, top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static void drawColorBox(final AxisAlignedBB axisalignedbb, final float red, final float green, final float blue, final float alpha) {
        final Tessellator ts = Tessellator.getInstance();
        final BufferBuilder vb = ts.getBuffer();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
    }
    
    public static void drawSelectionBoundingBox(final AxisAlignedBB boundingBox) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        tessellator.draw();
        vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        tessellator.draw();
        vertexbuffer.begin(1, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        tessellator.draw();
    }
    
    public static void glEnd() {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
    
    public static AxisAlignedBB getBoundingBox(final BlockPos blockPos) {
        return RenderUtil.mc.world.getBlockState(blockPos).getBoundingBox((IBlockAccess)RenderUtil.mc.world, blockPos).offset(blockPos);
    }
    
    public static void drawFilledBox(final AxisAlignedBB bb, final int color) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static void setColor(final Color color) {
        GL11.glColor4d(color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0, color.getAlpha() / 255.0);
    }
    
    public static Entity getEntity() {
        return (Entity)((RenderUtil.mc.getRenderViewEntity() == null) ? RenderUtil.mc.player : RenderUtil.mc.getRenderViewEntity());
    }
    
    public static void prepare(final float x, final float y, final float x1, final float y1, final int color, final int color1) {
        startRender();
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        GL11.glVertex2f(x, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        endRender2();
    }
    
    public static void startRender() {
        GL11.glPushAttrib(1048575);
        GL11.glPushMatrix();
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2884);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4353);
        GL11.glDisable(2896);
    }
    
    public static void endRender2() {
        GL11.glEnable(2896);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glDepthMask(true);
        GL11.glCullFace(1029);
        GL11.glPopMatrix();
        GL11.glPopAttrib();
    }
    
    public static void drawBlurredShadow(float x, float y, float width, float height, final int blurRadius, final Color color) {
        GL11.glPushMatrix();
        GlStateManager.alphaFunc(516, 0.01f);
        width += blurRadius * 2;
        height += blurRadius * 2;
        x -= blurRadius;
        y -= blurRadius;
        final float _X = x - 0.25f;
        final float _Y = y + 0.25f;
        final int identifier = (int)(width * height + width + color.hashCode() * blurRadius + blurRadius);
        GL11.glEnable(3553);
        GL11.glDisable(2884);
        GL11.glEnable(3008);
        GlStateManager.enableBlend();
        int texId = -1;
        if (RenderUtil.shadowCache.containsKey(identifier)) {
            texId = RenderUtil.shadowCache.get(identifier);
            GlStateManager.bindTexture(texId);
        }
        else {
            if (width <= 0.0f) {
                width = 1.0f;
            }
            if (height <= 0.0f) {
                height = 1.0f;
            }
            final BufferedImage original = new BufferedImage((int)width, (int)height, 3);
            final Graphics g = original.getGraphics();
            g.setColor(color);
            g.fillRect(blurRadius, blurRadius, (int)(width - blurRadius * 2), (int)(height - blurRadius * 2));
            g.dispose();
            final GaussianFilter op = new GaussianFilter((float)blurRadius);
            final BufferedImage blurred = op.filter(original, (BufferedImage)null);
            texId = TextureUtil.uploadTextureImageAllocate(TextureUtil.glGenTextures(), blurred, true, false);
            RenderUtil.shadowCache.put(identifier, texId);
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
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.resetColor();
        GL11.glEnable(2884);
        GL11.glPopMatrix();
    }
    
    public static void drawRect(double left, double top, double right, double bottom, final int color) {
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
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos(left, bottom, 0.0).endVertex();
        bufferbuilder.pos(right, bottom, 0.0).endVertex();
        bufferbuilder.pos(right, top, 0.0).endVertex();
        bufferbuilder.pos(left, top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public static void drawBorderedRect(final float left, final float top, final float right, final float bottom, final float borderWidth, final int insideColor, final int borderColor, final boolean borderIncludedInBounds) {
        drawRect(left - (borderIncludedInBounds ? 0.0f : borderWidth), top - (borderIncludedInBounds ? 0.0f : borderWidth), right + (borderIncludedInBounds ? 0.0f : borderWidth), bottom + (borderIncludedInBounds ? 0.0f : borderWidth), borderColor);
        drawRect(left + (borderIncludedInBounds ? borderWidth : 0.0f), top + (borderIncludedInBounds ? borderWidth : 0.0f), right - (borderIncludedInBounds ? borderWidth : 0.0f), bottom - (borderIncludedInBounds ? borderWidth : 0.0f), insideColor);
    }
    
    public static void renderBox(final AxisAlignedBB bb, final Color color, final Color outLineColor, final float lineWidth) {
        GL11.glPushMatrix();
        drawOutline(bb, lineWidth, outLineColor);
        drawBox(bb, color);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    public static void drawBox(final AxisAlignedBB bb, final Color color) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        color(color);
        fillBox(bb);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void drawOutline(final AxisAlignedBB bb, final float lineWidth, final Color color) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(lineWidth);
        color(color);
        fillOutline(bb);
        GL11.glLineWidth(1.0f);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void fillOutline(final AxisAlignedBB bb) {
        if (bb != null) {
            GL11.glBegin(1);
            GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
            glEnd();
        }
    }
    
    public static void fillBox(final AxisAlignedBB boundingBox) {
        if (boundingBox != null) {
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            glEnd();
        }
    }
    
    public static void color(final Color color) {
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }
    
    public static void color(final int color) {
        final Color colord = new Color(color);
        GL11.glColor4f(colord.getRed() / 255.0f, colord.getGreen() / 255.0f, colord.getBlue() / 255.0f, colord.getAlpha() / 255.0f);
    }
    
    public static void color(final float r, final float g, final float b, final float a) {
        GL11.glColor4f(r, g, b, a);
    }
    
    static {
        RenderUtil.camera = (ICamera)new Frustum();
        RenderUtil.tessellator = Tessellator.getInstance();
        RenderUtil.bufferbuilder = RenderUtil.tessellator.getBuffer();
        shadowCache = new HashMap<Integer, Integer>();
        RenderUtil.itemRender = RenderUtil.mc.getRenderItem();
        RenderUtil.camera = (ICamera)new Frustum();
        RenderUtil.delta = 0L;
    }
}

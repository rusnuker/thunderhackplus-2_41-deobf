//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.shaders;

import net.minecraft.client.shader.*;
import net.minecraft.client.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import java.awt.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;

public class FramebufferShader extends Shader
{
    protected Framebuffer framebuffer;
    protected float red;
    protected float green;
    protected float blue;
    protected float alpha;
    protected float radius;
    protected float quality;
    protected boolean entityShadows;
    protected Minecraft mc;
    
    public FramebufferShader(final String fragmentShader) {
        super(fragmentShader);
        this.mc = Minecraft.getMinecraft();
        this.alpha = 1.0f;
        this.radius = 2.0f;
        this.quality = 1.0f;
    }
    
    public void startDraw(final float partialTicks) {
        GlStateManager.enableAlpha();
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        (this.framebuffer = this.setupFrameBuffer(this.framebuffer)).framebufferClear();
        this.framebuffer.bindFramebuffer(true);
        this.entityShadows = this.mc.gameSettings.entityShadows;
        this.mc.gameSettings.entityShadows = false;
        ((IEntityRenderer)this.mc.entityRenderer).invokeSetupCameraTransform(partialTicks, 0);
    }
    
    public void stopDraw(final Color color, final float radius, final float quality, final float duplicate) {
        this.mc.gameSettings.entityShadows = this.entityShadows;
        this.framebuffer.unbindFramebuffer();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        this.mc.getFramebuffer().bindFramebuffer(true);
        this.red = color.getRed() / 255.0f;
        this.green = color.getGreen() / 255.0f;
        this.blue = color.getBlue() / 255.0f;
        this.alpha = color.getAlpha() / 255.0f;
        this.radius = radius;
        this.quality = quality;
        this.mc.entityRenderer.disableLightmap();
        RenderHelper.disableStandardItemLighting();
        this.startShader(duplicate);
        this.mc.entityRenderer.setupOverlayRendering();
        this.drawFramebuffer(this.framebuffer);
        this.stopShader();
        this.mc.entityRenderer.disableLightmap();
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }
    
    public Framebuffer setupFrameBuffer(Framebuffer frameBuffer) {
        if (frameBuffer == null) {
            return new Framebuffer(this.mc.displayWidth, this.mc.displayHeight, true);
        }
        if (frameBuffer.framebufferWidth != this.mc.displayWidth || frameBuffer.framebufferHeight != this.mc.displayHeight) {
            frameBuffer.deleteFramebuffer();
            frameBuffer = new Framebuffer(this.mc.displayWidth, this.mc.displayHeight, true);
        }
        return frameBuffer;
    }
    
    public void drawFramebuffer(final Framebuffer framebuffer) {
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        GL11.glBindTexture(3553, framebuffer.framebufferTexture);
        GL11.glBegin(7);
        GL11.glTexCoord2d(0.0, 1.0);
        GL11.glVertex2d(0.0, 0.0);
        GL11.glTexCoord2d(0.0, 0.0);
        GL11.glVertex2d(0.0, (double)scaledResolution.getScaledHeight());
        GL11.glTexCoord2d(1.0, 0.0);
        GL11.glVertex2d((double)scaledResolution.getScaledWidth(), (double)scaledResolution.getScaledHeight());
        GL11.glTexCoord2d(1.0, 1.0);
        GL11.glVertex2d((double)scaledResolution.getScaledWidth(), 0.0);
        GL11.glEnd();
        GL20.glUseProgram(0);
    }
}

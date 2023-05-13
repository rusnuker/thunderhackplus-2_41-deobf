//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.render;

import net.minecraft.client.shader.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.util.*;
import org.lwjgl.*;
import java.nio.*;
import org.lwjgl.opengl.*;

public class BlurUtil
{
    public static ShaderUtil blurShader;
    public static Framebuffer framebuffer;
    
    public static void uninitStencilBuffer() {
        GL11.glDisable(2960);
    }
    
    public static void drawBlur(final float radius, final Runnable data) {
        initStencilToWrite();
        data.run();
        readStencilBuffer(1);
        renderBlur(radius);
        uninitStencilBuffer();
    }
    
    public static void renderBlur(final float radius) {
        GlStateManager.enableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        (BlurUtil.framebuffer = createFrameBuffer(BlurUtil.framebuffer)).framebufferClear();
        BlurUtil.framebuffer.bindFramebuffer(true);
        BlurUtil.blurShader.init();
        setupUniforms(1.0f, 0.0f, radius);
        bindTexture(Util.mc.getFramebuffer().framebufferTexture);
        ShaderUtil.drawQuads();
        BlurUtil.framebuffer.unbindFramebuffer();
        BlurUtil.blurShader.unload();
        Util.mc.getFramebuffer().bindFramebuffer(true);
        BlurUtil.blurShader.init();
        setupUniforms(0.0f, 1.0f, radius);
        bindTexture(BlurUtil.framebuffer.framebufferTexture);
        ShaderUtil.drawQuads();
        BlurUtil.blurShader.unload();
        GlStateManager.resetColor();
        GlStateManager.bindTexture(0);
    }
    
    public static void bindTexture(final int texture) {
        GL11.glBindTexture(3553, texture);
    }
    
    public static void setupUniforms(final float dir1, final float dir2, final float radius) {
        BlurUtil.blurShader.setUniformi("textureIn", 0);
        BlurUtil.blurShader.setUniformf("texelSize", 1.0f / Util.mc.displayWidth, 1.0f / Util.mc.displayHeight);
        BlurUtil.blurShader.setUniformf("direction", dir1, dir2);
        BlurUtil.blurShader.setUniformf("radius", radius);
        final FloatBuffer weightBuffer = BufferUtils.createFloatBuffer(256);
        for (int i = 0; i <= radius; ++i) {
            weightBuffer.put(calculateGaussianValue((float)i, radius / 2.0f));
        }
        weightBuffer.rewind();
        GL20.glUniform1(BlurUtil.blurShader.getUniform("weights"), weightBuffer);
    }
    
    public static float calculateGaussianValue(final float x, final float sigma) {
        final double PI = 3.141592653;
        final double output = 1.0 / Math.sqrt(2.0 * PI * (sigma * sigma));
        return (float)(output * Math.exp(-(x * x) / (2.0 * (sigma * sigma))));
    }
    
    public static Framebuffer createFrameBuffer(final Framebuffer framebuffer) {
        if (framebuffer != null && framebuffer.framebufferWidth == Util.mc.displayWidth && framebuffer.framebufferHeight == Util.mc.displayHeight) {
            return framebuffer;
        }
        if (framebuffer != null) {
            framebuffer.deleteFramebuffer();
        }
        return new Framebuffer(Util.mc.displayWidth, Util.mc.displayHeight, true);
    }
    
    public static void initStencilToWrite() {
        Util.mc.getFramebuffer().bindFramebuffer(false);
        checkSetupFBO(Util.mc.getFramebuffer());
        GL11.glClear(1024);
        GL11.glEnable(2960);
        GL11.glStencilFunc(519, 1, 1);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glColorMask(false, false, false, false);
    }
    
    public static void checkSetupFBO(final Framebuffer framebuffer) {
        if (framebuffer != null && framebuffer.depthBuffer > -1) {
            setupFBO(framebuffer);
            framebuffer.depthBuffer = -1;
        }
    }
    
    public static void setupFBO(final Framebuffer framebuffer) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(framebuffer.depthBuffer);
        final int stencilDepthBufferID = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(36161, stencilDepthBufferID);
        EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, Util.mc.displayWidth, Util.mc.displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencilDepthBufferID);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencilDepthBufferID);
    }
    
    public static void readStencilBuffer(final int ref) {
        GL11.glColorMask(true, true, true, true);
        GL11.glStencilFunc(514, ref, 1);
        GL11.glStencilOp(7680, 7680, 7680);
    }
    
    static {
        BlurUtil.blurShader = new ShaderUtil("blurShader");
        BlurUtil.framebuffer = new Framebuffer(1, 1, false);
    }
}

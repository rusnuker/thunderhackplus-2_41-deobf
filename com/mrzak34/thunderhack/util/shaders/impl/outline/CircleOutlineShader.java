//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.shaders.impl.outline;

import com.mrzak34.thunderhack.util.shaders.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import java.util.*;

public final class CircleOutlineShader extends FramebufferShader
{
    public static final CircleOutlineShader INSTANCE;
    public float time;
    
    public CircleOutlineShader() {
        super("circleOutline.frag");
        this.time = 0.0f;
    }
    
    public void setupUniforms() {
        this.setupUniform("texture");
        this.setupUniform("texelSize");
        this.setupUniform("colors");
        this.setupUniform("divider");
        this.setupUniform("radius");
        this.setupUniform("maxSample");
        this.setupUniform("alpha0");
        this.setupUniform("resolution");
        this.setupUniform("time");
        this.setupUniform("PI");
        this.setupUniform("rad");
    }
    
    public void updateUniforms(final Color color, final float radius, final float quality, final boolean gradientAlpha, final int alphaOutline, final float duplicate, final float PI, final float rad) {
        GL20.glUniform1i(this.getUniform("texture"), 0);
        GL20.glUniform2f(this.getUniform("texelSize"), 1.0f / this.mc.displayWidth * (radius * quality), 1.0f / this.mc.displayHeight * (radius * quality));
        GL20.glUniform3f(this.getUniform("colors"), color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f);
        GL20.glUniform1f(this.getUniform("divider"), 140.0f);
        GL20.glUniform1f(this.getUniform("radius"), radius);
        GL20.glUniform1f(this.getUniform("maxSample"), 10.0f);
        GL20.glUniform1f(this.getUniform("alpha0"), gradientAlpha ? -1.0f : (alphaOutline / 255.0f));
        GL20.glUniform2f(this.getUniform("resolution"), new ScaledResolution(this.mc).getScaledWidth() / duplicate, new ScaledResolution(this.mc).getScaledHeight() / duplicate);
        GL20.glUniform1f(this.getUniform("time"), this.time);
        GL20.glUniform1f(this.getUniform("PI"), PI);
        GL20.glUniform1f(this.getUniform("rad"), rad);
    }
    
    public void stopDraw(final Color color, final float radius, final float quality, final boolean gradientAlpha, final int alphaOutline, final float duplicate, final float PI, final float rad) {
        this.mc.gameSettings.entityShadows = this.entityShadows;
        this.framebuffer.unbindFramebuffer();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        this.mc.getFramebuffer().bindFramebuffer(true);
        this.mc.entityRenderer.disableLightmap();
        RenderHelper.disableStandardItemLighting();
        this.startShader(color, radius, quality, gradientAlpha, alphaOutline, duplicate, PI, rad);
        this.mc.entityRenderer.setupOverlayRendering();
        this.drawFramebuffer(this.framebuffer);
        this.stopShader();
        this.mc.entityRenderer.disableLightmap();
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }
    
    public void startShader(final Color color, final float radius, final float quality, final boolean gradientAlpha, final int alphaOutline, final float duplicate, final float PI, final float rad) {
        GL11.glPushMatrix();
        GL20.glUseProgram(this.program);
        if (this.uniformsMap == null) {
            this.uniformsMap = new HashMap();
            this.setupUniforms();
        }
        this.updateUniforms(color, radius, quality, gradientAlpha, alphaOutline, duplicate, PI, rad);
    }
    
    public void update(final double speed) {
        this.time += (float)speed;
    }
    
    static {
        INSTANCE = new CircleOutlineShader();
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.thundergui2.components;

import com.mrzak34.thunderhack.gui.thundergui2.*;
import com.mrzak34.thunderhack.manager.*;
import java.awt.*;
import com.mrzak34.thunderhack.gui.clickui.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.modules.client.*;
import com.mrzak34.thunderhack.util.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.gui.hud.elements.*;
import com.mrzak34.thunderhack.util.render.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;

public class ConfigComponent
{
    float scroll_animation;
    private final String name;
    private final String date;
    private int posX;
    private int posY;
    private int progress;
    private int fade;
    private final int index;
    private boolean first_open;
    private float scrollPosY;
    private float prevPosY;
    
    public ConfigComponent(final String name, final String date, final int posX, final int posY, final int index) {
        this.scroll_animation = 0.0f;
        this.first_open = true;
        this.name = name;
        this.date = date;
        this.posX = posX;
        this.posY = posY;
        this.fade = 0;
        this.index = index * 5;
        this.scrollPosY = (float)posY;
        this.scroll_animation = 0.0f;
    }
    
    public void render(final int MouseX, final int MouseY) {
        if (this.scrollPosY != this.posY) {
            this.scroll_animation = ThunderGui2.fast(this.scroll_animation, 1.0f, 15.0f);
            this.posY = (int)RenderUtil.interpolate(this.scrollPosY, this.prevPosY, this.scroll_animation);
        }
        if (this.posY > ThunderGui2.getInstance().main_posY + ThunderGui2.getInstance().height || this.posY < ThunderGui2.getInstance().main_posY) {
            return;
        }
        if (ConfigManager.currentConfig.getName().equals(this.name + ".th")) {
            RoundedShader.drawGradientRound((float)(this.posX + 5), (float)this.posY, 285.0f, 30.0f, 4.0f, ColorUtil.applyOpacity(new Color(55, 44, 66, 255), this.getFadeFactor()), ColorUtil.applyOpacity(new Color(25, 20, 30, 255), this.getFadeFactor()), ColorUtil.applyOpacity(ThunderHackGui.getInstance().onColor1.getValue().getColorObject(), this.getFadeFactor()), ColorUtil.applyOpacity(ThunderHackGui.getInstance().onColor2.getValue().getColorObject(), this.getFadeFactor()));
        }
        else {
            RoundedShader.drawRound((float)(this.posX + 5), (float)this.posY, 285.0f, 30.0f, 4.0f, ColorUtil.applyOpacity(new Color(44, 35, 52, 255), this.getFadeFactor()));
        }
        if (this.first_open) {
            GL11.glPushMatrix();
            Stencil.write(false);
            Particles.roundedRect(this.posX - 0.5 + 5.0, this.posY - 0.5, 286.0, 31.0, 8.0, ColorUtil.applyOpacity(new Color(0, 0, 0, 255), this.getFadeFactor()));
            Stencil.erase(true);
            RenderUtil.drawBlurredShadow((float)(MouseX - 20), (float)(MouseY - 20), 40.0f, 40.0f, 60, ColorUtil.applyOpacity(new Color(-1017816450, true), this.getFadeFactor()));
            Stencil.dispose();
            GL11.glPopMatrix();
            this.first_open = false;
        }
        if (this.isHovered(MouseX, MouseY)) {
            GL11.glPushMatrix();
            Stencil.write(false);
            Particles.roundedRect(this.posX - 0.5 + 5.0, this.posY - 0.5, 286.0, 31.0, 8.0, ColorUtil.applyOpacity(new Color(0, 0, 0, 255), this.getFadeFactor()));
            Stencil.erase(true);
            RenderUtil.drawBlurredShadow((float)(MouseX - 20), (float)(MouseY - 20), 40.0f, 40.0f, 60, ColorUtil.applyOpacity(new Color(-1017816450, true), this.getFadeFactor()));
            Stencil.dispose();
            GL11.glPopMatrix();
        }
        RoundedShader.drawRound((float)(this.posX + 250), (float)(this.posY + 8), 30.0f, 14.0f, 2.0f, ColorUtil.applyOpacity(new Color(25, 20, 30, 255), this.getFadeFactor()));
        if (Drawable.isHovered(MouseX, MouseY, this.posX + 252, this.posY + 10, 10.0, 10.0)) {
            RoundedShader.drawRound((float)(this.posX + 252), (float)(this.posY + 10), 10.0f, 10.0f, 2.0f, ColorUtil.applyOpacity(new Color(21, 58, 0, 255), this.getFadeFactor()));
        }
        else {
            RoundedShader.drawRound((float)(this.posX + 252), (float)(this.posY + 10), 10.0f, 10.0f, 2.0f, ColorUtil.applyOpacity(new Color(32, 89, 0, 255), this.getFadeFactor()));
        }
        if (Drawable.isHovered(MouseX, MouseY, this.posX + 268, this.posY + 10, 10.0, 10.0)) {
            RoundedShader.drawRound((float)(this.posX + 268), (float)(this.posY + 10), 10.0f, 10.0f, 2.0f, ColorUtil.applyOpacity(new Color(65, 1, 13, 255), this.getFadeFactor()));
        }
        else {
            RoundedShader.drawRound((float)(this.posX + 268), (float)(this.posY + 10), 10.0f, 10.0f, 2.0f, ColorUtil.applyOpacity(new Color(94, 1, 18, 255), this.getFadeFactor()));
        }
        FontRender.drawIcon("x", this.posX + 252, this.posY + 13, ColorUtil.applyOpacity(-1, this.getFadeFactor()));
        FontRender.drawIcon("w", this.posX + 268, this.posY + 13, ColorUtil.applyOpacity(-1, this.getFadeFactor()));
        FontRender.drawMidIcon("u", (float)(this.posX + 7), (float)(this.posY + 5), ColorUtil.applyOpacity(-1, this.getFadeFactor()));
        FontRender.drawString6(this.name, (float)(this.posX + 37), (float)(this.posY + 6), ColorUtil.applyOpacity(-1, this.getFadeFactor()), false);
        FontRender.drawString7("updated on: " + this.date, (float)(this.posX + 37), (float)(this.posY + 17), ColorUtil.applyOpacity(new Color(-4342339, true).getRGB(), this.getFadeFactor()), false);
    }
    
    private float getFadeFactor() {
        return this.fade / (5.0f + this.index);
    }
    
    public void onTick() {
        if (this.progress > 4) {
            this.progress = 0;
        }
        ++this.progress;
        if (this.fade < 10 + this.index) {
            ++this.fade;
        }
    }
    
    private boolean isHovered(final int mouseX, final int mouseY) {
        return mouseX > this.posX && mouseX < this.posX + 295 && mouseY > this.posY && mouseY < this.posY + 30;
    }
    
    public void movePosition(final float deltaX, final float deltaY) {
        this.posY += (int)deltaY;
        this.posX += (int)deltaX;
        this.scrollPosY = (float)this.posY;
    }
    
    public void mouseClicked(final int MouseX, final int MouseY, final int clickedButton) {
        if (this.posY > ThunderGui2.getInstance().main_posY + ThunderGui2.getInstance().height || this.posY < ThunderGui2.getInstance().main_posY) {
            return;
        }
        if (Drawable.isHovered(MouseX, MouseY, this.posX + 252, this.posY + 10, 10.0, 10.0)) {
            ConfigManager.load(this.name);
        }
        if (Drawable.isHovered(MouseX, MouseY, this.posX + 268, this.posY + 10, 10.0, 10.0)) {
            ConfigManager.delete(this.name);
            ThunderGui2.getInstance().loadConfigs();
        }
    }
    
    public double getPosX() {
        return this.posX;
    }
    
    public double getPosY() {
        return this.posY;
    }
    
    public void scrollElement(final float deltaY) {
        this.scroll_animation = 0.0f;
        this.prevPosY = (float)this.posY;
        this.scrollPosY += deltaY;
    }
}

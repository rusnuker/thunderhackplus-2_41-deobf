//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.thundergui2.components;

import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.gui.thundergui2.*;
import com.mrzak34.thunderhack.util.render.*;
import java.io.*;

public class SettingElement
{
    protected Setting setting;
    protected double x;
    protected double y;
    protected double width;
    protected double height;
    protected double offsetY;
    protected double prev_offsetY;
    protected double scroll_offsetY;
    protected float scroll_animation;
    protected boolean hovered;
    
    public SettingElement(final Setting setting) {
        this.setting = setting;
        this.scroll_animation = 0.0f;
        this.prev_offsetY = this.y;
        this.scroll_offsetY = 0.0;
    }
    
    public void render(final int mouseX, final int mouseY, final float delta) {
        this.hovered = Drawable.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height);
        if (this.scroll_offsetY != this.y) {
            this.scroll_animation = ThunderGui2.fast(this.scroll_animation, 1.0f, 15.0f);
            this.y = (int)RenderUtil.interpolate(this.scroll_offsetY, this.prev_offsetY, this.scroll_animation);
        }
    }
    
    public void init() {
    }
    
    public void onTick() {
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
    }
    
    public void tick() {
    }
    
    public boolean isHovered() {
        return this.hovered;
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int button) {
    }
    
    public void handleMouseInput() throws IOException {
    }
    
    public void keyTyped(final char chr, final int keyCode) {
    }
    
    public void onClose() {
    }
    
    public void resetAnimation() {
    }
    
    public Setting getSetting() {
        return this.setting;
    }
    
    public double getX() {
        return this.x;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setY(final double y) {
        this.prev_offsetY = this.y;
        this.scroll_offsetY = y + this.offsetY;
    }
    
    public double getWidth() {
        return this.width;
    }
    
    public void setWidth(final double width) {
        this.width = width;
    }
    
    public double getHeight() {
        return this.height;
    }
    
    public void setHeight(final double height) {
        this.height = height;
    }
    
    public void setOffsetY(final double offsetY) {
        this.offsetY = offsetY;
    }
    
    public boolean isVisible() {
        return this.setting.isVisible();
    }
    
    public void checkMouseWheel(final float dWheel) {
        if (dWheel != 0.0f) {
            this.scroll_animation = 0.0f;
        }
    }
}

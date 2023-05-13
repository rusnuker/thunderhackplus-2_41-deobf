//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.clickui.base;

import com.mrzak34.thunderhack.setting.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.render.*;
import java.io.*;

public abstract class AbstractElement
{
    protected Setting setting;
    protected double x;
    protected double y;
    protected double width;
    protected double height;
    protected double offsetY;
    protected boolean hovered;
    protected int bgcolor;
    
    public AbstractElement(final Setting setting) {
        this.bgcolor = new Color(24, 24, 27).getRGB();
        this.setting = setting;
    }
    
    public void render(final int mouseX, final int mouseY, final float delta) {
        this.hovered = Drawable.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height);
    }
    
    public void init() {
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
    }
    
    public void tick() {
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
        this.y = y + this.offsetY;
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
}

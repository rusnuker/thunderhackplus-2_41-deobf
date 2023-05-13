//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.hud;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import org.lwjgl.input.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.client.gui.*;
import com.mrzak34.thunderhack.gui.hud.elements.*;
import com.mrzak34.thunderhack.gui.thundergui2.*;

public class HudElement extends Module
{
    int height;
    int width;
    int dragX;
    int dragY;
    private boolean mousestate;
    float x1;
    float y1;
    public final Setting<ColorSetting> shadowColor;
    public final Setting<ColorSetting> color2;
    public final Setting<ColorSetting> color3;
    public final Setting<ColorSetting> textColor;
    private final Setting<PositionSetting> pos;
    
    public HudElement(final String name, final String description, final int width, final int height) {
        super(name, description, Category.HUD);
        this.dragY = 0;
        this.mousestate = false;
        this.x1 = 0.0f;
        this.y1 = 0.0f;
        this.shadowColor = (Setting<ColorSetting>)this.register(new Setting("ShadowColor", (T)new ColorSetting(-15724528)));
        this.color2 = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-15724528)));
        this.color3 = (Setting<ColorSetting>)this.register(new Setting("Color2", (T)new ColorSetting(-979657829)));
        this.textColor = (Setting<ColorSetting>)this.register(new Setting("TextColor", (T)new ColorSetting(12500670)));
        this.pos = (Setting<PositionSetting>)this.register(new Setting("Position", (T)new PositionSetting(0.5f, 0.5f)));
        this.height = height;
        this.width = width;
    }
    
    public HudElement(final String name, final String description, final String eng_description, final int width, final int height) {
        super(name, description, eng_description, Category.HUD);
        this.dragY = 0;
        this.mousestate = false;
        this.x1 = 0.0f;
        this.y1 = 0.0f;
        this.shadowColor = (Setting<ColorSetting>)this.register(new Setting("ShadowColor", (T)new ColorSetting(-15724528)));
        this.color2 = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-15724528)));
        this.color3 = (Setting<ColorSetting>)this.register(new Setting("Color2", (T)new ColorSetting(-979657829)));
        this.textColor = (Setting<ColorSetting>)this.register(new Setting("TextColor", (T)new ColorSetting(12500670)));
        this.pos = (Setting<PositionSetting>)this.register(new Setting("Position", (T)new PositionSetting(0.5f, 0.5f)));
        this.height = height;
        this.width = width;
    }
    
    public int normaliseX() {
        return (int)(Mouse.getX() / 2.0f);
    }
    
    public int normaliseY() {
        final ScaledResolution sr = new ScaledResolution(HudElement.mc);
        return (-Mouse.getY() + sr.getScaledHeight() + sr.getScaledHeight()) / 2;
    }
    
    public boolean isHovering() {
        return this.normaliseX() > this.x1 && this.normaliseX() < this.x1 + this.width && this.normaliseY() > this.y1 && this.normaliseY() < this.y1 + this.height;
    }
    
    @Override
    public void onRender2D(final Render2DEvent e) {
        this.y1 = e.scaledResolution.getScaledHeight() * this.pos.getValue().getY();
        this.x1 = e.scaledResolution.getScaledWidth() * this.pos.getValue().getX();
        if ((HudElement.mc.currentScreen instanceof GuiChat || HudElement.mc.currentScreen instanceof HudEditorGui || HudElement.mc.currentScreen instanceof ThunderGui2) && Mouse.isButtonDown(0) && this.mousestate) {
            this.pos.getValue().setX((this.normaliseX() - this.dragX) / (float)e.scaledResolution.getScaledWidth());
            this.pos.getValue().setY((this.normaliseY() - this.dragY) / (float)e.scaledResolution.getScaledHeight());
        }
        if (Mouse.isButtonDown(0)) {
            if (!this.mousestate && this.isHovering()) {
                this.dragX = (int)(this.normaliseX() - this.pos.getValue().getX() * e.scaledResolution.getScaledWidth());
                this.dragY = (int)(this.normaliseY() - this.pos.getValue().getY() * e.scaledResolution.getScaledHeight());
                this.mousestate = true;
            }
        }
        else {
            this.mousestate = false;
        }
    }
    
    public float getPosX() {
        return this.x1;
    }
    
    public float getPosY() {
        return this.y1;
    }
    
    public float getX() {
        return this.pos.getValue().x;
    }
    
    public float getY() {
        return this.pos.getValue().y;
    }
    
    public void setHeight(final int h) {
        this.height = h;
    }
}

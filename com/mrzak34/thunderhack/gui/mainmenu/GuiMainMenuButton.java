//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.mainmenu;

import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import net.minecraft.init.*;
import net.minecraft.client.audio.*;

public class GuiMainMenuButton extends GuiButton
{
    static ScaledResolution sr;
    boolean _double;
    boolean alt;
    String name;
    
    public GuiMainMenuButton(final int buttonId, final int x, final int y, final boolean _double, final String name, final boolean alt) {
        super(buttonId, x, y, name);
        GuiMainMenuButton.sr = new ScaledResolution(Util.mc);
        this._double = _double;
        if (_double) {
            this.setWidth(222);
        }
        else {
            this.setWidth(107);
        }
        this.name = name;
        this.alt = alt;
    }
    
    public static int getMouseX() {
        return Mouse.getX() * GuiMainMenuButton.sr.getScaledWidth() / Minecraft.getMinecraft().displayWidth;
    }
    
    public static int getMouseY() {
        return GuiMainMenuButton.sr.getScaledHeight() - Mouse.getY() * GuiMainMenuButton.sr.getScaledHeight() / Minecraft.getMinecraft().displayHeight - 1;
    }
    
    public void drawButton(final Minecraft mc, final int mouseX, final int mouseY, final float mouseButton) {
        if (this.visible) {
            this.hovered = (mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + 35);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            final Color color = new Color(-2046820352, true);
            GlStateManager.pushMatrix();
            if (!this.alt) {
                if (this._double) {
                    RoundedShader.drawGradientRound(this.hovered ? ((float)(this.x + 1)) : ((float)this.x), this.hovered ? ((float)(this.y + 1)) : ((float)this.y), this.hovered ? 220.0f : 222.0f, this.hovered ? 33.0f : 35.0f, 7.0f, color, color, color, color);
                    FontRender.drawCentString6(this.name, (float)(this.x + 111), this.y + 17.0f, this.hovered ? new Color(8026746).getRGB() : -1);
                }
                else {
                    RoundedShader.drawGradientRound(this.hovered ? ((float)(this.x + 1)) : ((float)this.x), this.hovered ? ((float)(this.y + 1)) : ((float)this.y), this.hovered ? 105.0f : 107.0f, this.hovered ? 33.0f : 35.0f, 7.0f, color, color, color, color);
                    FontRender.drawCentString6(this.name, this.x + 53.5f, this.y + 17.0f, this.hovered ? new Color(8026746).getRGB() : -1);
                }
            }
            else if (this._double) {
                RoundedShader.drawGradientRound(this.hovered ? ((float)(this.x + 1)) : ((float)this.x), this.hovered ? ((float)(this.y + 1)) : ((float)this.y), this.hovered ? 237.0f : 239.0f, this.hovered ? 28.0f : 30.0f, 7.0f, color, color, color, color);
                FontRender.drawCentString6(this.name, this.x + 124.5f, this.y + 15.0f, this.hovered ? new Color(8026746).getRGB() : -1);
            }
            else {
                RoundedShader.drawGradientRound(this.hovered ? ((float)(this.x + 1)) : ((float)this.x), this.hovered ? ((float)(this.y + 1)) : ((float)this.y), this.hovered ? 113.0f : 115.0f, this.hovered ? 28.0f : 30.0f, 7.0f, color, color, color, color);
                FontRender.drawCentString6(this.name, this.x + 53.5f, this.y + 15.0f, this.hovered ? new Color(8026746).getRGB() : -1);
            }
            GlStateManager.popMatrix();
            this.mouseDragged(mc, mouseX, mouseY);
        }
    }
    
    protected void mouseDragged(final Minecraft mc, final int mouseX, final int mouseY) {
    }
    
    public void mouseReleased(final int mouseX, final int mouseY) {
    }
    
    public boolean mousePressed(final Minecraft mc, final int mouseX, final int mouseY) {
        return this.enabled && this.visible && mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height + 10;
    }
    
    public boolean isMouseOver() {
        return this.hovered;
    }
    
    public void drawButtonForegroundLayer(final int mouseX, final int mouseY) {
    }
    
    public void playPressSound(final SoundHandler soundHandlerIn) {
        soundHandlerIn.playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
    }
    
    public int getButtonWidth() {
        return this.width;
    }
    
    public void setWidth(final int width) {
        this.width = width;
    }
}

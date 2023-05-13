//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.thundergui2.components;

import java.awt.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.gui.thundergui2.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.util.render.*;
import com.mrzak34.thunderhack.util.math.*;
import com.mrzak34.thunderhack.*;

public class ColorPickerComponent extends SettingElement
{
    private final Setting colorSetting;
    private boolean open;
    private float hue;
    private float saturation;
    private float brightness;
    private int alpha;
    private boolean afocused;
    private boolean hfocused;
    private boolean sbfocused;
    private boolean copy_focused;
    private boolean paste_focused;
    private boolean rainbow_focused;
    private float spos;
    private float bpos;
    private float hpos;
    private float apos;
    private Color prevColor;
    private boolean firstInit;
    
    public ColorPickerComponent(final Setting setting) {
        super(setting);
        this.colorSetting = setting;
        this.firstInit = true;
    }
    
    public ColorSetting getColorSetting() {
        return this.colorSetting.getValue();
    }
    
    @Override
    public void render(final int mouseX, final int mouseY, final float delta) {
        super.render(mouseX, mouseY, delta);
        if (this.getY() > ThunderGui2.getInstance().main_posY + ThunderGui2.getInstance().height || this.getY() < ThunderGui2.getInstance().main_posY) {
            return;
        }
        FontRender.drawString6(this.getSetting().getName(), (float)this.getX(), (float)this.getY() + 5.0f, this.isHovered() ? -1 : new Color(-1325400065, true).getRGB(), false);
        Drawable.drawBlurredShadow((float)(int)(this.x + this.width - 20.0), (float)(int)(this.y + 5.0), 14.0f, 6.0f, 10, this.getColorSetting().getColorObject());
        RoundedShader.drawRound((float)(this.x + this.width - 20.0), (float)(this.y + 5.0), 14.0f, 6.0f, 1.0f, this.getColorSetting().getColorObject());
        if (this.open) {
            this.renderPicker(mouseX, mouseY, this.getColorSetting().getColorObject());
        }
    }
    
    @Override
    public void onTick() {
        super.onTick();
    }
    
    private void renderPicker(final int mouseX, final int mouseY, final Color color) {
        final double cx = this.x + 6.0;
        final double cy = this.y + 20.0;
        final double cw = this.width - 38.0;
        final double ch = this.height - 20.0;
        if (this.prevColor != this.getColorSetting().getColorObject()) {
            this.updatePos();
            this.prevColor = this.getColorSetting().getColorObject();
        }
        if (this.firstInit) {
            this.spos = (float)(cx + cw - (cw - cw * this.saturation));
            this.bpos = (float)(cy + (ch - ch * this.brightness));
            this.hpos = (float)(cy + (ch - 3.0 + (ch - 3.0) * this.hue));
            this.apos = (float)(cy + (ch - 3.0 - (ch - 3.0) * (this.alpha / 255.0f)));
            this.firstInit = false;
        }
        this.spos = RenderUtil.scrollAnimate(this.spos, (float)(cx + 40.0 + (cw - 40.0) - (cw - 40.0 - (cw - 40.0) * this.saturation)), 0.6f);
        this.bpos = RenderUtil.scrollAnimate(this.bpos, (float)(cy + (ch - ch * this.brightness)), 0.6f);
        this.hpos = RenderUtil.scrollAnimate(this.hpos, (float)(cy + (ch - 3.0 + (ch - 3.0) * this.hue)), 0.6f);
        this.apos = RenderUtil.scrollAnimate(this.apos, (float)(cy + (ch - 3.0 - (ch - 3.0) * (this.alpha / 255.0f))), 0.6f);
        final Color colorA = Color.getHSBColor(this.hue, 0.0f, 1.0f);
        final Color colorB = Color.getHSBColor(this.hue, 1.0f, 1.0f);
        final Color colorC = new Color(0, 0, 0, 0);
        final Color colorD = new Color(0, 0, 0);
        Drawable.horizontalGradient(cx + 40.0, cy, cx + cw, cy + ch, colorA.getRGB(), colorB.getRGB());
        Drawable.verticalGradient(cx + 40.0, cy, cx + cw, cy + ch, colorC.getRGB(), colorD.getRGB());
        for (float i = 1.0f; i < ch - 2.0; ++i) {
            final float curHue = (float)(1.0 / (ch / i));
            Drawable.drawRectWH(cx + cw + 4.0, cy + i, 8.0, 1.0, Color.getHSBColor(curHue, 1.0f, 1.0f).getRGB());
        }
        Drawable.drawRectWH(cx + cw + 17.0, cy + 1.0, 8.0, ch - 3.0, -1);
        Drawable.verticalGradient(cx + cw + 17.0, cy + 0.8, cx + cw + 25.0, cy + ch - 2.0, new Color(color.getRed(), color.getGreen(), color.getBlue(), 255).getRGB(), new Color(0, 0, 0, 0).getRGB());
        Drawable.drawRectWH(cx + cw + 3.0, this.hpos + 0.5f, 10.0, 1.0, -1);
        Drawable.drawRectWH(cx + cw + 16.0, this.apos + 0.5f, 10.0, 1.0, -1);
        RoundedShader.drawRoundOutline(this.spos, this.bpos, 3.0f, 3.0f, 1.5f, 1.0f, color, new Color(-1));
        Color value = Color.getHSBColor(this.hue, this.saturation, this.brightness);
        if (this.sbfocused) {
            this.saturation = (float)(MathUtil.clamp(mouseX - (cx + 40.0), 0.0, cw - 40.0) / (cw - 40.0));
            this.brightness = (float)((ch - MathUtil.clamp(mouseY - cy, 0.0, ch)) / ch);
            value = Color.getHSBColor(this.hue, this.saturation, this.brightness);
            this.setColor(new Color(value.getRed(), value.getGreen(), value.getBlue(), this.alpha));
        }
        if (this.hfocused) {
            this.hue = (float)(-((ch - MathUtil.clamp((float)(mouseY - cy), 0.0f, (float)ch)) / ch));
            value = Color.getHSBColor(this.hue, this.saturation, this.brightness);
            this.setColor(new Color(value.getRed(), value.getGreen(), value.getBlue(), this.alpha));
        }
        if (this.afocused) {
            this.alpha = (int)((ch - MathUtil.clamp((float)(mouseY - cy), 0.0f, (float)ch)) / ch * 255.0);
            this.setColor(new Color(value.getRed(), value.getGreen(), value.getBlue(), this.alpha));
        }
        this.rainbow_focused = Drawable.isHovered(mouseX, mouseY, this.getX(), cy, 40.0, 10.0);
        this.copy_focused = Drawable.isHovered(mouseX, mouseY, this.getX(), cy + 13.0, 40.0, 10.0);
        this.paste_focused = Drawable.isHovered(mouseX, mouseY, this.getX(), cy + 26.0, 40.0, 10.0);
        RoundedShader.drawRound((float)this.getX(), (float)cy, 40.0f, 10.0f, 2.0f, this.getColorSetting().isCycle() ? new Color(86, 63, 105, 250) : (this.rainbow_focused ? new Color(66, 48, 80, 250) : new Color(50, 35, 60, 250)));
        RoundedShader.drawRound((float)this.getX(), (float)cy + 13.0f, 40.0f, 10.0f, 2.0f, this.copy_focused ? new Color(66, 48, 80, 250) : new Color(50, 35, 60, 250));
        RoundedShader.drawRound((float)this.getX(), (float)cy + 26.0f, 40.0f, 9.5f, 2.0f, this.paste_focused ? new Color(66, 48, 80, 250) : new Color(50, 35, 60, 250));
        FontRender.drawCentString6("rainbow", (float)this.getX() + 20.0f, (float)cy + 4.0f, this.rainbow_focused ? -1 : (this.getColorSetting().isCycle() ? this.getColorSetting().getColor() : new Color(-1241513985, true).getRGB()));
        FontRender.drawCentString6("copy", (float)this.getX() + 20.0f, (float)cy + 16.5f, this.copy_focused ? -1 : new Color(-1241513985, true).getRGB());
        FontRender.drawCentString6("paste", (float)this.getX() + 20.0f, (float)cy + 29.5f, this.paste_focused ? -1 : new Color(-1241513985, true).getRGB());
    }
    
    private void updatePos() {
        final float[] hsb = Color.RGBtoHSB(this.getColorSetting().getColorObject().getRed(), this.getColorSetting().getColorObject().getGreen(), this.getColorSetting().getColorObject().getBlue(), null);
        this.hue = -1.0f + hsb[0];
        this.saturation = hsb[1];
        this.brightness = hsb[2];
        this.alpha = this.getColorSetting().getColorObject().getAlpha();
    }
    
    private void setColor(final Color color) {
        this.getColorSetting().setColor(color.getRGB());
        this.prevColor = color;
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.getY() > ThunderGui2.getInstance().main_posY + ThunderGui2.getInstance().height || this.getY() < ThunderGui2.getInstance().main_posY) {
            return;
        }
        final double cx = this.x + 4.0;
        final double cy = this.y + 21.0;
        final double cw = this.width - 34.0;
        final double ch = this.height - 20.0;
        if (Drawable.isHovered(mouseX, mouseY, this.x + this.width - 20.0, this.y + 5.0, 14.0, 6.0)) {
            this.open = !this.open;
        }
        if (!this.open) {
            return;
        }
        if (Drawable.isHovered(mouseX, mouseY, cx + cw + 17.0, cy, 8.0, ch) && button == 0) {
            this.afocused = true;
        }
        else if (Drawable.isHovered(mouseX, mouseY, cx + cw + 4.0, cy, 8.0, ch) && button == 0) {
            this.hfocused = true;
        }
        else if (Drawable.isHovered(mouseX, mouseY, cx + 40.0, cy, cw - 40.0, ch) && button == 0) {
            this.sbfocused = true;
        }
        if (this.rainbow_focused) {
            this.getColorSetting().setCycle(!this.getColorSetting().isCycle());
        }
        if (this.copy_focused) {
            Thunderhack.copy_color = this.getColorSetting().getColorObject();
        }
        if (this.paste_focused) {
            this.setColor((Thunderhack.copy_color == null) ? this.getColorSetting().getColorObject() : Thunderhack.copy_color);
        }
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int button) {
        this.hfocused = false;
        this.afocused = false;
        this.sbfocused = false;
    }
    
    @Override
    public void onClose() {
        this.hfocused = false;
        this.afocused = false;
        this.sbfocused = false;
    }
    
    public boolean isOpen() {
        return this.open;
    }
}

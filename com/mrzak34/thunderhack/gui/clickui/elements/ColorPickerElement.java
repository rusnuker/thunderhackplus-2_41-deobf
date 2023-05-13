//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.clickui.elements;

import com.mrzak34.thunderhack.gui.clickui.base.*;
import java.awt.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import com.mrzak34.thunderhack.util.render.*;
import com.mrzak34.thunderhack.util.math.*;

public class ColorPickerElement extends AbstractElement
{
    private float hue;
    private float saturation;
    private float brightness;
    private int alpha;
    private boolean afocused;
    private boolean hfocused;
    private boolean sbfocused;
    private float spos;
    private float bpos;
    private float hpos;
    private float apos;
    private Color prevColor;
    private boolean firstInit;
    private final Setting colorSetting;
    
    public ColorPickerElement(final Setting setting) {
        super(setting);
        this.colorSetting = setting;
        this.prevColor = this.getColorSetting().getColorObject();
        this.updatePos();
        this.firstInit = true;
    }
    
    public ColorSetting getColorSetting() {
        return this.colorSetting.getValue();
    }
    
    public void render(final int mouseX, final int mouseY, final float delta) {
        RoundedShader.drawRound((float)this.x + 2.0f, (float)this.y + 2.0f, (float)this.width - 4.0f, (float)this.height - 4.0f, 4.0f, new Color(this.bgcolor));
        FontRender.drawString5(this.setting.getName(), (float)this.x + 4.0f, (float)this.y + 5.0f, -1);
        Drawable.drawBlurredShadow((float)(int)(this.x + this.width - 20.0), (float)(int)(this.y + 5.0), 14.0f, 6.0f, 10, this.getColorSetting().getColorObject());
        RoundedShader.drawRound((float)(this.x + this.width - 20.0), (float)(this.y + 5.0), 14.0f, 6.0f, 1.0f, this.getColorSetting().getColorObject());
        if (!this.getColorSetting().isCycle()) {
            FontRender.drawString5("R", (float)(this.x + this.width - 30.0), (float)(this.y + 7.0), new Color(4737096).getRGB());
        }
        else {
            FontRender.drawString5("R", (float)(this.x + this.width - 30.0), (float)(this.y + 7.0), -1);
        }
        this.renderPicker(mouseX, mouseY, this.getColorSetting().getColorObject());
    }
    
    private void renderPicker(final int mouseX, final int mouseY, final Color color) {
        final double cx = this.x + 6.0;
        final double cy = this.y + 16.0;
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
        this.spos = RenderUtil.scrollAnimate(this.spos, (float)(cx + cw - (cw - cw * this.saturation)), 0.6f);
        this.bpos = RenderUtil.scrollAnimate(this.bpos, (float)(cy + (ch - ch * this.brightness)), 0.6f);
        this.hpos = RenderUtil.scrollAnimate(this.hpos, (float)(cy + (ch - 3.0 + (ch - 3.0) * this.hue)), 0.6f);
        this.apos = RenderUtil.scrollAnimate(this.apos, (float)(cy + (ch - 3.0 - (ch - 3.0) * (this.alpha / 255.0f))), 0.6f);
        final Color colorA = Color.getHSBColor(this.hue, 0.0f, 1.0f);
        final Color colorB = Color.getHSBColor(this.hue, 1.0f, 1.0f);
        final Color colorC = new Color(0, 0, 0, 0);
        final Color colorD = new Color(0, 0, 0);
        Drawable.horizontalGradient((float)cx, (float)cy, cx + cw, cy + ch, colorA.getRGB(), colorB.getRGB());
        Drawable.verticalGradient(cx, cy, cx + cw, cy + ch, colorC.getRGB(), colorD.getRGB());
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
            this.saturation = (float)(MathUtil.clamp((float)(mouseX - cx), 0.0f, (float)cw) / cw);
            this.brightness = (float)((ch - MathUtil.clamp((float)(mouseY - cy), 0.0f, (float)ch)) / ch);
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
    
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        final double cx = this.x + 4.0;
        final double cy = this.y + 17.0;
        final double cw = this.width - 34.0;
        final double ch = this.height - 20.0;
        if (Drawable.isHovered(mouseX, mouseY, cx + cw + 17.0, cy, 8.0, ch) && button == 0) {
            this.afocused = true;
        }
        else if (Drawable.isHovered(mouseX, mouseY, cx + cw + 4.0, cy, 8.0, ch) && button == 0) {
            this.hfocused = true;
        }
        else if (Drawable.isHovered(mouseX, mouseY, cx, cy, cw, ch) && button == 0) {
            this.sbfocused = true;
        }
        else if (Drawable.isHovered(mouseX, mouseY, (float)(this.x + this.width - 30.0), (float)(this.y + 7.0), 10.0, 10.0) && button == 0) {
            this.getColorSetting().setCycle(!this.getColorSetting().isCycle());
        }
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int button) {
        this.hfocused = false;
        this.afocused = false;
        this.sbfocused = false;
    }
    
    public void onClose() {
        this.hfocused = false;
        this.afocused = false;
        this.sbfocused = false;
    }
}

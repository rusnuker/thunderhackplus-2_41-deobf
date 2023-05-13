//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.clickui.elements;

import com.mrzak34.thunderhack.gui.clickui.base.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.util.render.*;
import com.mrzak34.thunderhack.util.math.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import java.util.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.util.math.*;

public class SliderElement extends AbstractElement
{
    private final float min;
    private final float max;
    public boolean listening;
    public String Stringnumber;
    private float animation;
    private double stranimation;
    private boolean dragging;
    
    public SliderElement(final Setting setting) {
        super(setting);
        this.Stringnumber = "";
        this.min = setting.getMin().floatValue();
        this.max = setting.getMax().floatValue();
    }
    
    public static String removeLastChar(final String str) {
        String output = "";
        if (str != null && str.length() > 0) {
            output = str.substring(0, str.length() - 1);
        }
        return output;
    }
    
    public void render(final int mouseX, final int mouseY, final float delta) {
        final double currentPos = (this.setting.getValue().floatValue() - this.min) / (this.max - this.min);
        this.stranimation += (this.setting.getValue().floatValue() * 100.0f / 100.0f - this.stranimation) / 2.0;
        this.animation = RenderUtil.scrollAnimate(this.animation, (float)currentPos, 0.5f);
        super.render(mouseX, mouseY, delta);
        String value = "";
        if (this.setting.getValue() instanceof Float) {
            value = String.valueOf(MathUtil.round(this.setting.getValue(), 2));
        }
        if (this.setting.getValue() instanceof Integer) {
            value = String.valueOf(MathUtil.round(this.setting.getValue(), 2));
        }
        if (!this.listening) {
            FontRender.drawString5(this.setting.getName(), (float)(this.x + 4.0), (float)(this.y + 4.0), -1);
            if (this.setting.getValue() instanceof Float) {
                FontRender.drawString5(String.valueOf(MathUtil.round(this.setting.getValue(), 2)), (float)(this.x + this.width - 4.0 - FontRender.getStringWidth6(value)), (float)this.y + 5.0f, -1);
            }
            if (this.setting.getValue() instanceof Integer) {
                FontRender.drawString5(String.valueOf(MathUtil.round(this.setting.getValue(), 2)), (float)(this.x + this.width - 4.0 - FontRender.getStringWidth6(value)), (float)this.y + 5.0f, -1);
            }
        }
        else {
            FontRender.drawString5(this.setting.getName(), (float)(this.x + 4.0), (float)(this.y + 4.0), -1);
            if (Objects.equals(this.Stringnumber, "")) {
                FontRender.drawString5("...", (float)(this.x + this.width - 4.0 - FontRender.getStringWidth6(value)), (float)this.y + 5.0f, -1);
            }
            else {
                FontRender.drawString5(this.Stringnumber, (float)(this.x + this.width - 4.0 - FontRender.getStringWidth6(value)), (float)this.y + 5.0f, -1);
            }
        }
        final Color color = new Color(-1973791);
        RoundedShader.drawRound((float)(this.x + 4.0), (float)(this.y + this.height - 4.0), (float)(this.width - 8.0), 1.0f, 0.5f, new Color(-15856114));
        RoundedShader.drawRound((float)(this.x + 4.0), (float)(this.y + this.height - 4.0), (float)((this.width - 8.0) * this.animation), 1.0f, 0.5f, color);
        RoundedShader.drawRound((float)(this.x + 2.0 + (this.width - 8.0) * this.animation), (float)(this.y + this.height - 5.5), 4.0f, 4.0f, 1.5f, color);
        this.animation = MathUtil.clamp(this.animation, 0.0f, 1.0f);
        if (this.dragging) {
            this.setValue(mouseX, this.x + 7.0, this.width - 14.0);
        }
    }
    
    private void setValue(final int mouseX, final double x, final double width) {
        final double diff = this.setting.getMax().floatValue() - this.setting.getMin().floatValue();
        final double percentBar = MathHelper.clamp((mouseX - x) / width, 0.0, 1.0);
        final double value = this.setting.getMin().floatValue() + percentBar * diff;
        if (this.setting.getValue() instanceof Float) {
            this.setting.setValue((float)value);
        }
        else if (this.setting.getValue() instanceof Integer) {
            this.setting.setValue((int)value);
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (button == 0 && this.hovered) {
            this.dragging = true;
        }
        else if (this.hovered) {
            this.Stringnumber = "";
            this.listening = true;
        }
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int button) {
        this.dragging = false;
    }
    
    public void resetAnimation() {
        this.dragging = false;
        this.animation = 0.0f;
        this.stranimation = 0.0;
    }
    
    public void keyTyped(final char typedChar, final int keyCode) {
        if (this.listening) {
            switch (keyCode) {
                case 1: {
                    this.listening = false;
                    this.Stringnumber = "";
                    return;
                }
                case 28: {
                    try {
                        this.searchNumber();
                    }
                    catch (Exception e) {
                        this.Stringnumber = "";
                        this.listening = false;
                    }
                }
                case 14: {
                    this.Stringnumber = removeLastChar(this.Stringnumber);
                    break;
                }
            }
            this.Stringnumber += typedChar;
        }
    }
    
    private void searchNumber() {
        if (this.setting.getValue() instanceof Float) {
            this.setting.setValue(Float.valueOf(this.Stringnumber));
            this.Stringnumber = "";
            this.listening = false;
        }
        else if (this.setting.getValue() instanceof Integer) {
            this.setting.setValue(Integer.valueOf(this.Stringnumber));
            this.Stringnumber = "";
            this.listening = false;
        }
    }
}

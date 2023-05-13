//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.thundergui2.components;

import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.gui.thundergui2.*;
import java.awt.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import com.mrzak34.thunderhack.util.render.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.util.math.*;
import java.util.*;
import net.minecraft.util.math.*;

public class SliderComponent extends SettingElement
{
    private final float min;
    private final float max;
    public boolean listening;
    public String Stringnumber;
    private float animation;
    private double stranimation;
    private boolean dragging;
    
    public SliderComponent(final Setting setting) {
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
    
    public void render(final int mouseX, final int mouseY, final float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);
        if (this.getY() > ThunderGui2.getInstance().main_posY + ThunderGui2.getInstance().height || this.getY() < ThunderGui2.getInstance().main_posY) {
            return;
        }
        FontRender.drawString6(this.getSetting().getName(), (float)this.getX(), (float)this.getY() + 5.0f, this.isHovered() ? -1 : new Color(-1325400065, true).getRGB(), false);
        final double currentPos = (this.setting.getValue().floatValue() - this.min) / (this.max - this.min);
        this.stranimation += (this.setting.getValue().floatValue() * 100.0f / 100.0f - this.stranimation) / 2.0;
        this.animation = RenderUtil.scrollAnimate(this.animation, (float)currentPos, 0.5f);
        final Color color = new Color(-1973791);
        RoundedShader.drawRound((float)(this.x + 54.0), (float)(this.y + this.height - 8.0), 90.0f, 1.0f, 0.5f, new Color(-15856114));
        RoundedShader.drawRound((float)(this.x + 54.0), (float)(this.y + this.height - 8.0), 90.0f * this.animation, 1.0f, 0.5f, color);
        RoundedShader.drawRound((float)(this.x + 52.0 + 90.0f * this.animation), (float)(this.y + this.height - 9.5), 4.0f, 4.0f, 1.5f, color);
        if (mouseX > this.x + 154.0 && mouseX < this.x + 176.0 && mouseY > this.y + this.height - 11.0 && mouseY < this.y + this.height - 4.0) {
            RoundedShader.drawRound((float)(this.x + 154.0), (float)(this.y + this.height - 11.0), 22.0f, 7.0f, 0.5f, new Color(82, 57, 100, 178));
        }
        else {
            RoundedShader.drawRound((float)(this.x + 154.0), (float)(this.y + this.height - 11.0), 22.0f, 7.0f, 0.5f, new Color(50, 35, 60, 178));
        }
        if (!this.listening) {
            if (this.setting.getValue() instanceof Float) {
                FontRender.drawString6(String.valueOf(MathUtil.round(this.setting.getValue(), 2)), (float)(this.x + 156.0), (float)(this.y + this.height - 9.0), new Color(-1157627905, true).getRGB(), false);
            }
            if (this.setting.getValue() instanceof Integer) {
                FontRender.drawString6(String.valueOf(this.setting.getValue()), (float)(this.x + 156.0), (float)(this.y + this.height - 9.0), new Color(-1157627905, true).getRGB(), false);
            }
        }
        else if (Objects.equals(this.Stringnumber, "")) {
            FontRender.drawString6("...", (float)(this.x + 156.0), (float)(this.y + this.height - 9.0), new Color(-1157627905, true).getRGB(), false);
        }
        else {
            FontRender.drawString6(this.Stringnumber, (float)(this.x + 156.0), (float)(this.y + this.height - 9.0), new Color(-1157627905, true).getRGB(), false);
        }
        this.animation = MathUtil.clamp(this.animation, 0.0f, 1.0f);
        if (this.dragging) {
            this.setValue(mouseX, this.x + 54.0, 90.0);
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
        if (this.getY() > ThunderGui2.getInstance().main_posY + ThunderGui2.getInstance().height || this.getY() < ThunderGui2.getInstance().main_posY) {
            return;
        }
        if (mouseX > this.x + 154.0 && mouseX < this.x + 176.0 && mouseY > this.y + this.height - 11.0 && mouseY < this.y + this.height - 4.0) {
            this.Stringnumber = "";
            this.listening = true;
        }
        else if (button == 0 && this.hovered) {
            this.dragging = true;
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
    
    public void checkMouseWheel(final float value) {
        super.checkMouseWheel(value);
        if (this.isHovered()) {
            ThunderGui2.scroll_lock = true;
            if (value < 0.0f) {
                if (this.setting.getValue() instanceof Float) {
                    this.setting.setValue(this.setting.getValue() + 0.01f);
                }
                else if (this.setting.getValue() instanceof Integer) {
                    this.setting.setValue(this.setting.getValue() + 1);
                }
            }
            else if (value > 0.0f) {
                if (this.setting.getValue() instanceof Float) {
                    this.setting.setValue(this.setting.getValue() - 0.01f);
                }
                else if (this.setting.getValue() instanceof Integer) {
                    this.setting.setValue(this.setting.getValue() - 1);
                }
            }
        }
    }
}

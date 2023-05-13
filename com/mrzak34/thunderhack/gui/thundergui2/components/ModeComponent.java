//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.thundergui2.components;

import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.gui.thundergui2.*;
import java.awt.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.modules.client.*;
import com.mrzak34.thunderhack.util.render.*;

public class ModeComponent extends SettingElement
{
    int progress;
    private double wheight;
    private boolean open;
    
    public ModeComponent(final Setting setting) {
        super(setting);
        this.progress = 0;
    }
    
    @Override
    public void render(final int mouseX, final int mouseY, final float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);
        if (this.getY() > ThunderGui2.getInstance().main_posY + ThunderGui2.getInstance().height || this.getY() < ThunderGui2.getInstance().main_posY) {
            return;
        }
        FontRender.drawString6(this.getSetting().getName(), (float)this.getX(), (float)this.getY() + 5.0f, this.isHovered() ? -1 : new Color(-1325400065, true).getRGB(), false);
        if (this.open) {
            double offsetY2 = 0.0;
            for (int i = 0; i <= this.setting.getModes().length - 1; ++i) {
                offsetY2 += 12.0;
            }
            RoundedShader.drawRound((float)(this.x + 114.0), (float)(this.y + 2.0), 62.0f, (float)(11.0 + offsetY2), 0.5f, new Color(50, 35, 60, 121));
        }
        if (mouseX > this.x + 114.0 && mouseX < this.x + 176.0 && mouseY > this.y + 2.0 && mouseY < this.y + 15.0) {
            RoundedShader.drawRound((float)(this.x + 114.0), (float)(this.y + 2.0), 62.0f, 11.0f, 0.5f, new Color(82, 57, 100, 178));
        }
        else {
            RoundedShader.drawRound((float)(this.x + 114.0), (float)(this.y + 2.0), 62.0f, 11.0f, 0.5f, new Color(50, 35, 60, 178));
        }
        FontRender.drawString6(this.setting.currentEnumName(), (float)(this.x + 116.0), (float)(this.y + 5.0), new Color(-1325400065, true).getRGB(), false);
        String arrow = "n";
        switch (this.progress) {
            case 0: {
                arrow = "n";
                break;
            }
            case 1: {
                arrow = "o";
                break;
            }
            case 2: {
                arrow = "p";
                break;
            }
            case 3: {
                arrow = "q";
                break;
            }
            case 4: {
                arrow = "r";
                break;
            }
        }
        FontRender.drawIcon(arrow, (int)(this.x + 166.0), (int)(this.y + 6.0), -1);
        double offsetY3 = 13.0;
        if (this.open) {
            final Color color = ClickGui.getInstance().getColor(0);
            for (int j = 0; j <= this.setting.getModes().length - 1; ++j) {
                FontRender.drawString5(this.setting.getModes()[j], (float)(this.x + 116.0), (float)(this.y + 5.0 + offsetY3), this.setting.currentEnumName().equalsIgnoreCase(this.setting.getModes()[j]) ? color.getRGB() : -1);
                offsetY3 += 12.0;
            }
        }
    }
    
    @Override
    public void onTick() {
        if (this.open && this.progress > 0) {
            --this.progress;
        }
        if (!this.open && this.progress < 4) {
            ++this.progress;
        }
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.getY() > ThunderGui2.getInstance().main_posY + ThunderGui2.getInstance().height || this.getY() < ThunderGui2.getInstance().main_posY) {
            return;
        }
        if (mouseX > this.x + 114.0 && mouseX < this.x + 176.0 && mouseY > this.y + 2.0 && mouseY < this.y + 15.0) {
            this.open = !this.open;
        }
        if (this.open) {
            double offsetY = 0.0;
            for (int i = 0; i <= this.setting.getModes().length - 1; ++i) {
                if (Drawable.isHovered(mouseX, mouseY, this.x, this.y + this.wheight + offsetY, this.width, 12.0) && button == 0) {
                    this.setting.setEnumByNumber(i);
                }
                offsetY += 12.0;
            }
        }
    }
    
    public void setWHeight(final double height) {
        this.wheight = height;
    }
    
    public boolean isOpen() {
        return this.open;
    }
}

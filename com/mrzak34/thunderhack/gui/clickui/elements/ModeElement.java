//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.clickui.elements;

import com.mrzak34.thunderhack.gui.clickui.base.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.notification.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.util.render.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import com.mrzak34.thunderhack.modules.client.*;
import java.awt.*;

public class ModeElement extends AbstractElement
{
    private final Animation rotation;
    public Setting setting2;
    private boolean open;
    private double wheight;
    
    public ModeElement(final Setting setting) {
        super(setting);
        this.rotation = new DecelerateAnimation(240, 1.0, Direction.FORWARDS);
        this.setting2 = setting;
    }
    
    public void render(final int mouseX, final int mouseY, final float delta) {
        super.render(mouseX, mouseY, delta);
        this.rotation.setDirection(this.open ? Direction.BACKWARDS : Direction.FORWARDS);
        final float tx = (float)(this.x + this.width - 7.0);
        final float ty = (float)(this.y + this.wheight / 2.0);
        final float thetaRotation = (float)(-180.0 * this.rotation.getOutput());
        GlStateManager.pushMatrix();
        GlStateManager.translate(tx, ty, 0.0f);
        GlStateManager.rotate(thetaRotation, 0.0f, 0.0f, 1.0f);
        GlStateManager.translate(-tx, -ty, 0.0f);
        Drawable.drawTexture(new ResourceLocation("textures/arrow.png"), this.x + this.width - 10.0, this.y + (this.wheight - 6.0) / 2.0, 6.0, 6.0);
        GlStateManager.popMatrix();
        FontRender.drawString5(this.setting2.getName(), (float)(this.x + 3.0), (float)(this.y + this.wheight / 2.0 - FontRender.getFontHeight5() / 2.0f) + 3.0f, -1);
        FontRender.drawString5(this.setting2.currentEnumName(), (float)(this.x + this.width - 16.0 - FontRender.getStringWidth5(this.setting.currentEnumName())), 3.0f + (float)(this.y + this.wheight / 2.0 - FontRender.getFontHeight5() / 2.0f), -1);
        if (this.open) {
            final Color color = ClickGui.getInstance().getColor(0);
            double offsetY = 0.0;
            for (int i = 0; i <= this.setting2.getModes().length - 1; ++i) {
                FontRender.drawCentString5(this.setting2.getModes()[i], (float)this.x + (float)this.width / 2.0f, (float)(this.y + this.wheight + (6.0f - FontRender.getFontHeight5() / 2.0f - 1.0f) + offsetY), this.setting2.currentEnumName().equalsIgnoreCase(this.setting2.getModes()[i]) ? color.getRGB() : -1);
                offsetY += 12.0;
            }
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (Drawable.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.wheight)) {
            if (button == 0) {
                this.setting2.increaseEnum();
            }
            else {
                this.open = !this.open;
            }
        }
        if (this.open) {
            double offsetY = 0.0;
            for (int i = 0; i <= this.setting2.getModes().length - 1; ++i) {
                if (Drawable.isHovered(mouseX, mouseY, this.x, this.y + this.wheight + offsetY, this.width, 12.0) && button == 0) {
                    this.setting2.setEnumByNumber(i);
                }
                offsetY += 12.0;
            }
        }
    }
    
    public void resetAnimation() {
    }
    
    public void setWHeight(final double height) {
        this.wheight = height;
    }
    
    public boolean isOpen() {
        return this.open;
    }
}

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

public class ParentElement extends AbstractElement
{
    private final Setting<Parent> parentSetting;
    private final Animation rotation;
    
    public ParentElement(final Setting setting) {
        super(setting);
        this.rotation = new DecelerateAnimation(240, 1.0, Direction.FORWARDS);
        this.parentSetting = (Setting<Parent>)setting;
    }
    
    public void render(final int mouseX, final int mouseY, final float delta) {
        super.render(mouseX, mouseY, delta);
        this.rotation.setDirection(this.getParentSetting().getValue().isExtended() ? Direction.BACKWARDS : Direction.FORWARDS);
        final float tx = (float)(this.x + this.width - 7.0);
        final float ty = (float)(this.y + 8.5);
        final float thetaRotation = (float)(-180.0 * this.rotation.getOutput());
        GlStateManager.pushMatrix();
        GlStateManager.translate(tx, ty, 0.0f);
        GlStateManager.rotate(thetaRotation, 0.0f, 0.0f, 1.0f);
        GlStateManager.translate(-tx, -ty, 0.0f);
        Drawable.drawTexture(new ResourceLocation("textures/arrow.png"), this.x + this.width - 10.0, this.y + 5.5, 6.0, 6.0);
        GlStateManager.popMatrix();
        FontRender.drawString5(this.setting.getName(), (float)(this.x + 3.0), (float)(this.y + this.height / 2.0 - FontRender.getFontHeight5() / 2.0f) + 3.0f, -1);
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.hovered) {
            this.getParentSetting().getValue().setExtended(!this.getParentSetting().getValue().isExtended());
        }
    }
    
    public Setting<Parent> getParentSetting() {
        return this.parentSetting;
    }
}

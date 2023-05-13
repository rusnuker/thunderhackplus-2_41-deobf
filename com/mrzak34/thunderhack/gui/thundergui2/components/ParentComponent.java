//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.thundergui2.components;

import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.gui.thundergui2.*;
import java.awt.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import com.mrzak34.thunderhack.util.render.*;

public class ParentComponent extends SettingElement
{
    public ParentComponent(final Setting setting) {
        super(setting);
        final Parent parent = setting.getValue();
        parent.setExtended(true);
    }
    
    @Override
    public void render(final int mouseX, final int mouseY, final float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);
        if (this.getY() > ThunderGui2.getInstance().main_posY + ThunderGui2.getInstance().height || this.getY() < ThunderGui2.getInstance().main_posY) {
            return;
        }
        FontRender.drawCentString6(this.getSetting().getName(), (float)(this.getX() + this.width / 2.0), (float)this.getY() + 5.0f, new Color(-1325400065, true).getRGB());
        RenderUtil.draw2DGradientRect((float)(this.getX() + 10.0), (float)(this.getY() + 6.0), (float)(this.getX() + this.width / 2.0 - 20.0), (float)(this.getY() + 7.0), new Color(16777215, true).getRGB(), new Color(16777215, true).getRGB(), new Color(-1325400065, true).getRGB(), new Color(-1325400065, true).getRGB());
        RenderUtil.draw2DGradientRect((float)(this.getX() + this.width / 2.0 + 20.0), (float)(this.getY() + 6.0), (float)(this.getX() + this.width - 10.0), (float)(this.getY() + 7.0), new Color(-1325400065, true).getRGB(), new Color(-1325400065, true).getRGB(), new Color(16777215, true).getRGB(), new Color(16777215, true).getRGB());
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.getY() > ThunderGui2.getInstance().main_posY + ThunderGui2.getInstance().height || this.getY() < ThunderGui2.getInstance().main_posY) {
            return;
        }
        if (this.hovered) {
            final Parent parent = this.setting.getValue();
            parent.setExtended(!parent.isExtended());
        }
    }
}

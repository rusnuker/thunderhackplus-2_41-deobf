//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.clickui.elements;

import com.mrzak34.thunderhack.gui.clickui.base.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import com.mojang.realmsclient.gui.*;
import com.mrzak34.thunderhack.setting.*;

public class SubBindElement extends AbstractElement
{
    public boolean isListening;
    
    public SubBindElement(final Setting setting) {
        super(setting);
    }
    
    public void render(final int mouseX, final int mouseY, final float delta) {
        super.render(mouseX, mouseY, delta);
        if (this.isListening) {
            FontRender.drawString5("...", (float)(this.x + 3.0), (float)(this.y + this.height / 2.0 - FontRender.getFontHeight5() / 2.0f), -1);
        }
        else {
            FontRender.drawString5("SubBind " + ChatFormatting.GRAY + this.setting.getValue().toString().toUpperCase(), (float)(this.x + 3.0), (float)(this.y + this.height / 2.0 - FontRender.getFontHeight5() / 2.0f), -1);
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.hovered && button == 0) {
            this.isListening = !this.isListening;
        }
    }
    
    public void keyTyped(final char chr, final int keyCode) {
        if (this.isListening) {
            SubBind subBindbind = new SubBind(keyCode);
            if (subBindbind.toString().equalsIgnoreCase("Escape")) {
                return;
            }
            if (subBindbind.toString().equalsIgnoreCase("Delete")) {
                subBindbind = new SubBind(-1);
            }
            this.setting.setValue(subBindbind);
            this.isListening = !this.isListening;
        }
    }
}

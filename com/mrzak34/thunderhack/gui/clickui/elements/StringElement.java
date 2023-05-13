//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.clickui.elements;

import com.mrzak34.thunderhack.gui.clickui.base.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.util.*;

public class StringElement extends AbstractElement
{
    public boolean isListening;
    private CurrentString currentString;
    
    public StringElement(final Setting setting) {
        super(setting);
        this.currentString = new CurrentString("");
    }
    
    public void render(final int mouseX, final int mouseY, final float delta) {
        super.render(mouseX, mouseY, delta);
        if (this.isListening) {
            FontRender.drawString5(this.currentString.getString() + this.getIdleSign(), (float)(this.x + 3.0), (float)(this.y + this.height / 2.0 - FontRender.getFontHeight5() / 2.0f), -1);
        }
        else {
            FontRender.drawString5(this.setting.getName().equals("Buttons") ? "Buttons " : ((this.setting.getName().equals("Prefix") ? ("Prefix  " + ChatFormatting.GRAY) : "") + this.setting.getValue()), (float)(this.x + 3.0), (float)(this.y + this.height / 2.0 - FontRender.getFontHeight5() / 2.0f), -1);
        }
    }
    
    public String getIdleSign() {
        return "...";
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.hovered && button == 0) {
            this.isListening = !this.isListening;
        }
    }
    
    public void keyTyped(final char chr, final int keyCode) {
        if (this.isListening) {
            switch (keyCode) {
                case 1: {
                    return;
                }
                case 28: {
                    this.enterString();
                }
                case 14: {
                    this.setString(SliderElement.removeLastChar(this.currentString.getString()));
                    break;
                }
            }
            if (ChatAllowedCharacters.isAllowedCharacter(chr)) {
                this.setString(this.currentString.getString() + chr);
            }
        }
    }
    
    private void enterString() {
        if (this.currentString.getString().isEmpty()) {
            this.setting.setValue(this.setting.getDefaultValue());
        }
        else {
            this.setting.setValue(this.currentString.getString());
        }
        this.setString("");
        this.isListening = !this.isListening;
    }
    
    public void setString(final String newString) {
        this.currentString = new CurrentString(newString);
    }
    
    public static class CurrentString
    {
        private final String string;
        
        public CurrentString(final String string) {
            this.string = string;
        }
        
        public String getString() {
            return this.string;
        }
    }
}

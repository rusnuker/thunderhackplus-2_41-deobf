//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.setting;

import java.awt.*;

public class ColorSettingHeader
{
    boolean opened;
    Color color;
    String name;
    
    public ColorSettingHeader(final boolean extended) {
        this.opened = extended;
    }
    
    public void setOpenedCSH(final boolean a) {
        this.opened = a;
    }
    
    public boolean getStateCSH() {
        return this.opened;
    }
    
    public void setNameCSH(final String a) {
        this.name = a;
    }
    
    public String getNameCSH() {
        return this.name;
    }
    
    public void setColorCSH(final Color a) {
        this.color = a;
    }
    
    public Color getColorCSH() {
        return this.color;
    }
}

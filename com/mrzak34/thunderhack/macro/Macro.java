//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.macro;

import net.minecraft.client.*;

public class Macro
{
    private final String name;
    private String text;
    private int bind;
    
    public Macro(final String name, final String text, final int bind) {
        this.name = name;
        this.text = text;
        this.bind = bind;
    }
    
    public void runMacro() {
        Minecraft.getMinecraft().player.sendChatMessage(this.text);
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getText() {
        return this.text;
    }
    
    public void setText(final String text) {
        this.text = text;
    }
    
    public int getBind() {
        return this.bind;
    }
    
    public void setBind(final int bind) {
        this.bind = bind;
    }
    
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Macro && this.getName().equalsIgnoreCase(((Macro)obj).getName());
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.setting;

import org.lwjgl.input.*;
import com.google.common.base.*;
import com.google.gson.*;

public class SubBind
{
    private int key;
    
    public SubBind(final int key) {
        this.key = key;
    }
    
    public static SubBind none() {
        return new SubBind(-1);
    }
    
    public int getKey() {
        return this.key;
    }
    
    public void setKey(final int key) {
        this.key = key;
    }
    
    public boolean isEmpty() {
        return this.key < 0;
    }
    
    @Override
    public String toString() {
        return this.isEmpty() ? "None" : ((this.key < 0) ? "None" : this.capitalise(Keyboard.getKeyName(this.key)));
    }
    
    public boolean isDown() {
        return !this.isEmpty() && Keyboard.isKeyDown(this.getKey());
    }
    
    private String capitalise(final String str) {
        if (str.isEmpty()) {
            return "";
        }
        return Character.toUpperCase(str.charAt(0)) + ((str.length() != 1) ? str.substring(1).toLowerCase() : "");
    }
    
    public static class SubBindConverter extends Converter<SubBind, JsonElement>
    {
        public JsonElement doForward(final SubBind subbind) {
            return (JsonElement)new JsonPrimitive(subbind.toString());
        }
        
        public SubBind doBackward(final JsonElement jsonElement) {
            final String s = jsonElement.getAsString();
            if (s.equalsIgnoreCase("None")) {
                return SubBind.none();
            }
            int key = -1;
            try {
                key = Keyboard.getKeyIndex(s.toUpperCase());
            }
            catch (Exception ex) {}
            if (key == 0) {
                return SubBind.none();
            }
            return new SubBind(key);
        }
    }
}

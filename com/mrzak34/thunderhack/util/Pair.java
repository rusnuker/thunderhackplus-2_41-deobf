//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

public class Pair<T, S>
{
    T key;
    S value;
    
    public Pair(final T key, final S value) {
        this.key = key;
        this.value = value;
    }
    
    public T getKey() {
        return this.key;
    }
    
    public void setKey(final T key) {
        this.key = key;
    }
    
    public S getValue() {
        return this.value;
    }
    
    public void setValue(final S value) {
        this.value = value;
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

public class MutableWrapper<T>
{
    protected T value;
    
    public MutableWrapper() {
        this(null);
    }
    
    public MutableWrapper(final T value) {
        this.value = value;
    }
    
    public T get() {
        return this.value;
    }
    
    public void set(final T value) {
        this.value = value;
    }
}
